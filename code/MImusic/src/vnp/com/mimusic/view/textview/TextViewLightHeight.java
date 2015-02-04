package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.TextViewLightHeight
 * 
 * @author teemo
 * 
 */
public class TextViewLightHeight extends TextView {

	public TextViewLightHeight(Context context) {
		super(context);
		init();
	}

	public TextViewLightHeight(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewLightHeight(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {

		FontsUtils.getInstance().setTextFontsRobotoLight(this);
		setLineSpacing(1.3f, 1.3f);
	}
}
