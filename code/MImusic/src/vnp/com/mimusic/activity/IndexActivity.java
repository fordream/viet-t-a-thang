package vnp.com.mimusic.activity;

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
		if (Conts.isTablet(this)) {
			startActivity(new Intent(this, LoginTabletActivty.class));
		} else {
			startActivity(new Intent(this, LoginActivty.class));
		}

//		startActivity(new Intent(this, LoginActivty.class));
		finish();
	}
}