package com.vnp.core.scroll;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

public abstract class VasBaseScrollListView implements OnTouchListener {
	private ListView listView;
	private int ytop = 0;
	private Handler handler = new Handler();

	public VasBaseScrollListView(ListView listView) {
		this.listView = listView;
		this.listView.setOnTouchListener(this);
	}

	@Override
	public final boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			ytop = (int) event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// Log.e("VasScrollListView", event.getY() + "");

			int yCurrentTop = (int) event.getY();
			if (yCurrentTop + 1 < ytop) {
				hidden();
			} else if (yCurrentTop - 1 > ytop) {
				show();
			}

			ytop = yCurrentTop;
		}
		return false;

	}

	public abstract void hidden();

	public abstract void show();
}