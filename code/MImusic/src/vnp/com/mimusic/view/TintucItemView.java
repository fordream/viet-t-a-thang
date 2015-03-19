package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.db.datastore.TintucStore;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

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
//
//	public void setData(Cursor item) {
//		ImageView tintuc_item_img_icon = (ImageView) findViewById(R.id.tintuc_item_img_icon);
//		TextView tintuc_item_tv_name = (TextView) findViewById(R.id.tintuc_item_tv_name);
//		TextView tintuc_item_tv_date = (TextView) findViewById(R.id.tintuc_item_tv_date);
//		TextView tintuc_item_tv_content = (TextView) findViewById(R.id.tintuc_item_tv_content);
//		tintuc_item_tv_name.setText(Conts.getStringCursor(item, TintucStore.title));
//		tintuc_item_tv_date.setText(Conts.getStringCursor(item, TintucStore.public_time));
//		tintuc_item_tv_content.setText(Conts.getStringCursor(item, TintucStore.header));
//		String images = Conts.getStringCursor(item, TintucStore.images);
//		Conts.showLogoTinTuc(tintuc_item_img_icon, images);
//	}

	public void setData(JSONObject item) {
		ImageView tintuc_item_img_icon = (ImageView) findViewById(R.id.tintuc_item_img_icon);
		TextView tintuc_item_tv_name = (TextView) findViewById(R.id.tintuc_item_tv_name);
		TextView tintuc_item_tv_date = (TextView) findViewById(R.id.tintuc_item_tv_date);
		TextView tintuc_item_tv_content = (TextView) findViewById(R.id.tintuc_item_tv_content);
		tintuc_item_tv_name.setText(Conts.getString(item, TintucStore.title));
		tintuc_item_tv_date.setText(Conts.getString(item, TintucStore.public_time));
		tintuc_item_tv_content.setText(Conts.getString(item, TintucStore.header));
		String images = Conts.getString(item, TintucStore.images);
		Conts.showLogoTinTuc(tintuc_item_img_icon, images);
	}
}