package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MoiDanhBaAdapter;
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

public class MoiNhieuDichVuDialog extends BaseAdialog implements android.view.View.OnClickListener {
	private ContentValues contentValues;

	public MoiNhieuDichVuDialog(Context context, int theme) {
		super(context, theme);
	}

	public MoiNhieuDichVuDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.moinhieudichvu_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));

		HeaderView maumoi_headerview = (HeaderView) findViewById(R.id.moinhieudichvu_dialog_headerview);
		maumoi_headerview.setTextHeader(R.string.moidanhba);
		maumoi_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		maumoi_headerview.setButtonRightImage(false, R.drawable.btn_back);
		maumoi_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss();
			}
		});
		ListView maumoi_list = (ListView) findViewById(R.id.moinhieudichvu_dialog_list);
		maumoi_list.setAdapter(new MoiDanhBaAdapter(getContext(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));

		findViewById(R.id.moinhieudichvu_dialog_chonloimoi).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new MauMoiDialog(getContext()).show();
			}
		});
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
				MoiNhieuDichVuDialog.this.dismiss();
			}
		});
		findViewById(R.id.moinhieudichvu_main).startAnimation(animation);

	}

	public MoiNhieuDichVuDialog(Context context, ContentValues contentValues) {
		super(context);
		this.contentValues = contentValues;
	}

	@Override
	public int getLayout() {
		return R.layout.moinhieudichvu_dialog;
	}

	@Override
	public void onClick(View v) {
	}
}