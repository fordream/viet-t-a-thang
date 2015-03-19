package vnp.com.mimusic.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.viettel.vtt.vdealer.R;

public class BynalProgressDialog extends ProgressDialog {

	public BynalProgressDialog(Context context) {
		super(context);
		setCancelable(false);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loading);
	}
}