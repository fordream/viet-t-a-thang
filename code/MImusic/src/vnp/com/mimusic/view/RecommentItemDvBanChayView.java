package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.R.anim;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

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
		findViewById(R.id.imageView1).setBackgroundColor(getResources().getColor(posititon %2 ==0? android.R.color.white : R.color.f3f3f3));
		findViewById(R.id.dangky_dialog_content).setBackgroundColor(getResources().getColor(!(posititon %2 ==0)? android.R.color.white : R.color.f3f3f3));
		
		
	}
}
