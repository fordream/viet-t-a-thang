package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.TextViewMedium
 * 
 * @author teemo
 * 
 */
public class TextViewMedium extends TextView {

	public TextViewMedium(Context context) {
		super(context);
		init();
	}

	public TextViewMedium(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public TextViewMedium(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoMedium(this);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		try {
			super.onDraw(canvas);
		} catch (Exception exception) {
		} catch (Error e) {
		}
	}
}
