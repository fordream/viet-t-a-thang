package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.TextViewBold
 * 
 * @author teemo
 * 
 */
public class TextViewBold extends TextView {

	public TextViewBold(Context context) {
		super(context);
		init();
	}

	public TextViewBold(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoBold(this);
	}
}
