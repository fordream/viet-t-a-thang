package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.R.anim;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecommentItemDvBanChayView extends LinearLayout {

	public RecommentItemDvBanChayView(Context context) {
		super(context);
		init();
	}

	public RecommentItemDvBanChayView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.recomment_dvbanchay, this);
	}

	public void setBackgroud(int posititon) {
		findViewById(R.id.imageView1).setBackgroundColor(getResources().getColor(posititon % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		findViewById(R.id.dangky_dialog_content).setBackgroundColor(getResources().getColor(!(posititon % 2 == 0) ? android.R.color.white : R.color.f3f3f3));

	}

	public void setData(JSONObject jsonObject) {
		ImageView icondichvuc = (ImageView)findViewById(R.id.icondichvuc);
		ImageLoaderUtils.getInstance(getContext()).DisplayImage(Conts.getString(jsonObject, DichVu.service_icon), icondichvuc);
		 ((TextView)findViewById(R.id.dangky_dialog_content)).setText(Conts.getString(jsonObject, DichVu.service_name));
	}
}
