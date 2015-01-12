package vnp.com.mimusic;

import org.json.JSONObject;

import com.vnp.core.crash.CrashExceptionHandler;

import vnp.com.api.MImusicService;
import vnp.com.db.User;
import vnp.com.mimusic.VApplication.IServiceConfig;
import vnp.com.mimusic.base.VTAnimationListener;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.VTAnimationUtils;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.add.OnTouchAnimation;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivty extends Activity implements OnClickListener {
	private LoadingView progressBar1;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		CrashExceptionHandler.sendCrash(this);
		
		CrashExceptionHandler.onCreate(this);
		// ((VApplication) getApplication()).dongbodanhba();
		setContentView(R.layout.activity_login);
		progressBar1 = (LoadingView) findViewById(R.id.loadingView1);
		Conts.showView(progressBar1, false);
		overridePendingTransition(R.anim.abc_nothing_0, R.anim.abc_nothing_0);

		findViewById(R.id.activity_login_btn).setOnClickListener(this);
		findViewById(R.id.activity_login_btn).setOnTouchListener(new OnTouchAnimation());

		findViewById(R.id.activity_login_soantin).setOnClickListener(this);
		findViewById(R.id.activity_login_soantin).setOnTouchListener(new OnTouchAnimation());
		HeaderView header = (HeaderView) findViewById(R.id.activity_login_header);
		header.setTextHeader(R.string.dangnhap);
		header.showButton(false, false);

		final AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
		alphaAnimation.setDuration(500);
		alphaAnimation.setAnimationListener(new VTAnimationListener() {
			@Override
			public void onAnimationEnd(Animation animation) {
				super.onAnimationEnd(animation);
				((VApplication) getApplication()).init(config);
			}
		});

		findViewById(R.id.activity_login_splash).startAnimation(alphaAnimation);

	}

	private IServiceConfig config = new IServiceConfig() {
		@Override
		public void onServiceDisconnected() {

		}

		@Override
		public void onServiceConnected() {
			callInitSetting();
		}
	};

	private void gotoHome() {
		startActivity(new Intent(this, BaseMusicSlideMenuActivity.class));
		finish();
		overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_nothing);
	}

	private void callInitSetting() {
		((TextView) findViewById(R.id.activity_login_number_phone)).setText(Conts.getUser(this));
		((TextView) findViewById(R.id.activity_login_password)).setText(Conts.getPassword(this));

		if (Conts.isBlank(Conts.getUser(this))) {
			if (Conts.is3GConnected(LoginActivty.this)) {
				login(true, "", "");
			}
		} else {
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
					Toast.makeText(LoginActivty.this, message, Toast.LENGTH_SHORT).show();
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
		findViewById(R.id.activity_login_main).setVisibility(View.VISIBLE);
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
				Toast.makeText(LoginActivty.this, message, Toast.LENGTH_SHORT).show();
				Conts.showView(progressBar1, false);
				Conts.enableView(new View[] { //
				findViewById(R.id.activity_login_number_phone), //
						findViewById(R.id.activity_login_password), //
						findViewById(R.id.activity_login_btn) });//
			}

			@Override
			public void onError() {
				onError("onError");
				// Toast.makeText(LoginActivty.this, "please check network",
				// Toast.LENGTH_SHORT).show();
				// Conts.showView(progressBar1, false);
				// Conts.enableView(new View[] { //
				// findViewById(R.id.activity_login_number_phone), //
				// findViewById(R.id.activity_login_password), //
				// findViewById(R.id.activity_login_btn) });//
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
				Conts.toast(this, getString(R.string.noappsendmessage));
			}
			return;
		}
		String numberPhone = ((TextView) findViewById(R.id.activity_login_number_phone)).getText().toString();
		final String password = ((TextView) findViewById(R.id.activity_login_password)).getText().toString();

		if (!numberPhone.trim().equals("") && !password.trim().equals("")) {
			login(false, numberPhone, password);
		} else {
			if (Conts.isBlank(numberPhone)) {
				VTAnimationUtils.animationErrorEditText(findViewById(R.id.activity_login_number_phone));
			}

			if (Conts.isBlank(password)) {
				VTAnimationUtils.animationErrorEditText(findViewById(R.id.activity_login_password));
			}
			Toast.makeText(this, "input password and number phone", Toast.LENGTH_SHORT).show();
		}
	}
}