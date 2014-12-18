package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MauMoiAdaper;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ListView;

public class MauMoiDialog extends BaseAdialog implements android.view.View.OnClickListener {
	private ContentValues contentValues;

	public MauMoiDialog(Context context, int theme) {
		super(context, theme);
	}

	public MauMoiDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.maumoi_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));

		HeaderView maumoi_headerview = (HeaderView) findViewById(R.id.maumoi_headerview);
		maumoi_headerview.setTextHeader(R.string.mautinmoitemplete);
		maumoi_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		maumoi_headerview.setButtonRightImage(false, R.drawable.btn_back);
		maumoi_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss();
			}
		});
		ListView maumoi_list = (ListView) findViewById(R.id.maumoi_list);
		maumoi_list.setAdapter(new MauMoiAdaper(getContext(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));
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
				MauMoiDialog.this.dismiss();
			}
		});
		findViewById(R.id.maumoi_main).startAnimation(animation);

	}

	public MauMoiDialog(Context context, ContentValues contentValues) {
		super(context);
		this.contentValues = contentValues;
	}

	@Override
	public int getLayout() {
		return R.layout.maumoi_dialog;
	}

	@Override
	public void onClick(View v) {
	}
}