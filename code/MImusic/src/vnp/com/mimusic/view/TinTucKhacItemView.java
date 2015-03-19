package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.viettel.vtt.vdealer.R;

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
		Conts.setTextView(findViewById(R.id.tintuckhac_item_tv_content), jsonObject, "title");
	}

	public void setData(int position) {
		//findViewById(R.id.imageView1).setBackgroundResource(position % 2 == 0 ? R.drawable.new_tintuc_khac_choice_1 : R.drawable.new_tintuc_khac_choice_2);
		//((TextView) findViewById(R.id.tintuckhac_item_tv_content)).setTextColor(getContext().getResources().getColor(position % 2 == 0 ? R.color.new_tintuc_khac_1 : R.color.new_tintuc_khac_2));
	}

}
