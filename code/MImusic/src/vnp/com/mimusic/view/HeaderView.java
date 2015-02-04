package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
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

	public void setButtonLeftImage(boolean needShow, int btnBack) {
		findViewById(R.id.header_btn_left).setVisibility(needShow ? View.VISIBLE : View.GONE);
		((ImageButton) findViewById(R.id.header_btn_left)).setImageResource(btnBack);
	}

	public void setButtonRightImage(boolean needShow, int btnBack) {
		((ImageButton) findViewById(R.id.header_btn_right)).setImageResource(btnBack);
		findViewById(R.id.header_btn_right).setVisibility(needShow ? View.VISIBLE : View.GONE);
	}

	public void setButtonMoi(boolean needShow) {
		findViewById(R.id.header_btn_right_moi).setVisibility(needShow ? View.VISIBLE : View.GONE);

	}

	public void setTextHeader(String title) {
		((TextView) findViewById(R.id.header_tv)).setText(title);
	}

	public void showHeadderSearch() {
		findViewById(R.id.header_search).setVisibility(View.VISIBLE);
		showHeader(true);
	}

	public void showHeader(boolean b) {
		findViewById(R.id.header_main_content).setVisibility(b ? INVISIBLE : GONE);
	}
}
