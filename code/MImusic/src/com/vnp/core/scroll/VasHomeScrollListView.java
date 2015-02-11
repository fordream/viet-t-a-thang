package com.vnp.core.scroll;

import android.app.Activity;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class VasHomeScrollListView extends VasBaseScrollListView implements OnTouchListener {

	public VasHomeScrollListView(View header, View top, ListView[] listView, Activity activity) {
		super(listView, header, activity);
	}

	@Override
	public void hidden() {
		// Conts.showView(header, false);
	}

	@Override
	public void show() {
		// Conts.showView(header, true);
	}

}