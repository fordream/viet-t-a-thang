package com.vnp.core.scroll;

import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.view.View;
import android.widget.ListView;

public class VasChonDichvuScrollListView extends  VasDichvuScrollListView {

	public VasChonDichvuScrollListView(View header, HeaderView listHeader, ListView[] listView, Activity activity) {
		super(header, listHeader, listView,activity);
	}


}