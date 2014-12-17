package vnp.com.mimusic.main;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.MenuLeftView;
import vnp.com.mimusic.view.MenuRightView;
import vnp.com.mimusic.view.TabView;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.aretha.slidemenu.ISlideMenuListener;
import com.aretha.slidemenu.SlideMenu;
import com.aretha.slidemenu.SlideMenu.OnSlideStateChangeListener;

public class BaseMusicSlideMenuActivity extends TabActivity {
	private SlideMenu mSlideMenu;

	public static void hiddenKeyBoard(Activity activity) {
		try {
			String service = Context.INPUT_METHOD_SERVICE;
			InputMethodManager imm = null;
			imm = (InputMethodManager) activity.getSystemService(service);
			IBinder binder = activity.getCurrentFocus().getWindowToken();
			imm.hideSoftInputFromWindow(binder, 0);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_slide_left_out);
		setContentView(R.layout.mactivity_slidemenu);
		mSlideMenu = (SlideMenu) findViewById(R.id.slideMenu);

		String[] menus = new String[] {//
		Conts.HOME,//
				Conts.BANGXEPHANG,//
				Conts.LICHSUBANHANG,//
				Conts.QUYDINHBANHANG,//
				Conts.HUONGDANBANHANG,//
				Conts.DICHVU,//
				Conts.INFOR,//
				Conts.TIMKIEM,//
				Conts.ORTHER, //
				Conts.TINTUC };//

		for (String menu : menus) {
			addTab(RootMenuActivity.class, menu, menu, menu);
		}

		// menu left

		MenuLeftView mactivity_slidemenu_menuleft = (MenuLeftView) findViewById(R.id.mactivity_slidemenu_menuleft);
		mactivity_slidemenu_menuleft.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getSlideMenu().close(true);

				if (position == 0) {
					// bang xep hang
					getTabHost().setCurrentTab(1);
				} else if (position == 1) {
					// lich su ban hang
					getTabHost().setCurrentTab(2);
				} else if (position == 2) {
					// Quy dinh ban hang
					getTabHost().setCurrentTab(3);
				} else if (position == 3) {
					// huong dan ban hang
					getTabHost().setCurrentTab(4);
				} else {
					// chinh sua thong tin
					getTabHost().setCurrentTab(6);
				}
			}
		});

		// tabview
		TabView mactivityslide_menu_tabview = (TabView) findViewById(R.id.mactivityslide_menu_tabview);
		mactivityslide_menu_tabview.setOnClickListener(tabOnClick, homeOnClick);

		// Menu Right
		final MenuRightView mactivity_menu_right = (MenuRightView) findViewById(R.id.mactivity_menu_right);
		mactivity_menu_right.initData();

		getSlideMenu().setiSlideMenuListener(new ISlideMenuListener() {

			@Override
			public boolean canUseBackMenu() {
				if (getSlideMenu().getCurrentState() == SlideMenu.STATE_OPEN_RIGHT) {
					return !mactivity_menu_right.needBack();
				}

				return true;
			}
		});

		getSlideMenu().setOnSlideStateChangeListener(new OnSlideStateChangeListener() {

			@Override
			public void onSlideStateChange(int slideState) {

				Log.e("onSlideStateChange", slideState + "");
				hiddenKeyBoard(BaseMusicSlideMenuActivity.this);
			}

			@Override
			public void onSlideOffsetChange(float offsetPercent) {
			}
		});

	}

	private View.OnClickListener homeOnClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.header_btn_left) {
				openMenuLeft();
			} else if (v.getId() == R.id.header_btn_right) {
				openMenuRight();
			}
		}
	};

	private View.OnClickListener tabOnClick = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.tab_1) {
				getTabHost().setCurrentTab(0);
			} else if (v.getId() == R.id.tab_2) {
				getTabHost().setCurrentTab(5);
			} else if (v.getId() == R.id.tab_3) {
				getTabHost().setCurrentTab(9);
			} else if (v.getId() == R.id.tab_4) {
				getTabHost().setCurrentTab(7);
			}
		}
	};

	public void openMenuLeft() {
		getSlideMenu().open(false, true);
	}

	public void openMenuRight() {
		getSlideMenu().open(true, true);
	}

	public void setSlideRole(int res) {
		if (null == mSlideMenu) {
			return;
		}

		getLayoutInflater().inflate(res, mSlideMenu, true);
	}

	public SlideMenu getSlideMenu() {
		return mSlideMenu;
	}

	public void addTab(Class<?> activity, String tabSpect, String indicator, String type) {
		TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
		TabSpec firstTabSpec = tabHost.newTabSpec(tabSpect);
		Intent intent = new Intent(this, activity);
		Bundle extras = new Bundle();
		extras.putString("type", type);
		intent.putExtras(extras);
		firstTabSpec.setIndicator(indicator).setContent(intent);
		tabHost.addTab(firstTabSpec);
	}

	public boolean isMenuOpen() {
		return false;
	}

	public void finish(boolean b) {
		finish();
		overridePendingTransition(R.anim.abc_slide_left_in, R.anim.abc_slide_right_out);
	}
}