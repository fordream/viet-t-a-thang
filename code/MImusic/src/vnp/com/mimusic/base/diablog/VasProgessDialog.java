package vnp.com.mimusic.base.diablog;

import vnp.com.mimusic.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

public class VasProgessDialog extends ProgressDialog {

	public VasProgessDialog(Context context) {
		super(context, android.R.style.Theme_Translucent_NoTitleBar);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loadingview);
	}
}
