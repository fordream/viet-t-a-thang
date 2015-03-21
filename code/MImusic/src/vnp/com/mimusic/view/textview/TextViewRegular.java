package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.graphics.Canvas;
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

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception exception) {
		} catch (Error e) {
		}
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoRegular(this);
	}
}
