package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
//vnp.com.mimusic.view.ChiTietDichVuNoFeatureView
public class ChiTietDichVuNoFeatureView extends LinearLayout {

	public ChiTietDichVuNoFeatureView(Context context) {
		super(context);
		init();
	}

	public ChiTietDichVuNoFeatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietdichvu_no_feature, this);
	}

}
