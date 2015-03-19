package vnp.com.mimusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.viettel.vtt.vdealer.R;

//vnp.com.mimusic.view.DichVuItemView
public class ChonTatCaView extends LinearLayout {
	public ChonTatCaView(Context context) {
		super(context);
		init();
	}

	public ChonTatCaView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chonall_item, this);
	}
}