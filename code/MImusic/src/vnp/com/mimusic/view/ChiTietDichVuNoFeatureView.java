package vnp.com.mimusic.view;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
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
	}

	public void useValue2(boolean b) {
	}

	public void setData(Cursor cursor) {
		boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVuStore.service_status)));
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);
		TextView home_item_tv_name = (TextView) findViewById(R.id.home_item_tv_name);
		home_item_tv_name.setText(cursor.getString(cursor.getColumnIndex(DichVuStore.service_name)));

		home_item_img_icon.setImageResource(R.drawable.no_avatar);
		String service_icon = cursor.getString(cursor.getColumnIndex(DichVuStore.service_icon)) + "";
		Conts.showLogoDichvu(home_item_img_icon, service_icon);

		((TextView) findViewById(R.id.home_item_right_control_1_tv)).setText(isDangKy ? R.string.dangdung : R.string.dangky);

		Conts.setTextViewCursor(findViewById(R.id.gia), cursor, DichVuStore.service_price);
	}

	public void hiddenChitietdichvu_no_feature_dangky() {
	}

	public void showFooter() {
	}

	public void center() {
	}

	public void updateDangDung() {
		((TextView) findViewById(R.id.home_item_right_control_1_tv)).setText(R.string.dangdung);
	}
}