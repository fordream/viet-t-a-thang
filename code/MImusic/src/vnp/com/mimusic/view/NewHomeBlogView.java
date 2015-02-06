package vnp.com.mimusic.view;

import vnp.com.db.DichVu;
import vnp.com.db.Recomment;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	private LinearLayout main;
	private TextView title;
	private HorizontalListView mHlvSimpleList;
	private Context xContext;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		String xid = Conts.getStringCursor(cursor, DichVu.ID);
		(((RootMenuActivity) xContext)).gotoChiTietDichVuFromHome(xid);
	}

	public NewHomeBlogView(Context context) {
		super(context);
		xContext = context;
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_blog, this);
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
			
			if (mHlvSimpleList.getAdapter() != null) {
				return;
			}
			
			Cursor cursor = Recomment.getCursorFromDichvu(getContext(), -1);
			mHlvSimpleList.setAdapter(new CursorAdapter(getContext(), cursor) {
				@Override
				public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
					return new ReCommentDichVuItemView(arg0);
				}

				@Override
				public void bindView(View arg0, Context arg1, Cursor cursor) {
					if (arg0 == null) {
						arg0 = new ReCommentDichVuItemView(arg1);
					}
					((ReCommentDichVuItemView) arg0).setData(cursor);
				}
			});
		} else if (type() == 1) {
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
		} else {
			Cursor cursorDVHot = DichVu.getCursorFromUser(getContext(), 3);

			main.removeAllViews();

			if (cursorDVHot != null) {
				while (cursorDVHot.moveToNext()) {
					final ContentValues values = new ContentValues();
					values.put("name", String.format(xContext.getString(R.string.title_dangky), cursorDVHot.getString(cursorDVHot.getColumnIndex(DichVu.service_name))));
					values.put(DichVu.service_name, cursorDVHot.getString(cursorDVHot.getColumnIndex(DichVu.service_name)));
					values.put(DichVu.service_code, cursorDVHot.getString(cursorDVHot.getColumnIndex(DichVu.service_code)));
					String content = String.format(xContext.getString(R.string.xacnhandangky_form), Conts.getStringCursor(cursorDVHot, DichVu.service_name),
							Conts.getStringCursor(cursorDVHot, DichVu.service_price));
					values.put("content", content);
					values.put(DichVu.ID, cursorDVHot.getString(cursorDVHot.getColumnIndex(DichVu.ID)));
					values.put("type", "dangky");

					DichVuItemView child = new DichVuItemView(getContext());
					child.setData(cursorDVHot);
					main.addView(child);
					final String id = Conts.getStringCursor(cursorDVHot, DichVu.ID);
					final int position = cursorDVHot.getPosition();
					child.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							((RootMenuActivity) xContext).gotoChiTietDichVuFromHome(id);
						}
					});

					child.findViewById(R.id.home_item_right_control_2).setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							(((RootMenuActivity) xContext)).gotoMoiDvChoNhieuNguoi(id, position);
						}
					});

					final boolean isDangKy = "0".equals(cursorDVHot.getString(cursorDVHot.getColumnIndex(DichVu.service_status)));
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
				}
			}
		}
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