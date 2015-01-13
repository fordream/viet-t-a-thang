package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.view.ReCommentView;
import vnp.com.mimusic.view.RecommentItemView;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class ReCommnetDialog extends BaseAdialog implements android.view.View.OnClickListener {
	ReCommentView reCommentView;

	public ReCommnetDialog(Context context, int theme) {
		super(context, theme);
	}

	public ReCommnetDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		reCommentView = new ReCommentView(getContext()) {

			@Override
			public void addDv() {

			}

			@Override
			public void addContact() {

			}

			@Override
			public void mCLose() {
				super.mCLose();
				dismiss();
			}
		};

		setContentView(reCommentView);
		reCommentView.start();
	}

	@Override
	public int getLayout() {
		return 0;
	}

	@Override
	public void onClick(View v) {

	}

}