package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
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
		findViewById(R.id.icondichvuc).setBackgroundResource(posititon % 2 == 0 ? R.drawable.recomment_bg_mx1 : R.drawable.recomment_bg_mx2);
		findViewById(R.id.dangky_dialog_content).setBackgroundResource(posititon % 2 == 0 ? R.drawable.recomment_bg_mxx1 : R.drawable.recomment_bg_mxx2);
	}

	public void setData(JSONObject jsonObject) {
		ImageView icondichvuc = (ImageView) findViewById(R.id.icondichvuc);
		String url = Conts.getString(jsonObject, DichVu.service_icon);

		ImageLoaderUtils.getInstance(getContext()).displayImage(url, icondichvuc, R.drawable.no_image);
		((TextView) findViewById(R.id.dangky_dialog_content)).setText(Conts.getString(jsonObject, DichVu.service_name));
	}
}
