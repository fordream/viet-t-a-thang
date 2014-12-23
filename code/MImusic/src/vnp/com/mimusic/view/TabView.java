package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

public class TabView extends LinearLayout {
	public TabView(Context context) {
		super(context);
		init();
	}

	public TabView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.tab, this);
	}

	public void setOnClickListener(OnClickListener tabOnClick, OnClickListener homeOnClick) {
		findViewById(R.id.tab_1).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_2).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_3).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_4).setOnClickListener(tabOnClick);
		findViewById(R.id.tab_headerview).findViewById(R.id.header_btn_left).setOnClickListener(homeOnClick);
		findViewById(R.id.tab_headerview).findViewById(R.id.header_btn_right).setOnClickListener(homeOnClick);
	}

	public void setTextHeader(int res) {
		HeaderView headerView = (HeaderView) findViewById(R.id.tab_headerview);
		headerView.setTextHeader(res);
	}

	private int index = 0;

	public void updateTab(int res) {
		int next = 0;
		if (res == R.string.kenhbanvas) {
			next = 0;
		} else if (res == R.string.dichvu) {
			next = 1;
		} else if (res == R.string.bangxephang) {
			next = 2;
		} else if (res == R.string.tintuc) {
			next = 3;
		}

		if (next != index) {
			findViewById(R.id.tab_selected_main).post(new NextRunable(next));
		}
		// findViewById(R.id.tab_1).setBackgroundResource(res ==
		// R.string.kenhbanvas ? R.drawable.tab_indivicator_selected :
		// R.drawable.tab_indivicator);
		// findViewById(R.id.tab_2).setBackgroundResource(res == R.string.dichvu
		// ? R.drawable.tab_indivicator_selected : R.drawable.tab_indivicator);
		// findViewById(R.id.tab_3).setBackgroundResource(res == R.string.tintuc
		// ? R.drawable.tab_indivicator_selected : R.drawable.tab_indivicator);
		// findViewById(R.id.tab_4).setBackgroundResource(res ==
		// R.string.bangxephang ? R.drawable.tab_indivicator_selected :
		// R.drawable.tab_indivicator);
	}

	private class NextRunable implements Runnable {
		int next;

		public NextRunable(int next) {
			this.next = next;
		}

		@Override
		public void run() {
			int width = findViewById(R.id.tab_selected_main).getWidth();
			TranslateAnimation animation = new TranslateAnimation(width / 4 * index, width / 4 * next, 0, 0);
			animation.setDuration(300);
			animation.setFillAfter(true);
			findViewById(R.id.tab_selected).startAnimation(animation);
			index = next;
		}
	}
}