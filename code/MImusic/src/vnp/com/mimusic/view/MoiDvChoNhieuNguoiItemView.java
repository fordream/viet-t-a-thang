package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MoiDvChoNhieuNguoiItemView extends LinearLayout {

	public MoiDvChoNhieuNguoiItemView(Context context) {
		super(context);
		init();
	}

	public MoiDvChoNhieuNguoiItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moidvchonhieunguoi_item, this);
	}

}
