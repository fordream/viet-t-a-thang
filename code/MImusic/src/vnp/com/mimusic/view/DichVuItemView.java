package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

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

		final boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVuStore.service_status)));
		int poistion = cursor.getPosition();

		/**
		 * config backgroud for item
		 */
		findViewById(R.id.home_item_main).setBackgroundResource(poistion % 2 == 0 ? R.drawable.tranfer : R.drawable.new_dichvu_item_2_bg);
		findViewById(R.id.home_item_img_icon).setBackgroundResource(poistion % 2 == 0 ? R.drawable.new_dichvu_icon_bg_0 : R.drawable.new_dichvu_icon_bg_1);

		Conts.setTextResource(findViewById(R.id.home_item_right_control_1_tv), isDangKy ? R.string.dangdung : R.string.dangky);
		Conts.setTextViewCursor(findViewById(R.id.home_item_tv_name), cursor, DichVuStore.service_name);
		Conts.setTextViewCursor(findViewById(R.id.home_item_tv_content), cursor, DichVuStore.service_content);

		// show image of service
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);
		String service_icon = cursor.getString(cursor.getColumnIndex(DichVuStore.service_icon)) + "";
		// ImageLoaderUtils.getInstance(getContext()).displayImage(service_icon,
		// home_item_img_icon, R.drawable.no_image);

		Conts.showLogoDichvu(home_item_img_icon, service_icon);
		findViewById(R.id.home_item_right_control).setBackgroundResource(poistion % 2 == 0 ? R.drawable.home_dv_bg_x_0 : R.drawable.home_dv_bg_x_1);

	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dichvu_item, this);
		findViewById(R.id.home_item_header).setOnClickListener(null);
	}

//	public void setDataHome(Cursor cursor) {
//		int poistion = cursor.getPosition() + 1;
//		/**
//		 * config backgroud for item
//		 */
//		findViewById(R.id.home_item_main).setBackgroundResource(poistion % 2 == 0 ? R.drawable.tranfer : R.drawable.new_dichvu_item_2_bg);
//		findViewById(R.id.home_item_img_icon).setBackgroundResource(poistion % 2 == 0 ? R.drawable.new_dichvu_icon_bg_0 : R.drawable.new_dichvu_icon_bg_1);
//	}

	public void setData(JSONObject object, int poistion) {
		DichVuStore dichVuStore = new DichVuStore(getContext());
		findViewById(R.id.home_item_header).setVisibility(View.GONE);

		final boolean isDangKy = dichVuStore.isRegister(Conts.getString(object, DichVuStore.service_code));
		// "0".equals(cursor.getString(cursor.getColumnIndex(DichVuStore.service_status)));
		// int poistion = cursor.getPosition();

		/**
		 * config backgroud for item
		 */
		findViewById(R.id.home_item_main).setBackgroundResource(poistion % 2 == 0 ? R.drawable.tranfer : R.drawable.new_dichvu_item_2_bg);
		findViewById(R.id.home_item_img_icon).setBackgroundResource(poistion % 2 == 0 ? R.drawable.new_dichvu_icon_bg_0 : R.drawable.new_dichvu_icon_bg_1);

		Conts.setTextResource(findViewById(R.id.home_item_right_control_1_tv), isDangKy ? R.string.dangdung : R.string.dangky);
		Conts.setTextView(findViewById(R.id.home_item_tv_name), object, DichVuStore.service_name);
		Conts.setTextView(findViewById(R.id.home_item_tv_content), object, DichVuStore.service_content);

		// show image of service
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);
		String service_icon = Conts.getString(object, DichVuStore.service_icon);// cursor.getString(cursor.getColumnIndex(DichVuStore.service_icon))
																				// +
																				// "";
		// ImageLoaderUtils.getInstance(getContext()).displayImage(service_icon,
		// home_item_img_icon, R.drawable.no_image);

		Conts.showLogoDichvu(home_item_img_icon, service_icon);
		findViewById(R.id.home_item_right_control).setBackgroundResource(poistion % 2 == 0 ? R.drawable.home_dv_bg_x_0 : R.drawable.home_dv_bg_x_1);
	}

}