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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReCommnetDialog extends BaseAdialog implements android.view.View.OnClickListener {

	public ReCommnetDialog(Context context, int theme) {
		super(context, theme);
	}

	public ReCommnetDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.recomment_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		LinearLayout recomment_list = (LinearLayout) findViewById(R.id.recomment_list);
		
		
		findViewById(R.id.recomment_close).setOnClickListener(this);
		// findViewById(R.id.dangky_dialog_register).setOnClickListener(this);
	}

	public void mDismiss() {
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
				ReCommnetDialog.this.dismiss();
			}
		});
		findViewById(R.id.recomment_main).startAnimation(animation);

	}

	@Override
	public int getLayout() {
		return R.layout.recomment;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.recomment_close) {
			mDismiss();
		}
	}

	public void mOpen() {
		mDismiss();
	}
}