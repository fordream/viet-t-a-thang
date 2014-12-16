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
public class HeaderView extends LinearLayout {

	public HeaderView(Context context) {
		super(context);
		init();
	}

	public HeaderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header, this);
	}

	public void setTextHeader(int dangnhap) {
		((TextView) findViewById(R.id.header_tv)).setText(dangnhap);
	}

	public void showButton(boolean leftShow, boolean rightShow) {
		findViewById(R.id.header_btn_left).setVisibility(leftShow ? View.VISIBLE : View.INVISIBLE);
		findViewById(R.id.header_btn_right).setVisibility(rightShow ? View.VISIBLE : View.INVISIBLE);
	}

}
