package vnp.com.mimusic.view.add;

import android.annotation.SuppressLint;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class OnTouchAnimation implements OnTouchListener {
	private Animation a_normal, a_selected, a_current;

	public OnTouchAnimation() {
		a_normal = new AlphaAnimation(1, 1f);
		a_normal.setDuration(0);
		a_normal.setFillAfter(true);

		a_selected = new AlphaAnimation(0.5f, 0.5f);
		a_selected.setDuration(0);
		a_selected.setFillAfter(true);

		a_current = a_normal;
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
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
				if (a_current.equals(a_normal)) {
					a_current = a_selected;
					v.startAnimation(a_current);
				}
			}
		}
		return false;
	}

}
