package vnp.com.mimusic.view;

import vnp.com.db.TinTuc;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TintucItemView extends LinearLayout {

	public TintucItemView(Context context) {
		super(context);
		init();
	}

	public TintucItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tintuc_item, this);
	}

	public void setData(Cursor item) {
		ImageView tintuc_item_img_icon = (ImageView) findViewById(R.id.tintuc_item_img_icon);
		TextView tintuc_item_tv_name = (TextView) findViewById(R.id.tintuc_item_tv_name);
		TextView tintuc_item_tv_date = (TextView) findViewById(R.id.tintuc_item_tv_date);
		TextView tintuc_item_tv_content = (TextView) findViewById(R.id.tintuc_item_tv_content);
		tintuc_item_tv_name.setText(Conts.getStringCursor(item, TinTuc.title));
		tintuc_item_tv_date.setText(Conts.getStringCursor(item, TinTuc.public_time));
		tintuc_item_tv_content.setText(Conts.getStringCursor(item, TinTuc.header));

		String images = Conts.getStringCursor(item, TinTuc.images);

		ImageLoaderUtils.getInstance(getContext()).displayImage(images, tintuc_item_img_icon, R.drawable.no_avatar);
	}
}