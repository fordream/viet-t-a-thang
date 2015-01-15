package vnp.com.mimusic.base.diablog;

import org.json.JSONObject;

import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.view.ReCommentView;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

public abstract class ReCommnetDialog extends BaseAdialog implements android.view.View.OnClickListener {
	ReCommentView reCommentView;
	JSONObject responseRecommend;

	public ReCommnetDialog(Context context, JSONObject responseRecommend) {
		super(context);
		this.responseRecommend = responseRecommend;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		reCommentView = new ReCommentView(getContext(), this.responseRecommend) {

			@Override
			public void addDv(String csCode) {
				_addDv(csCode);
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

	public abstract void _addDv(String csCode);

	@Override
	public int getLayout() {
		return 0;
	}

	@Override
	public void onClick(View v) {

	}

}