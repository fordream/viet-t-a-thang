package vnp.com.mimusic.activity;

import org.json.JSONObject;

import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.VApplication.IServiceConfig;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.DialogCallBack;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.LoadingView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.vnp.core.crash.CrashExceptionHandler;

//vnp.com.mimusic.activity.SplashScreenActivity
public class SplashScreenActivity extends Activity {
	private vnp.com.mimusic.view.LoadingView loadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		 * log for crash
		 */
		CrashExceptionHandler.sendCrash(this);
		CrashExceptionHandler.onCreate(this);

		setContentView(R.layout.splash);

		loadingView = (LoadingView) findViewById(R.id.loadingView1);

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!isFinishing()) {
					((VApplication) getApplication()).init(config);
				}
			}
		}, 1000);

		// startActivity(new Intent(SplashScreenActivity.this,
		// NewMusicSlideMenuActivity.class));
	}

	private IServiceConfig config = new IServiceConfig() {
		@Override
		public void onServiceDisconnected() {

		}

		@Override
		public void onServiceConnected() {
			// TODO
			// startActivity(new Intent(SplashScreenActivity.this,
			// NewMusicSlideMenuActivity.class));

			if (Conts.LOGINWIFI) {
				startActivity(new Intent(SplashScreenActivity.this, LoginActivty.class));
				return;
			}
			if (Conts.is3GConnected(SplashScreenActivity.this)) {
				((VApplication) getApplication()).login(true, "", "", new IContsCallBack() {

					@Override
					public void onStart() {
						Conts.showView(loadingView, true);
					}

					@Override
					public void onSuscess(JSONObject response) {
						//
						Conts.showView(loadingView, false);
						if (!isFinishing()) {
							startActivity(new Intent(SplashScreenActivity.this, NewMusicSlideMenuActivity.class));
							finish();
							overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_nothing);
						}
					}

					@Override
					public void onError(String message) {
						Conts.showView(loadingView, false);
						if (!isFinishing()) {
							// startActivity(new
							// Intent(SplashScreenActivity.this,
							// LoginActivty.class));
							// finish();
							// overridePendingTransition(R.anim.abc_slide_right_in,
							// R.anim.abc_nothing);

							Conts.showDialogDongYCallBack(SplashScreenActivity.this, getString(R.string.khongthedangnhap), new DialogCallBack() {

								@Override
								public void callback(Object object) {
									finish();
								}
							});
						}
					}
				});
			} else {
				if (!isFinishing()) {
					Conts.showDialogDongYCallBack(SplashScreenActivity.this, getString(R.string.need3g), new DialogCallBack() {

						@Override
						public void callback(Object object) {
							finish();
						}
					});
					// startActivity(new Intent(SplashScreenActivity.this,
					// LoginActivty.class));
					// finish();
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_nothing);

				}
			}
		}
	};
}