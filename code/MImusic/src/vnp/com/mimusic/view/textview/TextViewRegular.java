package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.TextViewRegular
 * 
 * @author teemo
 * 
 */
public class TextViewRegular extends TextView {

	public TextViewRegular(Context context) {
		super(context);
		init();
	}

	public TextViewRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewRegular(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoMedium(this);
	}
}
