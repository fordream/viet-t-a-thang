package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * vnp.com.mimusic.view.VTTextView
 * 
 * @author teemo
 * 
 */
public class VTTextView extends TextView {

	public VTTextView(Context context) {
		super(context);
		init();
	}

	public VTTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public VTTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		setLineSpacing(1.3f, 1.3f);
	}
}
