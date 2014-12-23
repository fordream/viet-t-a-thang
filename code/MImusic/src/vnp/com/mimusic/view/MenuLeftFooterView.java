package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class MenuLeftFooterView extends LinearLayout {

	public MenuLeftFooterView(Context context) {
		super(context);
		init();
	}

	public MenuLeftFooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		try {
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_left_footer, this);
		} catch (Exception ex) {

		}
	}

}
