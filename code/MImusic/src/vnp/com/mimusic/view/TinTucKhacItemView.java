package vnp.com.mimusic.view;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TinTucKhacItemView extends LinearLayout {

	public TinTucKhacItemView(Context context) {
		super(context);
		init();
	}

	public TinTucKhacItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tintuckhac_item, this);
	}

	public void setData(JSONObject jsonObject) {
		try {
			((TextView) findViewById(R.id.tintuckhac_item_tv_content)).setText(jsonObject.getString("title"));
		} catch (JSONException e) {
		}
	}

}
