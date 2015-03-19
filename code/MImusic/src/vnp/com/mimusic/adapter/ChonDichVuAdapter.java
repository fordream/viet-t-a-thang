package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.DichVuItemView;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

public abstract class ChonDichVuAdapter extends ArrayAdapter<JSONObject> {

	JSONArray array;
	private DichVuStore dichVuStore;

	public ChonDichVuAdapter(Context context, JSONArray array) {
		super(context, 0);
		this.array = array;
		dichVuStore = new DichVuStore(getContext());
	}

	@Override
	public int getCount() {
		return array.length();
	}

	@Override
	public JSONObject getItem(int position) {
		try {
			return array.getJSONObject(position);
		} catch (JSONException e) {
			return new JSONObject();
		}

	}

	@Override
	public View getView(int poistion, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new DichVuItemView(parent.getContext());
		}

		/*
		 * kiem tra xem co hien thi item nay khong
		 */
		JSONObject cursor = getItem(poistion);
		String name = Conts.getString(cursor, DichVuStore.service_name);
		final String id = Conts.getString(cursor, DichVuStore.ID);
		convertView.findViewById(R.id.home_item_main).setVisibility(Conts.xDontains(textSearch, false, new String[] { name }) ? View.VISIBLE : View.GONE);

		/**
		 * set data
		 */
		((DichVuItemView) convertView).setData(cursor, poistion);
		Resources resources = parent.getContext().getResources();
		convertView.findViewById(R.id.home_item_main).setBackgroundColor(resources.getColor(poistion % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		convertView.findViewById(R.id.home_item_img_icon).setBackgroundColor(resources.getColor(poistion % 2 == 1 ? android.R.color.white : R.color.f3f3f3));

		final boolean isDangKy = dichVuStore.isRegister(Conts.getString(cursor, DichVuStore.service_code));
		TextView home_item_right_control_1_tv = (TextView) convertView.findViewById(R.id.home_item_right_control_1_tv);

		home_item_right_control_1_tv.setText(isDangKy ? R.string.dangdung : R.string.dangky);
		home_item_right_control_1_tv.setTextColor(resources.getColor(R.color.c475055));

		((TextView) convertView.findViewById(R.id.home_item_right_control_2_tv)).setTextColor(resources.getColor(R.color.a73444));

		convertView.findViewById(R.id.home_item_right_control_icon).setBackgroundResource(R.drawable.home_dangky_2);
		convertView.findViewById(R.id.home_item_right_control_2_icon).setBackgroundResource(R.drawable.home_moi_2);

		/**
	 * 
	 */
		int color = resources.getColor(R.color.dcdee1);
		if (poistion % 2 == 0) {
			color = resources.getColor(R.color.e7e9ec);
		}

		convertView.findViewById(R.id.home_item_right_control).setBackgroundColor(color);

		/**
	 * 
	 */
		ImageView home_item_img_icon = (ImageView) convertView.findViewById(R.id.home_item_img_icon);
		View home_item_right_control_1 = (View) convertView.findViewById(R.id.home_item_right_control_1);
		View home_item_right_control_2 = (View) convertView.findViewById(R.id.home_item_right_control_2);

		TextView home_item_tv_name = (TextView) convertView.findViewById(R.id.home_item_tv_name);
		TextView home_item_tv_link = (TextView) convertView.findViewById(R.id.home_item_tv_link);
		TextView home_item_tv_content = (TextView) convertView.findViewById(R.id.home_item_tv_content);
		home_item_tv_name.setText(Conts.getString(cursor, DichVuStore.service_name));
		home_item_tv_content.setText(Conts.getString(cursor, DichVuStore.service_content));

		home_item_img_icon.setImageResource(R.drawable.no_avatar);
		// show image
		String service_icon = Conts.getString(cursor, DichVuStore.service_icon) + "";

		// ImageLoaderUtils.getInstance(context).displayImage(service_icon,
		// home_item_img_icon, R.drawable.no_image);
		Conts.showLogoDichvu(home_item_img_icon, service_icon);

		final ContentValues values = new ContentValues();
		values.put("name", Conts.getString(cursor, DichVuStore.service_name));
		values.put(DichVuStore.service_code, Conts.getString(cursor, DichVuStore.service_code));
		values.put("content", Conts.getString(cursor, DichVuStore.service_content));
		values.put(DichVuStore.ID, Conts.getString(cursor, DichVuStore.ID));
		values.put("type", "dangky");
		home_item_right_control_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isDangKy) {
					dangKy(values);

				}
			}
		});

		home_item_right_control_2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
			}
		});
		convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				moiDVChoNhieuNguoi(id);
			}
		});

		convertView.findViewById(R.id.home_item_right_control).setVisibility(View.INVISIBLE);

		return convertView;
	}

	// @Override
	// public void bindView(View convertView, Context context, Cursor cursor) {
	// if (convertView == null) {
	// convertView = new DichVuItemView(context);
	// }
	//
	// /*
	// * kiem tra xem co hien thi item nay khong
	// */
	// String name =
	// cursor.getString(cursor.getColumnIndex(DichVuStore.service_name));
	// final String id =
	// cursor.getString(cursor.getColumnIndex(DichVuStore.ID));
	// convertView.findViewById(R.id.home_item_main).setVisibility(Conts.xDontains(textSearch,
	// false, new String[] { name }) ? View.VISIBLE : View.GONE);
	//
	// /**
	// * set data
	// */
	// ((DichVuItemView) convertView).setData(cursor);
	// int poistion = cursor.getPosition();
	// Resources resources = context.getResources();
	// convertView.findViewById(R.id.home_item_main).setBackgroundColor(resources.getColor(poistion
	// % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
	// convertView.findViewById(R.id.home_item_img_icon).setBackgroundColor(resources.getColor(poistion
	// % 2 == 1 ? android.R.color.white : R.color.f3f3f3));
	//
	// final boolean isDangKy =
	// "0".equals(cursor.getString(cursor.getColumnIndex(DichVuStore.service_status)));
	// TextView home_item_right_control_1_tv = (TextView)
	// convertView.findViewById(R.id.home_item_right_control_1_tv);
	//
	// home_item_right_control_1_tv.setText(isDangKy ? R.string.dangdung :
	// R.string.dangky);
	// home_item_right_control_1_tv.setTextColor(resources.getColor(R.color.c475055));
	//
	// ((TextView)
	// convertView.findViewById(R.id.home_item_right_control_2_tv)).setTextColor(resources.getColor(R.color.a73444));
	//
	// convertView.findViewById(R.id.home_item_right_control_icon).setBackgroundResource(R.drawable.home_dangky_2);
	// convertView.findViewById(R.id.home_item_right_control_2_icon).setBackgroundResource(R.drawable.home_moi_2);
	//
	// /**
	// *
	// */
	// int color = resources.getColor(R.color.dcdee1);
	// if (poistion % 2 == 0) {
	// color = resources.getColor(R.color.e7e9ec);
	// }
	//
	// convertView.findViewById(R.id.home_item_right_control).setBackgroundColor(color);
	//
	// /**
	// *
	// */
	// ImageView home_item_img_icon = (ImageView)
	// convertView.findViewById(R.id.home_item_img_icon);
	// View home_item_right_control_1 = (View)
	// convertView.findViewById(R.id.home_item_right_control_1);
	// View home_item_right_control_2 = (View)
	// convertView.findViewById(R.id.home_item_right_control_2);
	//
	// TextView home_item_tv_name = (TextView)
	// convertView.findViewById(R.id.home_item_tv_name);
	// TextView home_item_tv_link = (TextView)
	// convertView.findViewById(R.id.home_item_tv_link);
	// TextView home_item_tv_content = (TextView)
	// convertView.findViewById(R.id.home_item_tv_content);
	// home_item_tv_name.setText(cursor.getString(cursor.getColumnIndex(DichVuStore.service_name)));
	// home_item_tv_content.setText(cursor.getString(cursor.getColumnIndex(DichVuStore.service_content)));
	//
	// home_item_img_icon.setImageResource(R.drawable.no_avatar);
	// // show image
	// String service_icon =
	// cursor.getString(cursor.getColumnIndex(DichVuStore.service_icon)) + "";
	//
	// // ImageLoaderUtils.getInstance(context).displayImage(service_icon,
	// // home_item_img_icon, R.drawable.no_image);
	// Conts.showLogoDichvu(home_item_img_icon, service_icon);
	//
	// final ContentValues values = new ContentValues();
	// values.put("name",
	// cursor.getString(cursor.getColumnIndex(DichVuStore.service_name)));
	// values.put(DichVuStore.service_code,
	// cursor.getString(cursor.getColumnIndex(DichVuStore.service_code)));
	// values.put("content",
	// cursor.getString(cursor.getColumnIndex(DichVuStore.service_content)));
	// values.put(DichVuStore.ID,
	// cursor.getString(cursor.getColumnIndex(DichVuStore.ID)));
	// values.put("type", "dangky");
	// home_item_right_control_1.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// if (!isDangKy) {
	// dangKy(values);
	//
	// }
	// }
	// });
	//
	// home_item_right_control_2.setOnClickListener(new View.OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// }
	// });
	// convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new
	// OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// moiDVChoNhieuNguoi(id);
	// }
	// });
	//
	// convertView.findViewById(R.id.home_item_right_control).setVisibility(View.INVISIBLE);
	// }

	public abstract void dangKy(ContentValues values);

	// @Override
	// public View newView(Context context, Cursor cursor, ViewGroup parent) {
	// return new DichVuItemView(context);
	// }

	public abstract void moiDVChoNhieuNguoi(String id);

	private String textSearch = "";

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}
}
