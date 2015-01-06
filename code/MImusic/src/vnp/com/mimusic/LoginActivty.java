package vnp.com.mimusic;

import org.json.JSONObject;

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
		((VApplication) getApplication()).dongbodanhba();
		setContentView(R.layout.activity_login);
		progressBar1 = (LoadingView) findViewById(R.id.loadingView1);
		Conts.showView(progressBar1, false);
		overridePendingTransition(R.anim.abc_nothing_0, R.anim.abc_nothing_0);

		findViewById(R.id.activity_login_btn).setOnClickListener(this);
		findViewById(R.id.activity_login_btn).setOnTouchListener(new OnTouchAnimation());

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
		String selection = User.STATUS + "='1'";
		Cursor cursor = getContentResolver().query(User.CONTENT_URI, null, selection, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			((TextView) findViewById(R.id.activity_login_number_phone)).setText(cursor.getString(cursor.getColumnIndex(User.USER)));
			((TextView) findViewById(R.id.activity_login_password)).setText(cursor.getString(cursor.getColumnIndex(User.PASSWORD)));
			cursor.close();
			// gotoHome();
		} else {
			// findViewById(R.id.activity_login_main).setVisibility(View.VISIBLE);
		}
		findViewById(R.id.activity_login_main).setVisibility(View.VISIBLE);
	}

	@Override
	public void onClick(View v) {
		String numberPhone = ((TextView) findViewById(R.id.activity_login_number_phone)).getText().toString();
		final String password = ((TextView) findViewById(R.id.activity_login_password)).getText().toString();

		if (!numberPhone.trim().equals("") && !password.trim().equals("")) {

			((VApplication) getApplication()).getmImusicService().login(false, numberPhone, password, new IContsCallBack() {

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

					Toast.makeText(LoginActivty.this, "please check network", Toast.LENGTH_SHORT).show();
					Conts.showView(progressBar1, false);
					Conts.enableView(new View[] { //
					findViewById(R.id.activity_login_number_phone), //
							findViewById(R.id.activity_login_password), //
							findViewById(R.id.activity_login_btn) });//
				}
			});

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