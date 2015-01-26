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
		final boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVu.service_status)));

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
		convertView.findViewById(R.id.home_item_right_control_1).setOnClickListener(new DangKyClickListener(values, isDangKy));
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
