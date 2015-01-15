package vnp.com.mimusic.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.vnp.core.common.https.HttpsRestClient;
import com.vnp.core.common.https.RunSSL1;
import com.vnp.core.common.https.RunSSL2;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.LoginTabletActivty;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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

		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpsRestClient httpsRestClient = new HttpsRestClient("https://125.235.40.85/api.php/utilitiServices?token=5087683F-1687-F8D4-0C8C-974833E65116");
				//httpsRestClient.addParam("u", "0968050820");
				//httpsRestClient.addParam("p", "265376");
				//httpsRestClient.addParam("token", "5087683F-1687-F8D4-0C8C-974833E65116");
				
				httpsRestClient.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
				httpsRestClient.execute(RequestMethod.GET);
				LogUtils.e("resx", httpsRestClient.getResponse());
			}
		}).start();
		startActivity(new Intent(this, LoginActivty.class));
		finish();

	}
}
