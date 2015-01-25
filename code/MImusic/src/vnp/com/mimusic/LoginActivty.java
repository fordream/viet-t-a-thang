package vnp.com.mimusic;

import org.json.JSONObject;

import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.VTAnimationUtils;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.add.OnTouchAnimation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.vnp.core.crash.CrashExceptionHandler;

public class LoginActivty extends Activity implements OnClickListener {
	private LoadingView progressBar1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CrashExceptionHandler.sendCrash(this);
		CrashExceptionHandler.onCreate(this);

		setContentView(R.layout.activity_login);
		progressBar1 = (LoadingView) findViewById(R.id.loadingView1);
		Conts.showView(progressBar1, false);

		findViewById(R.id.activity_login_btn).setOnClickListener(this);
		findViewById(R.id.activity_login_btn).setOnTouchListener(new OnTouchAnimation());

		findViewById(R.id.activity_login_soantin).setOnClickListener(this);
		findViewById(R.id.activity_login_soantin).setOnTouchListener(new OnTouchAnimation());

		init();
	}

	private void init() {
		((TextView) findViewById(R.id.activity_login_number_phone)).setText(Conts.getUser(this));
		((TextView) findViewById(R.id.activity_login_password)).setText(Conts.getPassword(this));
		((TextView) findViewById(R.id.activity_login_number_phone)).setText("0964506972");
		((TextView) findViewById(R.id.activity_login_password)).setText("265376");

		if (!Conts.isBlank(Conts.getUser(this))) {
			((VApplication) getApplication()).refreshToken(new IContsCallBack() {
				@Override
				public void onStart() {
					Conts.showView(progressBar1, true);
					Conts.disableView(new View[] { //

					findViewById(R.id.activity_login_number_phone), //
							findViewById(R.id.activity_login_password), //
							findViewById(R.id.activity_login_btn) });//
					Conts.hiddenKeyBoard(LoginActivty.this);
				}

				@Override
				public void onSuscess(JSONObject response) {
					gotoHome();
				}

				@Override
				public void onError(String message) {
					Conts.showDialogThongbao(LoginActivty.this, message);
					Conts.showView(progressBar1, false);
					Conts.enableView(new View[] { //
					findViewById(R.id.activity_login_number_phone), //
							findViewById(R.id.activity_login_password), //
							findViewById(R.id.activity_login_btn) });//
				}

				@Override
				public void onError() {
					onError("onError");
				}
			});
		}
	}

	private void gotoHome() {
		if (!isFinishing()) {
			startActivity(new Intent(this, BaseMusicSlideMenuActivity.class));
			finish();
			overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_nothing);
		}
	}

	private void login(boolean is3G, final String u, final String p) {
		((VApplication) getApplication()).login(is3G, u, p, new IContsCallBack() {

			@Override
			public void onStart() {
				Conts.showView(progressBar1, true);
				Conts.disableView(new View[] { //

				findViewById(R.id.activity_login_number_phone), //
						findViewById(R.id.activity_login_password), //
						findViewById(R.id.activity_login_btn) });//
				Conts.hiddenKeyBoard(LoginActivty.this);
			}

			@Override
			public void onSuscess(JSONObject response) {
				gotoHome();
			}

			@Override
			public void onError(String message) {
				Conts.showDialogThongbao(LoginActivty.this, message);
				Conts.showView(progressBar1, false);
				Conts.enableView(new View[] { //
				findViewById(R.id.activity_login_number_phone), //
						findViewById(R.id.activity_login_password), //
						findViewById(R.id.activity_login_btn) });//
			}

			@Override
			public void onError() {
				onError("onError");
			}
		});
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.activity_login_soantin) {

			try {
				Intent sendIntent = new Intent(Intent.ACTION_VIEW);
				sendIntent.putExtra("sms_body", "MK");
				sendIntent.setType("vnd.android-dir/mms-sms");
				sendIntent.putExtra("address", "567");
				startActivity(sendIntent);
			} catch (Exception exception) {
				Conts.showDialogThongbao(this, getString(R.string.noappsendmessage));
			}
			return;
		}

		final String numberPhone = ((TextView) findViewById(R.id.activity_login_number_phone)).getText().toString();
		final String password = ((TextView) findViewById(R.id.activity_login_password)).getText().toString();

		if (!Conts.isBlank(numberPhone) && !Conts.isBlank(password)) {
			login(false, numberPhone, password);
		} else {
			if (Conts.isBlank(numberPhone)) {
				VTAnimationUtils.animationErrorEditText(findViewById(R.id.activity_login_number_phone));
			}

			if (Conts.isBlank(password)) {
				VTAnimationUtils.animationErrorEditText(findViewById(R.id.activity_login_password));
			}

			Conts.showDialogThongbao(LoginActivty.this, getString(R.string.bancannhapsdtvamatkkau));
		}
	}
}