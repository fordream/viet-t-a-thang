package vnp.com.mimusic.view;

import vnp.com.mimusic.view.add.OnTouchAnimation;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;

//vnp.com.mimusic.view.BaseButton
public class BaseButton extends Button {

	public BaseButton(Context context) {
		super(context);
		init();
	}

	public BaseButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BaseButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		// a_normal = new AlphaAnimation(1, 1f);
		// a_normal.setDuration(0);
		// a_normal.setFillAfter(true);
		//
		// a_selected = new AlphaAnimation(0.5f, 0.5f);
		// a_selected.setDuration(0);
		// a_selected.setFillAfter(true);
		//
		// a_current = a_normal;
		setOnTouchListener(new OnTouchAnimation());
	}

	// private Animation a_normal, a_selected, a_current;
	//
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// if (event.getAction() == MotionEvent.ACTION_DOWN) {
	// a_current = a_selected;
	// startAnimation(a_current);
	// } else if (event.getAction() == MotionEvent.ACTION_UP ||
	// event.getAction() == MotionEvent.ACTION_CANCEL) {
	// a_current = a_normal;
	// startAnimation(a_current);
	// } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
	// float x = event.getX();
	// float y = event.getY();
	// float w = getWidth();
	// float h = getHeight();
	//
	// if (x < 0 || x > w) {
	// if (a_current.equals(a_selected)) {
	// a_current = a_normal;
	// startAnimation(a_current);
	// }
	// } else if (y < 0 || y > h) {
	// if (a_current.equals(a_selected)) {
	// a_current = a_normal;
	// startAnimation(a_current);
	// }
	// } else {
	// if (a_current.equals(a_normal)) {
	// a_current = a_selected;
	// startAnimation(a_current);
	// }
	// }
	// }
	// return super.onTouchEvent(event);
	// }
}
