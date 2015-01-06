package vnp.com.mimusic.view;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MauMoiItemView extends LinearLayout {

	public MauMoiItemView(Context context) {
		super(context);
		init();
	}

	public MauMoiItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.maumoi_item, this);
	}

	public void setBackgroundItemColor(int position) {
		findViewById(R.id.maumoi_item_main).setBackgroundColor(getContext().getResources().getColor(position % 2 == 0 ? android.R.color.transparent : R.color.f3f3f3));
	}

	public void setData(JSONObject jsonObject) {
		((TextView) findViewById(R.id.bangxephang_item_tv_listdv)).setText("");
		try {
			((TextView) findViewById(R.id.bangxephang_item_tv_listdv)).setText(jsonObject.getString("content"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
