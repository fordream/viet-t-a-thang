package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.base.BaseAdialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

public class XacNhanDialog extends BaseAdialog implements android.view.View.OnClickListener {

	public XacNhanDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.dangky_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_scale_in));
		((TextView) findViewById(R.id.dangky_dialog_content)).setText(getMessage());
		findViewById(R.id.imageView1).setOnClickListener(this);
	}

	private String message = "";

	public void setMessage(String str) {
		message = str;
	}

	private String getMessage() {
		return message;
	}

	public void mDismiss() {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_scale_out);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				XacNhanDialog.this.dismiss();
			}
		});
		findViewById(R.id.dangky_dialog_main).startAnimation(animation);

	}

	@Override
	public int getLayout() {
		return R.layout.xacnhan_dialog;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.imageView1) {
			mDismiss();
		}
	}
}