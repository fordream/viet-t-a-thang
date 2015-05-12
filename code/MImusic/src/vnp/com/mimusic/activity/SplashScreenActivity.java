package vnp.com.mimusic.activity;

import org.json.JSONObject;

import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DataStore;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.VApplication.IServiceConfig;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.util.Conts.AppInforGetCallBack;
import vnp.com.mimusic.util.Conts.DialogCallBack;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.LoadingView;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import com.gc.android.market.api.MarketSession;
import com.gc.android.market.api.MarketSession.Callback;
import com.gc.android.market.api.model.Market.AppsRequest;
import com.gc.android.market.api.model.Market.AppsResponse;
import com.gc.android.market.api.model.Market.ResponseContext;
import com.viettel.vtt.vdealer.R;
import com.vnp.core.crash.CrashExceptionHandler;

//vnp.com.mimusic.activity.SplashScreenActivity
public class SplashScreenActivity extends Activity {
	private vnp.com.mimusic.view.LoadingView loadingView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		DataStore.getInstance().init(this);
		DataStore.getInstance().save("RootMenuActivity", false);
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

	}

	private IServiceConfig config = new IServiceConfig() {
		@Override
		public void onServiceDisconnected() {

		}

		@Override
		public void onServiceConnected() {

			if (Conts.LOGINWIFI) {
				startActivity(new Intent(SplashScreenActivity.this, LoginActivty.class));
				return;
			}

			if (Conts.is3GConnectedOrWifiConnected(SplashScreenActivity.this)) {
				checkVersionOffApp();
			} else {
				if (!isFinishing()) {
					showDialogNeed3g();
				}
			}
		}

	};

	private void checkVersionOffApp() {
		new AsyncTask<String, String, String>() {
			@Override
			protected String doInBackground(String... params) {
				RestClient restClient = new RestClient("https://play.google.com/store/apps/details?id=" + SplashScreenActivity.this.getPackageName());
				restClient.execute(RequestMethod.GET);
				String response = restClient.getResponse();
				try {

					String softwareVersion = response.substring(response.indexOf("softwareVersion") + "softwareVersion".length());

					softwareVersion = softwareVersion.substring(softwareVersion.indexOf("\">") + 2);
					softwareVersion = softwareVersion.substring(0, softwareVersion.indexOf("<"));
					return softwareVersion.trim();
				} catch (Exception exception) {

				}
				return null;
			}

			@Override
			protected void onPostExecute(String softwareVersion) {
				super.onPostExecute(softwareVersion);
				LogUtils.es("dkm",softwareVersion + "");
				if (!isFinishing()) {
					if (Conts.isBlank(softwareVersion)) {
						showDialogNeed3g();
					} else {
						String nowVersionName = Conts.getVersionName(SplashScreenActivity.this);

						if (Conts.isBlank(nowVersionName)) {
							login3g();
						} else {
							if (Conts.isBlank(softwareVersion)) {
								login3g();
							} else if (Conts.isBlank(softwareVersion)) {
								showDialogNeed3g();
							} else if (Conts.convertToFloat(nowVersionName) >= Conts.convertToFloat(softwareVersion)) {
								login3g();
							} else {
								String message = getString(R.string.needupdate);
								message = String.format(message, getString(R.string.app_name), softwareVersion);
								Conts.showDialogDongYCallBack(SplashScreenActivity.this, message, new DialogCallBack() {
									@Override
									public void callback(Object object) {
										Conts.callMarket(SplashScreenActivity.this);
										finish();
									}
								});
							}
						}
					}
				}
			}
		}.execute("");
	}

	private void showDialogNeed3g() {
		Conts.showDialogDongYCallBack(SplashScreenActivity.this, getString(R.string.need3g), new DialogCallBack() {

			@Override
			public void callback(Object object) {
				finish();
			}
		});
	}

	private void login3g() {
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

					Conts.showDialogDongYCallBack(SplashScreenActivity.this, getString(R.string.khongthedangnhap), new DialogCallBack() {

						@Override
						public void callback(Object object) {
							finish();
						}
					});
				}
			}
		});
	}
}