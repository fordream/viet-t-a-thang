package vnp.com.mimusic.view;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
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

		final boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVu.service_status)));
		int poistion = cursor.getPosition();
		Resources resources = getContext().getResources();

		/**
		 * config backgroud for item
		 */
		findViewById(R.id.home_item_main).setBackgroundResource(poistion % 2 == 0 ? R.drawable.tranfer : R.drawable.new_dichvu_item_2_bg);
		findViewById(R.id.home_item_img_icon).setBackgroundResource(poistion % 2 == 0 ? R.drawable.new_dichvu_icon_bg_0 : R.drawable.new_dichvu_icon_bg_1);

		Conts.setTextResource(findViewById(R.id.home_item_right_control_1_tv), isDangKy ? R.string.dangdung : R.string.dangky);
		Conts.setTextViewCursor(findViewById(R.id.home_item_tv_name), cursor, DichVu.service_name);
		Conts.setTextViewCursor(findViewById(R.id.home_item_tv_content), cursor, DichVu.service_content);

		// show image of service
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);
		String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";
		ImageLoaderUtils.getInstance(getContext()).DisplayImage(service_icon, home_item_img_icon, BitmapFactory.decodeResource(getResources(), R.drawable.no_image));
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.dichvu_item, this);
		findViewById(R.id.home_item_header).setOnClickListener(null);
	}
}