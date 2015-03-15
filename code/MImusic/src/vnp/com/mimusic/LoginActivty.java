package vnp.com.mimusic;

import org.json.JSONObject;

import vnp.com.db.datastore.AccountStore;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.VTAnimationUtils;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.add.OnTouchAnimation;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.vnp.core.crash.CrashExceptionHandler;

public class LoginActivty extends Activity implements OnClickListener, OnTouchListener {
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

		findViewById(R.id.activity_login_main).setOnTouchListener(this);
		init();
	}

	private void init() {
		((TextView) findViewById(R.id.activity_login_number_phone)).setText(new AccountStore(this).getUser());
		((TextView) findViewById(R.id.activity_login_password)).setText(new AccountStore(this).getPassword());
		((TextView) findViewById(R.id.activity_login_number_phone)).setText("84978581052");
		((TextView) findViewById(R.id.activity_login_password)).setText("917124");

		if (!Conts.isBlank(new AccountStore(this).getUser())) {
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

			});
		}
	}

	private void gotoHome() {
		if (!isFinishing()) {
			startActivity(new Intent(this, NewMusicSlideMenuActivity.class));
			finish();
			overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_nothing);
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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Conts.hiddenKeyBoard(this);
		return false;
	}
}