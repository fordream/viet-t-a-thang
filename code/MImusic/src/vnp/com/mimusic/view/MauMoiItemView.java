package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

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

}
