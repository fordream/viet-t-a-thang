package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class RecommentItemView extends LinearLayout {

	public RecommentItemView(Context context) {
		super(context);
		init();
	}

	public RecommentItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recomment_item, this);
	}

	public void setData(JSONObject jsonObject) {
		String text = String.format("%s (%s)", Conts.getString(jsonObject, "name"), Conts.getSDT(Conts.getString(jsonObject, "phone")));
		Conts.setTextView(findViewById(R.id.home_item_tv_name), text);
		Conts.setTextView(findViewById(R.id.home_item_tv_name), jsonObject, "name");
		Conts.setTextView(findViewById(R.id.home_item_tv_content_sdt), jsonObject, "phone");
		Conts.getSDT(findViewById(R.id.home_item_tv_content_sdt));
	}
}