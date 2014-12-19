package vnp.com.mimusic.base.diablog;

import java.util.ArrayList;
import java.util.List;

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

public class ChonListContactDialog extends BaseAdialog implements android.view.View.OnClickListener {
	public ChonListContactDialog(Context context, int theme) {
		super(context, theme);
	}

	public ChonListContactDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.chonlistcontact_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		HeaderView chitiettintuc_headerview = (HeaderView) findViewById(R.id.chonlistcontact_headerview);
		chitiettintuc_headerview.setTextHeader(R.string.moidanhba);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.btn_back);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDismiss();
			}
		});
		ListView menu_left_list = (ListView) findViewById(R.id.chonlistcontact_list);

		List<ContentValues> objects = new ArrayList<ContentValues>();
		for (int i = 0; i < 10; i++) {
			objects.add(new ContentValues());
		}

		menu_left_list.setAdapter(new MoiDanhBaAdapter(getContext(), new String[] { "a", "a", "a", "a", "a", "a", "a" }) {

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
				ChonListContactDialog.this.dismiss();
			}
		});
		findViewById(R.id.chonlistcontact_main).startAnimation(animation);

	}

	@Override
	public int getLayout() {
		return R.layout.chonlistcontact;
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