package com.vnp.core.scroll;

import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class VasTintucScrollListView extends VasBaseScrollListView implements OnTouchListener {
	private View header;
	private HeaderView top;

	public VasTintucScrollListView(View header, HeaderView top, ListView listView) {
		super(listView);
		this.header = header;
		this.top = top;
	}

	@Override
	public void hidden() {
		Conts.showView(header, false);
		top.showHeader(false);
	}

	@Override
	public void show() {
		Conts.showView(header, true);
		top.showHeader(true);
	}
}