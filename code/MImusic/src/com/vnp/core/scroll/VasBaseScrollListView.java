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
	private int ytop = 0;
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
		for (ListView listView : listViews) {
			// if (listView.getAdapter() != null && listView.getVisibility() ==
			// View.VISIBLE) {
			// if (listView.getAdapter().getCount() < 5) {
			// return false;
			// }
			// }
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			ytop = (int) event.getY();
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
			release();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			int yCurrentTop = (int) event.getY();
			if (Math.abs(ytop - yCurrentTop) > 5) {
				update(ytop - yCurrentTop, yCurrentTop);
				ytop = yCurrentTop;
			}
		}
		return false;

	}

	private void release() {
		ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
		int marginTop = 0;
		int height = header.getHeight();

		if (layoutParams instanceof LinearLayout.LayoutParams) {
			marginTop = ((LinearLayout.LayoutParams) layoutParams).topMargin;
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			marginTop = ((RelativeLayout.LayoutParams) layoutParams).topMargin;
		}

		if (marginTop < -height / 2) {
			marginTop = -height;
			// hidden(true);
		} else {
			marginTop = 0;
			// hidden(false);
		}

		update(marginTop);
		if (layoutParams instanceof LinearLayout.LayoutParams) {
			((LinearLayout.LayoutParams) layoutParams).topMargin = marginTop;
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			((RelativeLayout.LayoutParams) layoutParams).topMargin = marginTop;
		}

		header.setLayoutParams(layoutParams);
	}

	private void update(int dy, int yCurrentTop) {
		ViewGroup.LayoutParams layoutParams = header.getLayoutParams();
		int marginTop = 0;
		int height = header.getHeight();
		if (layoutParams instanceof LinearLayout.LayoutParams) {
			marginTop = ((LinearLayout.LayoutParams) layoutParams).topMargin;
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			marginTop = ((RelativeLayout.LayoutParams) layoutParams).topMargin;
		}

		// marginTop = height - dy;
		marginTop = marginTop - dy;

		if (marginTop <= -height) {
			marginTop = -height;
			// ytop = yCurrentTop;
			// hidden(true);
		} else if (marginTop > 0) {
			marginTop = 0;
			// hidden(false);
			// ytop = yCurrentTop;
		}

		update(marginTop);
		if (layoutParams instanceof LinearLayout.LayoutParams) {
			((LinearLayout.LayoutParams) layoutParams).topMargin = marginTop;
		} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
			((RelativeLayout.LayoutParams) layoutParams).topMargin = marginTop;
		}

		header.setLayoutParams(layoutParams);
	}

	private void update(int marginTop) {
		for (ListView listView : listViews) {
			if (listView.getHeaderViewsCount() > 0) {
				View view = listView.getChildAt(0);
				if (view instanceof HeaderView) {
					HeaderView headerView = ((HeaderView) view);

					ViewGroup.LayoutParams layoutParams = headerView.findViewById(R.id.header_main_content).getLayoutParams();
					if (layoutParams instanceof LinearLayout.LayoutParams) {
						((LinearLayout.LayoutParams) layoutParams).topMargin = marginTop;
					} else if (layoutParams instanceof RelativeLayout.LayoutParams) {
						((RelativeLayout.LayoutParams) layoutParams).topMargin = marginTop;
					}

					headerView.findViewById(R.id.header_main_content).setLayoutParams(layoutParams);
				}
			}
		}
	}

	public abstract void hidden();

	public abstract void show();
}