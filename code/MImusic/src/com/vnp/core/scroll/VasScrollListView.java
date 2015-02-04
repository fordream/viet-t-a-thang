package com.vnp.core.scroll;

import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class VasScrollListView implements OnScrollListener {
	private View header;
	private View top;

	private int ytop = 0;

	public VasScrollListView(View header, View top) {
		this.header = header;
		this.top = top;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

		// LogUtils.e("VasScrollListView", scrollState + " " +
		// OnScrollListener.SCROLL_STATE_IDLE + " " +
		// OnScrollListener.SCROLL_STATE_FLING + " " +
		// OnScrollListener.SCROLL_STATE_TOUCH_SCROLL);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

		int yCurrentTop = top.getTop();

		if (yCurrentTop + 1 < ytop) {
			Conts.showView(header, false);
		} else if (yCurrentTop - 1 > ytop) {
			Conts.showView(header, true);
		}
		LogUtils.e("VasScrollListView", yCurrentTop + " ");

		ytop = yCurrentTop;
	}
}