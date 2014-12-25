package vnp.com.mimusic.util;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class VTAnimationUtils {

	public static void animationErrorEditText(View view) {
		Animation scale = new ScaleAnimation(1, 0.8f, 1, 0.8f, view.getWidth() / 2, view.getHeight() / 2);
		scale.setDuration(100);
		view.startAnimation(scale);
	}
}