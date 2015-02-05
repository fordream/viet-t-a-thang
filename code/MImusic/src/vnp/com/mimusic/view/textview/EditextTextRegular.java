package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * vnp.com.mimusic.view.textview.EditextTextRegular
 * 
 * @author teemo
 * 
 */
public class EditextTextRegular extends EditText {

	public EditextTextRegular(Context context) {
		super(context);
		init();
	}

	public EditextTextRegular(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EditextTextRegular(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoRegular(this);
	}
}
