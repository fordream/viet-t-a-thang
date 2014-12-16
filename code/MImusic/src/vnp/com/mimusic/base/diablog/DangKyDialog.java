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
import android.widget.TextView;

public class DangKyDialog extends BaseAdialog implements android.view.View.OnClickListener {
	private ContentValues contentValues;

	public DangKyDialog(Context context, int theme) {
		super(context, theme);
	}

	public DangKyDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.dangky_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		((TextView) findViewById(R.id.dangky_dialog_name)).setText(contentValues.getAsString("name"));
		((TextView) findViewById(R.id.dangky_dialog_content)).setText(contentValues.getAsString("content"));

		findViewById(R.id.dangky_dialog_cancel).setOnClickListener(this);
		findViewById(R.id.dangky_dialog_register).setOnClickListener(this);
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
				DangKyDialog.this.dismiss();
			}
		});
		findViewById(R.id.dangky_dialog_main).startAnimation(animation);
		
		
		
	}

	public DangKyDialog(Context context, ContentValues contentValues) {
		super(context);
		this.contentValues = contentValues;
	}

	@Override
	public int getLayout() {
		return R.layout.dangky_dialog;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.dangky_dialog_cancel) {
			mDismiss();
		} else if (v.getId() == R.id.dangky_dialog_register) {
			// call to server
			mDismiss();
		}
	}
}