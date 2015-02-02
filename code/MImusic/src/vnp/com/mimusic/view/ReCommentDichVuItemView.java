package vnp.com.mimusic.view;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

//vnp.com.mimusic.view.ReCommentDichVuItemView
public class ReCommentDichVuItemView extends LinearLayout {

	public ReCommentDichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ReCommentDichVuItemView(Context context) {
		super(context);
		init();
	}

	public void setData(Cursor cursor) {
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.icon);

		Conts.getView(this, R.id.left).setVisibility(View.GONE);
		home_item_img_icon.setBackgroundResource(R.drawable.new_home_dv_bg_1);
		home_item_img_icon.setImageResource(R.drawable.tranfer);
		Conts.setTextView(findViewById(R.id.name), "");

		if (cursor != null) {
			String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";
			ImageLoaderUtils.getInstance(getContext()).DisplayImage(service_icon, home_item_img_icon, R.drawable.no_image);
			Conts.setTextViewCursor(findViewById(R.id.name), cursor, DichVu.service_name);

			int position = cursor.getPosition();
			if (position % 3 == 0) {
				home_item_img_icon.setBackgroundResource(R.drawable.new_home_dv_bg_1);
			} else if (position % 3 == 1) {
				home_item_img_icon.setBackgroundResource(R.drawable.new_home_dv_bg_2);
			} else if (position % 3 == 2) {
				home_item_img_icon.setBackgroundResource(R.drawable.new_home_dv_bg_3);
			}

			int poistion = cursor.getPosition();

			Conts.getView(this, R.id.left).setVisibility(poistion == 0 ? View.VISIBLE : View.GONE);
		}

	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_gallery_item, this);
	}
}
