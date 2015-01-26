package vnp.com.mimusic.adapter;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import vnp.com.mimusic.view.DichVuItemView;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public abstract class DichVuAdapter extends CursorAdapter {

	public DichVuAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new DichVuItemView(context);
		}

		/*
		 * kiem tra xem co hien thi item nay khong
		 */
		String name = cursor.getString(cursor.getColumnIndex(DichVu.service_name));
		final String id = cursor.getString(cursor.getColumnIndex(DichVu.ID));
		convertView.findViewById(R.id.home_item_main).setVisibility(name.toUpperCase().contains(textSearch.toUpperCase()) ? View.VISIBLE : View.GONE);

		/**
		 * set data
		 */
		((DichVuItemView) convertView).setData(cursor);

		int poistion = cursor.getPosition();
		Resources resources = context.getResources();
		convertView.findViewById(R.id.home_item_main).setBackgroundColor(resources.getColor(poistion % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		convertView.findViewById(R.id.home_item_img_icon).setBackgroundColor(resources.getColor(poistion % 2 == 1 ? android.R.color.white : R.color.f3f3f3));

		final boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVu.service_status)));
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
		TextView home_item_tv_name = (TextView) convertView.findViewById(R.id.home_item_tv_name);
		TextView home_item_tv_content = (TextView) convertView.findViewById(R.id.home_item_tv_content);
		home_item_tv_name.setText(cursor.getString(cursor.getColumnIndex(DichVu.service_name)));
		home_item_tv_content.setText(cursor.getString(cursor.getColumnIndex(DichVu.service_content)));

		// show image of service
		String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";
		ImageLoaderUtils.getInstance(context).DisplayImage(service_icon, home_item_img_icon, BitmapFactory.decodeResource(context.getResources(), R.drawable.no_image));

		/**
		 * add action
		 */
		ContentValues values = new ContentValues();
		values.put("name", String.format(context.getString(R.string.title_dangky), cursor.getString(cursor.getColumnIndex(DichVu.service_name))));
		values.put(DichVu.service_code, cursor.getString(cursor.getColumnIndex(DichVu.service_code)));
		String content = String.format(context.getString(R.string.xacnhandangky_form), Conts.getStringCursor(cursor, DichVu.service_name), Conts.getStringCursor(cursor, DichVu.service_price));
		values.put("content", content);
		values.put(DichVu.ID, cursor.getString(cursor.getColumnIndex(DichVu.ID)));
		values.put("type", "dangky");
		home_item_right_control_1.setOnClickListener(new DangKyClickListener(values, isDangKy));
		convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new MoiDichVuClickListener(id));
	}

	public abstract void dangKy(ContentValues values);

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new DichVuItemView(context);
	}

	public abstract void moiDVChoNhieuNguoi(String id);

	private String textSearch = "";

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}

	private class MoiDichVuClickListener implements OnClickListener {
		private String id;

		public MoiDichVuClickListener(String id) {
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			moiDVChoNhieuNguoi(id);
		}
	}

	private class DangKyClickListener implements OnClickListener {
		private ContentValues id;
		private boolean isDangky = false;

		public DangKyClickListener(ContentValues id, boolean isDangky) {
			this.id = id;
			this.isDangky = isDangky;
		}

		@Override
		public void onClick(View v) {
			if (!isDangky) {
				dangKy(id);
			}
		}
	}
}
