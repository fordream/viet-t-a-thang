package vnp.com.mimusic.activity;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.util.Conts.IContsCallBack;
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

		Bundle bundle = new Bundle();
		bundle.putString("u", "0968050820");
		bundle.putString("p", "265376");
		((VApplication) getApplication()).executeHttps(RequestMethod.POST, API.API_R002, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				Conts.saveHttpsToken(IndexActivity.this, Conts.getString(response, "token"));
				callReComment();
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String message) {

			}

			@Override
			public void onError() {

			}
		});

		startActivity(new Intent(this, LoginActivty.class));
		finish();

	}

	protected void callReComment() {
		((VApplication) getApplication()).executeHttps(RequestMethod.GET, API.API_R026, new Bundle(), new IContsCallBack() {

			@Override
			public void onStart() {

			}

			@Override
			public void onError() {

			}

			@Override
			public void onError(String message) {
				// LogUtils.e("recomend", message);
			}

			@Override
			public void onSuscess(JSONObject response) {
				((VApplication) getApplication()).saveRecomend(response);
				// LogUtils.e("recomend", response.toString());
			}
		});
	}
}
