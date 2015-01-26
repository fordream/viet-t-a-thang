package vnp.com.mimusic.view;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.DichVuItemView
public class DichVuItemView extends LinearLayout {
	public DichVuItemView(Context context) {
		super(context);
		init();
	}

	public DichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public void setData(Cursor cursor) {
		findViewById(R.id.home_item_header).setVisibility(View.GONE);
	}

	public void initData(String string) {
		((TextView) findViewById(R.id.home_item_tv_name)).setText(string);
		findViewById(R.id.home_item_img_icon).setVisibility(INVISIBLE);
		findViewById(R.id.home_item_right_control).setVisibility(GONE);
		findViewById(R.id.home_item_tv_content).setVisibility(View.GONE);
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dichvu_item, this);
		findViewById(R.id.home_item_header).setOnClickListener(null);
	}

	public void setDataCusor(Cursor cursor, int poistion) {
		findViewById(R.id.home_item_main).setBackgroundColor(getResources().getColor(poistion % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		findViewById(R.id.home_item_img_icon).setBackgroundColor(getResources().getColor(poistion % 2 == 1 ? android.R.color.white : R.color.f3f3f3));

		final boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVu.service_status)));
		int mColor = getResources().getColor(isDangKy ? R.color.c475055 : (poistion == 0 ? android.R.color.white : R.color.c475055));
		TextView home_item_right_control_1_tv = (TextView) findViewById(R.id.home_item_right_control_1_tv);

		home_item_right_control_1_tv.setText(isDangKy ? R.string.dangdung : R.string.dangky);
		home_item_right_control_1_tv.setTextColor(mColor);

		((TextView) findViewById(R.id.home_item_right_control_2_tv)).setTextColor(getResources().getColor(poistion == 0 ? android.R.color.white : R.color.a73444));

		findViewById(R.id.home_item_right_control_icon).setBackgroundResource(isDangKy ? R.drawable.home_dangky_3_dadangky : (poistion == 0 ? R.drawable.home_dangky_1 : R.drawable.home_dangky_2));
		findViewById(R.id.home_item_right_control_2_icon).setBackgroundResource(poistion == 0 ? R.drawable.home_moi_1 : R.drawable.home_moi_2);

		/**
		 * 
		 */
		int color = getResources().getColor(R.color.dcdee1);
		if (poistion % 2 == 0) {
			color = getResources().getColor(R.color.e7e9ec);
		}

		if (poistion == 0) {
			color = getResources().getColor(R.color.a73444);
		}

		findViewById(R.id.home_item_right_control).setBackgroundColor(color);

		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);

		TextView home_item_tv_name = (TextView) findViewById(R.id.home_item_tv_name);
		TextView home_item_tv_content = (TextView) findViewById(R.id.home_item_tv_content);
		TextView home_item_header_tv = (TextView) findViewById(R.id.home_item_header_tv);

		home_item_tv_name.setText(cursor.getString(cursor.getColumnIndex(DichVu.service_name)));
		home_item_tv_content.setText(Html.fromHtml(cursor.getString(cursor.getColumnIndex(DichVu.service_content)) + ""));

		home_item_img_icon.setImageResource(R.drawable.no_avatar);
		// show image
		String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";

		ImageLoaderUtils.getInstance(getContext()).DisplayImage(service_icon, home_item_img_icon, BitmapFactory.decodeResource(getResources(), R.drawable.no_image));
	}

}