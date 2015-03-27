package vnp.com.mimusic.view;

import android.content.Context;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

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
		findViewById(R.id.chitietdichvu_no_feature_main).setOnClickListener(null);
	}

	/**
	 * 
	 * @param needShow
	 * @param text
	 */
	public void setText(boolean needShow, String text) {
		no_data_message.setText(text);
		findViewById(R.id.chitietdichvu_no_feature_main).setVisibility(needShow ? VISIBLE : GONE);
	}

	/**
	 * 
	 * @param needShow
	 * @param text
	 */
	public void setText(boolean needShow, int text) {
		no_data_message.setText(text);
		findViewById(R.id.chitietdichvu_no_feature_main).setVisibility(needShow ? VISIBLE : GONE);
	}

	public void setTextNoDataX(boolean needShow, Spanned fromHtml) {
		no_data_message.setText(fromHtml);
		findViewById(R.id.chitietdichvu_no_feature_main).setVisibility(needShow ? VISIBLE : GONE);
		if (needShow) {
			no_data_message.setGravity(Gravity.LEFT);
		}
	}

	public void setTextNoDataX2(boolean needShow, Spanned fromHtml) {
		no_data_message.setText(fromHtml);
		findViewById(R.id.chitietdichvu_no_feature_main).setVisibility(needShow ? VISIBLE : GONE);
		no_data_message.setGravity(Gravity.LEFT);
	}
}
