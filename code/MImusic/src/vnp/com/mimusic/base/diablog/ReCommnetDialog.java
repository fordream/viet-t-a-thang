package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.view.RecommentItemView;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public abstract class ReCommnetDialog extends BaseAdialog implements android.view.View.OnClickListener {

	public ReCommnetDialog(Context context, int theme) {
		super(context, theme);
	}

	public ReCommnetDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.recomment_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_top));

		findViewById(R.id.recomment_icon_bottom).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(0);
			}
		});
		LinearLayout recomment_list = (LinearLayout) findViewById(R.id.recomment_list);
		for (int i = 0; i < 4; i++) {
			RecommentItemView recommentItemView = new RecommentItemView(getContext());
			recomment_list.addView(recommentItemView);
			recommentItemView.setOnClickListener(new RecommentItemOnClick());
		}

		findViewById(R.id.recomment_close).setOnClickListener(this);
		findViewById(R.id.recomment_dv_1).setOnClickListener(this);
		findViewById(R.id.recomment_dv_2).setOnClickListener(this);
		findViewById(R.id.recomment_dv_3).setOnClickListener(this);
		findViewById(R.id.recomment_dv_4).setOnClickListener(this);
	}

	public void mDismiss(final int index) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_out_top);
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
				if (index == 0) {

				} else if (index == 1) {
					openDichvuDetail();
				} else if (index == 2) {
					openMoiContact();
				}
			}
		});
		findViewById(R.id.recomment_main).startAnimation(animation);

	}

	public abstract void openDichvuDetail();

	public abstract void openMoiContact();

	@Override
	public int getLayout() {
		return R.layout.recomment;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.recomment_close) {
			mDismiss(0);
		} else if (v.getId() == R.id.recomment_dv_1) {
			mDismiss(1);
		} else if (v.getId() == R.id.recomment_dv_2) {
			mDismiss(1);
		} else if (v.getId() == R.id.recomment_dv_3) {
			mDismiss(1);
		} else if (v.getId() == R.id.recomment_dv_4) {
			mDismiss(1);
		}
	}

	private class RecommentItemOnClick implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			mDismiss(2);
		}
	}
}