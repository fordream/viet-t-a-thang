package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class ChiTietCaNhanBangXepHangItemView extends LinearLayout {

	public ChiTietCaNhanBangXepHangItemView(Context context) {
		super(context);
		init();
	}

	public ChiTietCaNhanBangXepHangItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietcanhanbangxephang_item, this);
	}

}
