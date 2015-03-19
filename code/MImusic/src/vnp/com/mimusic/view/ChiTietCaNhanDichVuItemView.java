package vnp.com.mimusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.viettel.vtt.vdealer.R;

public class ChiTietCaNhanDichVuItemView extends HomeItemView {

	public ChiTietCaNhanDichVuItemView(Context context) {
		super(context);
		// init();
		findViewById(R.id.home_item_right_control).setVisibility(View.GONE);
	}

	public ChiTietCaNhanDichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		findViewById(R.id.home_item_right_control).setVisibility(View.GONE);
		// init();
	}

	// private void init() {
	// ((LayoutInflater)
	// getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietcanhandichvu_item,
	// this);
	// }

}
