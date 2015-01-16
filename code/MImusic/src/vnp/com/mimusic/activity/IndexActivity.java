package vnp.com.mimusic.activity;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.LogUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.vnp.core.common.https.HttpsRestClient;
import com.vnp.core.common.https.HttpsRestClient.IHttpsRestClientLisner;

public class IndexActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_nothing_0, R.anim.abc_nothing_0);
		// if (Conts.isTablet(this)) {
		// startActivity(new Intent(this, LoginTabletActivty.class));
		// } else {
		// startActivity(new Intent(this, LoginActivty.class));
		// }

		HttpsRestClient httpsRestClient = new HttpsRestClient(IndexActivity.this, "https://125.235.40.85/api.php/" + API.API_R026);
		httpsRestClient.addParam("u", "0968050820");
		httpsRestClient.addParam("p", "265376");
		httpsRestClient.addParam("token", "A1E56268-D289-7A8A-A3FF-710307DDEE80");
		//httpsRestClient.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		httpsRestClient.execute(RequestMethod.GET, new IHttpsRestClientLisner() {
			@Override
			public void onSucces(int responseCode, String message, String response, Exception exception) {
				LogUtils.e("resx", String.format("%s %s %s", responseCode, message + "", response + ""));
				if (exception != null) {
					LogUtils.e("resx", exception);
				}
			}
		});

		startActivity(new Intent(this, LoginActivty.class));
		finish();

	}
}
