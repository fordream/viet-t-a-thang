package vnp.com.mimusic.view;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.ChiTietDichVuNoFeatureView
public class ChiTietDichVuNoFeatureView extends LinearLayout {

	public ChiTietDichVuNoFeatureView(Context context) {
		super(context);
		init();
	}

	public ChiTietDichVuNoFeatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietdichvu_no_feature, this);
	}

	public void setBackground(int white) {
		findViewById(R.id.chitietdichvu_no_feature_main).setBackgroundColor(getResources().getColor(white));
	}

	public void useValue2(boolean b) {
		findViewById(R.id.chitietdichvu_no_feature_gia_1).setVisibility(!b ? View.VISIBLE : View.GONE);
		findViewById(R.id.chitietdichvu_no_feature_gia_2).setVisibility(b ? View.VISIBLE : View.GONE);
	}

	public void setData(Cursor cursor) {
		boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVu.service_status)));
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);
		TextView home_item_tv_name = (TextView) findViewById(R.id.home_item_tv_name);
		TextView chitietdichvu_no_feature_gia_1 = (TextView) findViewById(R.id.chitietdichvu_no_feature_gia_1);
		TextView chitietdichvu_no_feature_gia_2 = (TextView) findViewById(R.id.chitietdichvu_no_feature_gia_2);
		home_item_tv_name.setText(cursor.getString(cursor.getColumnIndex(DichVu.service_name)));
		chitietdichvu_no_feature_gia_1.setText(cursor.getString(cursor.getColumnIndex(DichVu.service_price)));
		chitietdichvu_no_feature_gia_2.setText(String.format(getContext().getString(R.string.format_gia), cursor.getString(cursor.getColumnIndex(DichVu.service_price))));
		home_item_img_icon.setImageResource(R.drawable.no_avatar);
		// show image
		String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";
		ImageLoaderUtils.getInstance(getContext()).DisplayImage(service_icon, home_item_img_icon, BitmapFactory.decodeResource(getResources(), R.drawable.no_image));

		findViewById(R.id.chitietdichvu_no_feature_dangky).setEnabled(isDangKy ? false : true);
		((TextView) findViewById(R.id.dangkyx)).setText(isDangKy ? R.string.dangdung : R.string.dangky);
		findViewById(R.id.dangkyxm1).setVisibility(isDangKy ? View.GONE : View.VISIBLE);
	}

	public void hiddenChitietdichvu_no_feature_dangky() {
		findViewById(R.id.chitietdichvu_no_feature_dangky).setEnabled(false);
		((TextView) findViewById(R.id.dangkyx)).setText(R.string.dangdung);
		findViewById(R.id.dangkyxm1).setVisibility(View.GONE);
	}

	public void showFooter() {
		findViewById(R.id.chitietdichvu_no_feature_main_footer).setVisibility(View.VISIBLE);
	}
}