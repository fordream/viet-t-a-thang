package vnp.com.mimusic.view.textview;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.textview.EditextTextThin
 * 
 * @author teemo
 * 
 */
public class EditextTextThin extends EditText {

	public EditextTextThin(Context context) {
		super(context);
		init();
	}

	public EditextTextThin(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public EditextTextThin(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		FontsUtils.getInstance().setTextFontsRobotoThin(this);
	}
}
