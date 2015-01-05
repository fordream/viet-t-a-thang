package vnp.com.mimusic.view;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import android.content.Context;
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

	public void setData(JSONObject item) {
		ImageView tintuc_item_img_icon = (ImageView) findViewById(R.id.tintuc_item_img_icon);
		TextView tintuc_item_tv_name = (TextView) findViewById(R.id.tintuc_item_tv_name);
		TextView tintuc_item_tv_date = (TextView) findViewById(R.id.tintuc_item_tv_date);
		TextView tintuc_item_tv_content = (TextView) findViewById(R.id.tintuc_item_tv_content);
		tintuc_item_img_icon.setImageResource(R.drawable.no_avatar);
		
		try {
			tintuc_item_tv_name.setText(item.getString("title"));
			tintuc_item_tv_date.setText(item.getString("public_time"));
			tintuc_item_tv_content.setText(item.getString("header"));
		} catch (JSONException e) {
		}

	}
}