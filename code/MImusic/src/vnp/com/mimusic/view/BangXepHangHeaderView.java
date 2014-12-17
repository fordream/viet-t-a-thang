package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */
public class BangXepHangHeaderView extends LinearLayout {

	public BangXepHangHeaderView(Context context) {
		super(context);
		init();
	}

	public BangXepHangHeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bangxephang_header, this);
	}

}
