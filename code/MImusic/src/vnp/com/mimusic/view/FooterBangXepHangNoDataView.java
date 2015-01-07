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
public class FooterBangXepHangNoDataView extends LinearLayout {
	private TextView no_data_message;

	public FooterBangXepHangNoDataView(Context context) {
		super(context);
		init();
	}

	public FooterBangXepHangNoDataView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bangxephang_nodata, this);
		no_data_message = (TextView) findViewById(R.id.no_data_message);
	}

	/**
	 * 
	 * @param needShow
	 * @param text
	 */
	public void setText(boolean needShow, String text) {
		no_data_message.setText(text);
		no_data_message.setVisibility(needShow ? VISIBLE : GONE);
	}

	/**
	 * 
	 * @param needShow
	 * @param text
	 */
	public void setText(boolean needShow, int text) {
		no_data_message.setText(text);
		no_data_message.setVisibility(needShow ? VISIBLE : GONE);
	}
}
