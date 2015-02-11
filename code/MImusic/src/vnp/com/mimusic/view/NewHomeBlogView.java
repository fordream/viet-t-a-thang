package vnp.com.mimusic.view;

import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.Recomment;
import vnp.com.db.User;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.meetme.android.horizontallistview.HorizontalListView;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */

// vnp.com.mimusic.view.BangXepHangHeaderView
public abstract class NewHomeBlogView extends LinearLayout implements OnClickListener, OnItemClickListener {
	private DichVuStore dichVuStore;
	private LinearLayout main;
	private TextView title;
	private HorizontalListView mHlvSimpleList;
	private Context xContext;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		JSONObject cursor = (JSONObject) parent.getItemAtPosition(position);
		String xid = Conts.getString(cursor, DichVuStore.service_code);

		(((RootMenuActivity) xContext)).gotoChiTietDichVuFromHome(xid);
	}

	public NewHomeBlogView(Context context) {
		super(context);
		xContext = context;
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_blog, this);
		dichVuStore = new DichVuStore(getContext());

		findViewById(R.id.dichvubanchay).setOnClickListener(this);
		mHlvSimpleList = (HorizontalListView) findViewById(R.id.hlvSimpleList);
		mHlvSimpleList.setOnItemClickListener(this);
		main = Conts.getView(this, R.id.main);
		title = Conts.getView(this, R.id.title);
		if (type() == 0) {
			title.setText(R.string.dichvudexuat);
			mHlvSimpleList.setVisibility(View.VISIBLE);
		} else if (type() == 1) {
			title.setText(R.string.moithanhvien);
		} else {
			title.setText(R.string.dichvubanchay);
		}
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.dichvubanchay) {
			onClickHeader();
		}
	}

	public void update() {
		if (type() == 0) {
			addDichVuDeXuat();
		} else if (type() == 1) {
			addUerHot();
		} else {
			addDichVuHot();
		}
	}

	private void addDichVuHot() {
		JSONArray array = dichVuStore.getDichvu();
		int index = 0;
		main.removeAllViews();
		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject object = array.getJSONObject(i);
				final ContentValues values = new ContentValues();
				values.put("name", String.format(xContext.getString(R.string.title_dangky), Conts.getString(object, DichVuStore.service_name)));
				values.put(DichVuStore.service_name, Conts.getString(object, DichVuStore.service_name));
				values.put(DichVuStore.service_code, Conts.getString(object, DichVuStore.service_code));
				String content = String.format(xContext.getString(R.string.xacnhandangky_form), Conts.getString(object, DichVuStore.service_name), Conts.getString(object, DichVuStore.service_price));
				values.put("content", content);
				values.put(DichVuStore.ID, Conts.getString(object, DichVuStore.ID));
				values.put("type", "dangky");

				DichVuItemView child = new DichVuItemView(getContext());
				child.setData(object, i);

				main.addView(child);
				final String service_code = Conts.getString(object, DichVuStore.service_code);

				final int position = index;
				child.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						((RootMenuActivity) xContext).gotoChiTietDichVuFromHome(service_code);
					}
				});

				child.findViewById(R.id.home_item_right_control_2).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						(((RootMenuActivity) xContext)).gotoMoiDvChoNhieuNguoi(service_code, position);
					}
				});

				final boolean isDangKy = dichVuStore.isRegister(Conts.getString(object, DichVuStore.service_code));

				if (isDangKy) {
					child.findViewById(R.id.home_item_right_control_1).setOnClickListener(null);
				} else {
					child.findViewById(R.id.home_item_right_control_1).setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							dangky(values);
						}
					});
				}

				index++;
				if (index == 3) {
					break;
				}
			} catch (Exception exception) {

			}
		}

	}

	private void addUerHot() {
		Cursor cursorUserRecomment = Recomment.getCursorFromUser(getContext(), 5);

		main.removeAllViews();

		if (cursorUserRecomment != null) {
			while (cursorUserRecomment.moveToNext()) {
				NewHomeItemView child = new NewHomeItemView(getContext());
				child.updateUser(cursorUserRecomment);
				main.addView(child);
				final String user = Conts.getStringCursor(cursorUserRecomment, User.USER);
				final String name = Conts.getStringCursor(cursorUserRecomment, User.NAME_CONTACT);
				final int position = cursorUserRecomment.getPosition();

				child.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						(((RootMenuActivity) xContext)).moiContactUser(user, name, position);
					}
				});

			}
		}

		if (cursorUserRecomment != null) {
			cursorUserRecomment.close();
		}
	}

	private void addDichVuDeXuat() {
		if (mHlvSimpleList.getAdapter() != null) {
			if (mHlvSimpleList.getAdapter().getCount() > 0) {
				return;
			}
		}

		String listRecomment = Recomment.getListReCommentDichvu(getContext());
		StringTokenizer stringTokenizer = new StringTokenizer(listRecomment, ",");
		final JSONArray array = new JSONArray();
		while (stringTokenizer.hasMoreElements()) {
			String serviceCode = stringTokenizer.nextElement().toString();
			if (!Conts.isBlank(serviceCode)) {
				serviceCode = serviceCode.replace("\"", "").replace("'", "");
				array.put(dichVuStore.getDvByServiceCode(serviceCode));
			}
		}

		BaseAdapter adapter = new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (convertView == null) {
					convertView = new ReCommentDichVuItemView(parent.getContext());
				}
				((ReCommentDichVuItemView) convertView).setData((JSONObject) getItem(position), position);
				return convertView;
			}

			@Override
			public long getItemId(int position) {
				return position;
			}

			@Override
			public Object getItem(int position) {
				try {
					return array.get(position);
				} catch (JSONException e) {
					return new JSONObject();
				}
			}

			@Override
			public int getCount() {
				return array.length();
			}
		};

		mHlvSimpleList.setAdapter(adapter);

	}

	public void dangky(ContentValues values) {

	}

	public abstract void onClickHeader();

	/**
	 * 
	 * @return 0 dich vu de xuat 1 moi thue bao, 2 dịch vu ban chay
	 * 
	 */
	public abstract int type();
}