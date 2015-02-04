package com.vnp.core.scroll;

import vnp.com.mimusic.util.Conts;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class VasHomeScrollListView extends VasBaseScrollListView implements OnTouchListener {
	private View header;
	private View top;

	public VasHomeScrollListView(View header, View top, ListView listView) {
		super(listView);
		this.header = header;
		this.top = top;
	}

	@Override
	public void hidden() {
		Conts.showView(header, false);
	}

	@Override
	public void show() {
		Conts.showView(header, true);
	}

}