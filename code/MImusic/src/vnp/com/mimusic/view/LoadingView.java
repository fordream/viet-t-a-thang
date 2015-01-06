package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class LoadingView extends LinearLayout {
	public LoadingView(Context context) {
		super(context);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		try {
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.loadingview, this);
			findViewById(R.id.loading_main).setOnClickListener(null);
		} catch (Exception exception) {

		}
	}
}