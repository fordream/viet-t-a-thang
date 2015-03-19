package vnp.com.mimusic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.viettel.vtt.vdealer.R;

public class TabView extends LinearLayout {
	private ImageView tab_1_img, tab_2_img, tab_3_img, tab_4_img;

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
		tab_1_img = (ImageView) findViewById(R.id.tab_1_img);
		tab_2_img = (ImageView) findViewById(R.id.tab_2_img);
		tab_3_img = (ImageView) findViewById(R.id.tab_3_img);
		tab_4_img = (ImageView) findViewById(R.id.tab_4_img);

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

		tab_1_img.setImageResource(next == 0 ? R.drawable.a_1 : R.drawable.a_1_dis);
		tab_2_img.setImageResource(next == 1 ? R.drawable.a_2 : R.drawable.a_2_dis);
		tab_4_img.setImageResource(next == 2 ? R.drawable.a_3 : R.drawable.a_3_dis);
		tab_3_img.setImageResource(next == 3 ? R.drawable.a_4 : R.drawable.a_4_dis);

		if (next != index) {
			findViewById(R.id.tab_selected_main).post(new NextRunable(next));
		}
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
			animation.setDuration(100);
			animation.setFillAfter(true);
			findViewById(R.id.tab_selected).startAnimation(animation);
			index = next;
//			findViewById(R.id.tab_1).setBackgroundResource(next == 0 ? R.drawable.new_tabs_selected : R.drawable.tranfer);
//			findViewById(R.id.tab_2).setBackgroundResource(next == 1 ? R.drawable.new_tabs_selected : R.drawable.tranfer);
//			findViewById(R.id.tab_3).setBackgroundResource(next == 3 ? R.drawable.new_tabs_selected : R.drawable.tranfer);
//			findViewById(R.id.tab_4).setBackgroundResource(next == 2 ? R.drawable.new_tabs_selected : R.drawable.tranfer);

		}
	}
}