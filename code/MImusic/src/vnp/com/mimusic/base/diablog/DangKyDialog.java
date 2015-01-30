package vnp.com.mimusic.base.diablog;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class DangKyDialog extends BaseAdialog implements android.view.View.OnClickListener {
	private ContentValues contentValues;
	private String type = "";

	public DangKyDialog(Context context) {
		super(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		findViewById(R.id.dangky_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_scale_in));
		((TextView) findViewById(R.id.dangky_dialog_name)).setText(contentValues.getAsString("name"));
		((TextView) findViewById(R.id.dangky_dialog_content)).setText(contentValues.getAsString("content"));

		String btn_right = contentValues.getAsString("btn_right");

		if (contentValues.containsKey("btn_left_close")) {
			findViewById(R.id.dangky_dialog_cancel).setVisibility(contentValues.getAsBoolean("btn_left_close") ? View.GONE : View.VISIBLE);
		}
		type = contentValues.getAsString("type");
		if (btn_right != null && !btn_right.trim().equals("")) {
			((TextView) findViewById(R.id.dangky_dialog_register)).setText(btn_right);
		}
		findViewById(R.id.dangky_dialog_cancel).setOnClickListener(this);
		findViewById(R.id.dangky_dialog_register).setOnClickListener(this);

		findViewById(R.id.thongbaox).setOnClickListener(this);
		if (contentValues.containsKey("typeThongBao")) {
			boolean typeThongBao = contentValues.getAsBoolean("typeThongBao");
			if (typeThongBao) {
				findViewById(R.id.thongbaox).setVisibility(View.VISIBLE);
				findViewById(R.id.thongbaofooter).setVisibility(View.GONE);
			}
		}
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
		if (v.getId() == R.id.thongbaox) {
			mDismiss();
		} else if (v.getId() == R.id.dangky_dialog_cancel) {
			mDismiss();
		} else if (v.getId() == R.id.dangky_dialog_register) {
			// call to server
			mOpen();
			// mDismiss();
		}
	}

	private ProgressDialog progressDialog;

	public void mOpen() {
		if ("dangky".equals(type)) {
			Bundle bundle = new Bundle();
			bundle.putString(DichVu.service_code, contentValues.getAsString(DichVu.service_code));

			((VApplication) getmContext().getApplicationContext()).execute(RequestMethod.POST, API.API_R017, bundle, new IContsCallBack() {

				@Override
				public void onSuscess(JSONObject response) {
					progressDialog.dismiss();
					closePopUp();
					updateUiDangKy();
				}

				@Override
				public void onStart() {
					progressDialog = ProgressDialog.show(getContext(), null, getContext().getString(R.string.loading));
				}

				@Override
				public void onError(String message) {
					Conts.toast(getContext(), message);
					progressDialog.dismiss();
					closePopUp();
				}

				@Override
				public void onError() {
					onError("onError");
				}
			});
		} else {
			mDismiss();
		}
	}

	private void closePopUp() {
		mDismiss();

	}

	public void updateUiDangKy() {

	}

}