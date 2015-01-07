package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.HeaderView
 * 
 * @author teemo
 * 
 */
public class HeaderListTextView extends LinearLayout {
	private TextView header_list_text_tv;

	public HeaderListTextView(Context context) {
		super(context);
		init();
	}

	public HeaderListTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.header_list_text, this);
		header_list_text_tv = (TextView) findViewById(R.id.header_list_text_tv);
	}

	/**
	 * 
	 * @param needShow
	 * @param text
	 */
	public void setText(boolean needShow, String text) {
		header_list_text_tv.setText(text);
		header_list_text_tv.setVisibility(needShow ? VISIBLE : GONE);
	}

	/**
	 * 
	 * @param needShow
	 * @param text
	 */
	public void setText(boolean needShow, int text) {
		header_list_text_tv.setText(text);
		header_list_text_tv.setVisibility(needShow ? VISIBLE : GONE);
	}
}
