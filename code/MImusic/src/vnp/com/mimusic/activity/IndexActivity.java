package vnp.com.mimusic.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.vnp.core.common.https.RunSSL1;
import com.vnp.core.common.https.RunSSL2;

import vnp.com.api.API;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.LoginTabletActivty;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
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
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("u", "1674537885"));
		params.add(new BasicNameValuePair("p", "241841"));
		RunSSL2.xTest("https://125.235.40.85/api.php/" + API.API_R002, params);
		startActivity(new Intent(this, LoginActivty.class));
		finish();

	}
}
