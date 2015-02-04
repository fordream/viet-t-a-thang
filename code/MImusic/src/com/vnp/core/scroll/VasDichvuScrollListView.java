package com.vnp.core.scroll;

import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public class VasDichvuScrollListView extends VasBaseScrollListView implements OnTouchListener {
	private View header;
	private HeaderView listHeader;

	public VasDichvuScrollListView(View header, HeaderView listHeader, ListView[] listView) {
		super(listView, header);
		this.header = header;
		this.listHeader = listHeader;
	}

	@Override
	public void hidden() {
		Conts.showView(header, false);
		listHeader.showHeader(false);
	}

	@Override
	public void show() {
		Conts.showView(header, true);
		listHeader.showHeader(true);
	}

}