package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class TabView extends LinearLayout {
	public TabView(Context context) {
		super(context);
		init();
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab, this);
	}

	public void setOnClickListener(OnClickListener tabOnClick, OnClickListener homeOnClick) {
		findViewById(R.id.tab_1).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_2).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_3).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_4).setOnClickListener(tabOnClick);

		findViewById(R.id.tab_headerview).findViewById(R.id.header_btn_left).setOnClickListener(homeOnClick);
		findViewById(R.id.tab_headerview).findViewById(R.id.header_btn_right).setOnClickListener(homeOnClick);
	}
}