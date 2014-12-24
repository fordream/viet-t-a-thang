package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import vnp.com.mimusic.base.BaseAdialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;

public abstract class DateDialog extends BaseAdialog implements android.view.View.OnClickListener {

	public DateDialog(Context context, int theme) {
		super(context, theme);
	}

	public DateDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		findViewById(R.id.date_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		findViewById(R.id.date_close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(false);
			}
		});

		findViewById(R.id.date_done).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(true);
			}
		});
	}

	public void mDismiss(final boolean isSendData) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_out_bottom);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				DateDialog.this.dismiss();
				if (isSendData) {
					sendData("", "", "");
				}
			}
		});
		findViewById(R.id.date_dialog_main).startAnimation(animation);

	}

	public abstract void sendData(String date, String month, String year);

	@Override
	public int getLayout() {
		return R.layout.date_dialog;
	}

	@Override
	public void onClick(View v) {
	}

}