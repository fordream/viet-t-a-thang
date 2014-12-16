package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ChiTietCaNhanDichVuItemView extends LinearLayout {

	public ChiTietCaNhanDichVuItemView(Context context) {
		super(context);
		init();
	}

	public ChiTietCaNhanDichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietcanhandichvu_item, this);
	}

}
