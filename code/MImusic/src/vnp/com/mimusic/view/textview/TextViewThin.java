package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.TextViewThin
 * 
 * @author teemo
 * 
 */
public class TextViewThin extends TextView {

	public TextViewThin(Context context) {
		super(context);
		init();
	}

	public TextViewThin(Context context, AttributeSet attrs) {
		super(context, attrs);
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

	public TextViewThin(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoThin(this);
	}
}
