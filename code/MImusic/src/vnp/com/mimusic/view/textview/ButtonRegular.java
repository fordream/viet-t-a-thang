package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * vnp.com.mimusic.view.textview.ButtonRegular
 * 
 * @author teemo
 * 
 */
public class ButtonRegular extends Button {

	public ButtonRegular(Context context) {
		super(context);
		init();
	}

	public ButtonRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ButtonRegular(Context context, AttributeSet attrs, int defStyleAttr) {
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
