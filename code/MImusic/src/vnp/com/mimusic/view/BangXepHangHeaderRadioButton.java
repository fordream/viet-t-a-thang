package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.FontsUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RadioButton;

//vnp.com.mimusic.view.BangXepHangHeaderRadioButton
public class BangXepHangHeaderRadioButton extends RadioButton {

	public BangXepHangHeaderRadioButton(Context context) {
		super(context);

		init();
	}

	public BangXepHangHeaderRadioButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public BangXepHangHeaderRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
	}

	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		try {
			setTextColor(getResources().getColor(checked ? R.color.bangxephang_header_active : R.color.bangxephang_header_un_active));
			// AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 1.0f);
			// if (!checked) {
			// alphaAnimation = new AlphaAnimation(0.8f, 0.8f);
			// }

			// alphaAnimation.setFillAfter(false);
			// startAnimation(alphaAnimation);
			if (checked) {
				// setTypeface(Typeface.DEFAULT_BOLD);

				FontsUtils.getInstance().setTextFontsRobotoMedium(this);
			} else {
				// setTypeface(Typeface.DEFAULT);
				FontsUtils.getInstance().setTextFontsRobotoRegular(this);
			}

		} catch (Exception ex) {
		}
	}

}