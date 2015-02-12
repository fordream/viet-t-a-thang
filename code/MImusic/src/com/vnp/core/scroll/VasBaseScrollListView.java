package com.vnp.core.scroll;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

public abstract class VasBaseScrollListView implements OnTouchListener {
	private ListView listViews[];
	private int ytop = -1000;
	private View header;
	private Activity activity;

	public VasBaseScrollListView(ListView[] listView, View header, Activity activity) {
		this.listViews = listView;
		this.activity = activity;
		this.header = header;
		for (ListView listView2 : listViews)
			listView2.setOnTouchListener(this);
	}

	@Override
	public final boolean onTouch(View v, MotionEvent event) {
		if (activity != null) {
			Conts.hiddenKeyBoard(activity);
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			ytop = (int) event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
			release();
			ytop = -1000;
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			int yCurrentTop = (int) event.getY();
			if (ytop == -1000) {
				ytop = yCurrentTop;
				return false;
			}

			if (Math.abs(ytop - yCurrentTop) > 10) {
				update(ytop - yCurrentTop, yCurrentTop);
				ytop = yCurrentTop;
			}
		}
		return false;
	}

	private void release() {

		int marginTop = 0;
		int height = header.getHeight();

		ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
		if (layoutParams instanceof LinearLayout.LayoutParams) {
			marginTop = ((LinearLayout.LayoutParams) layoutParams).topMargin;
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			marginTop = ((RelativeLayout.LayoutParams) layoutParams).topMargin;
		}

		if (marginTop < -height / 2) {
			marginTop = -height;
		} else {
			marginTop = 0;
		}

		update(marginTop);

		update(header, marginTop);

	}

	private int height;

	private void update(int dy, int yCurrentTop) {
		dy = dy / 2;

		int marginTop = 0;
		if (height == 0) {
			height = header.getHeight();
		}

		ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
		if (layoutParams instanceof LinearLayout.LayoutParams) {
			marginTop = ((LinearLayout.LayoutParams) layoutParams).topMargin;
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			marginTop = ((RelativeLayout.LayoutParams) layoutParams).topMargin;
		}

		marginTop = marginTop - dy;

		if (marginTop < -height) {
			marginTop = -height;
		} else if (marginTop > 0) {
			marginTop = 0;
		}

		update(marginTop);

		update(header, marginTop);
	}

	public void update(final View v, final int margintop) {
		if (v != null) {

			ViewGroup.LayoutParams layoutParamsHeader = v.getLayoutParams();
			if (layoutParamsHeader instanceof LinearLayout.LayoutParams) {
				((LinearLayout.LayoutParams) layoutParamsHeader).topMargin = margintop;
			} else if (layoutParamsHeader instanceof RelativeLayout.LayoutParams) {
				((RelativeLayout.LayoutParams) layoutParamsHeader).topMargin = margintop;
			}
			v.setLayoutParams(layoutParamsHeader);

		}
	}

	private void update(int marginTop) {

		for (ListView listView : listViews) {
			if (listView.getHeaderViewsCount() > 0) {
				View view = listView.getChildAt(0);
				if (view instanceof HeaderView) {
					update(view.findViewById(R.id.header_main_content), marginTop);
				}
			}
		}
	}

	public abstract void hidden();

	public abstract void show();
}