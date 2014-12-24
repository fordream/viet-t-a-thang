package vnp.com.mimusic.adapter;

import java.util.Random;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.view.DichVuItemView;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
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
		String name = cursor.getString(cursor.getColumnIndex(DichVu.NAME));
		convertView.findViewById(R.id.home_item_main).setVisibility(name.toUpperCase().contains(textSearch.toUpperCase()) ? View.VISIBLE : View.GONE);

		/**
		 * set data
		 */
		((DichVuItemView) convertView).setData(cursor);
		int poistion = cursor.getPosition();
		Resources resources = context.getResources();
		convertView.findViewById(R.id.home_item_main).setBackgroundColor(resources.getColor(poistion % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		convertView.findViewById(R.id.home_item_img_icon).setBackgroundColor(resources.getColor(poistion % 2 == 1 ? android.R.color.white : R.color.f3f3f3));

		boolean isDangKy = new Random().nextBoolean();
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

		// home_item_img_icon.setBackgroundResource(R.drawable.icon_imusiz);

		home_item_tv_name.setText(cursor.getString(cursor.getColumnIndex(DichVu.NAME)));
		home_item_tv_link.setText(cursor.getString(cursor.getColumnIndex(DichVu.URL)));
		home_item_tv_content.setText(cursor.getString(cursor.getColumnIndex(DichVu.SHORTCONTENT)));

		final ContentValues values = new ContentValues();
		values.put("name", cursor.getString(cursor.getColumnIndex(DichVu.NAME)));
		values.put("content", cursor.getString(cursor.getColumnIndex(DichVu.SHORTCONTENT)));
		home_item_right_control_1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new DangKyDialog(v.getContext(), values).show();
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
				moiDVChoNhieuNguoi();
			}
		});

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new DichVuItemView(context);
	}

	public abstract void moiDVChoNhieuNguoi();

	private String textSearch = "";

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}
}
