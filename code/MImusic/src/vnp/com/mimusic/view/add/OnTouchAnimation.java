package vnp.com.mimusic.view.add;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class OnTouchAnimation implements OnTouchListener {
	private Animation a_normal, a_selected, a_current;

	public OnTouchAnimation() {

	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {

		/**
		 */
		// a_normal = new AlphaAnimation(1, 1f);
		if (a_normal == null) {
			a_normal = new ScaleAnimation(1, 1, 1, 1, v.getWidth() / 2, v.getHeight() / 2);
			a_normal.setDuration(0);
			a_normal.setFillAfter(true);
		}
		// a_selected = new AlphaAnimation(0.5f, 0.5f);
		if (a_selected == null) {
			a_selected = new ScaleAnimation(0.9f, 0.9f, 0.9f, 0.9f, v.getWidth() / 2, v.getHeight() / 2);
			a_selected.setDuration(0);
			a_selected.setFillAfter(true);
		}
		if (a_current == null) {
			a_current = a_normal;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			a_current = a_selected;
			v.startAnimation(a_current);
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
			a_current = a_normal;
			v.startAnimation(a_current);
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			float x = event.getX();
			float y = event.getY();
			float w = v.getWidth();
			float h = v.getHeight();
			if (x < 0 || x > w) {
				if (a_current.equals(a_selected)) {
					a_current = a_normal;
					v.startAnimation(a_current);
				}
			} else if (y < 0 || y > h) {
				if (a_current.equals(a_selected)) {
					a_current = a_normal;
					v.startAnimation(a_current);
				}
			} else {
				// if (a_current.equals(a_normal)) {
				// a_current = a_selected;
				// v.startAnimation(a_current);
				// }
			}
		}
		return false;
	}

}
