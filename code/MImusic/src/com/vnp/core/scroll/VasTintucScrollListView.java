package com.vnp.core.scroll;

import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class VasTintucScrollListView extends VasBaseScrollListView implements OnTouchListener {

	public VasTintucScrollListView(View header, HeaderView top, ListView[] listView, Activity activity) {
		super(listView, header, activity);
	}

	@Override
	public void hidden() {
	}

	@Override
	public void show() {
	}
}