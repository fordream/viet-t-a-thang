package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.TextViewLight
 * 
 * @author teemo
 * 
 */
public class TextViewLight extends TextView {

	public TextViewLight(Context context) {
		super(context);
		init();
	}

	public TextViewLight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewLight(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

		FontsUtils.getInstance().setTextFontsRobotoLight(this);
	}
}
