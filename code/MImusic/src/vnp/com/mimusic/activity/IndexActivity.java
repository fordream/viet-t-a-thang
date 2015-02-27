package vnp.com.mimusic.activity;

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

		if (Conts.isTablet(this)) {
			startActivity(new Intent(this, LoginTabletActivty.class));
		} else {
			startActivity(new Intent(this, SplashScreenActivity.class));
		}

		finish();
		overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_nothing_0);
	}
}