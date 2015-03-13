package vnp.com.mimusic.main;

import org.json.JSONObject;

import vnp.com.db.VasContact;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.base.diablog.VasProgessDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.MenuLeftView;
import vnp.com.mimusic.view.MenuRightView;
import vnp.com.mimusic.view.TabView;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.vnp.core.crash.CrashExceptionHandler;

public class NewMusicSlideMenuActivity extends TabActivity {
	private android.support.v4.widget.DrawerLayout drawerLayout;

	@Override
	protected void onPause() {
		super.onPause();
		unregisterReceiver(broadcastReceiver);
		unregisterReceiver(broadcastReceiverDongBoDanhBa);
	}

	private BroadcastReceiver broadcastReceiverDongBoDanhBa = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			((MenuRightView) findViewById(R.id.mactivity_menu_right)).initData();
		}
	};
	private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			((MenuLeftView) findViewById(R.id.mactivity_slidemenu_menuleft)).showData();
		}
	};

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, new IntentFilter("broadcastReceivermactivity_slidemenu_menuleft"));
		registerReceiver(broadcastReceiverDongBoDanhBa, new IntentFilter("dongbodanhba"));

		if (findViewById(R.id.mactivity_slidemenu_menuleft) != null) {
			((MenuLeftView) findViewById(R.id.mactivity_slidemenu_menuleft)).showData();
		}

		if (findViewById(R.id.mactivity_menu_right) != null) {
			MenuRightView mactivity_menu_right = (MenuRightView) findViewById(R.id.mactivity_menu_right);
			mactivity_menu_right.initData();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		CrashExceptionHandler.onCreate(this);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		drawerLayout.setDrawerListener(new DrawerListener() {

			@Override
			public void onDrawerStateChanged(int arg0) {

			}

			@Override
			public void onDrawerSlide(View arg0, float arg1) {

			}

			@Override
			public void onDrawerOpened(View arg0) {

			}

			@Override
			public void onDrawerClosed(View arg0) {
				Conts.hiddenKeyBoard(NewMusicSlideMenuActivity.this);
			}
		});
		
		
		getTabHost().setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {
				int[] menus = new int[] {//
				R.string.kenhbanvas,//
						R.string.bangxephang,//
						R.string.lichsubanhang,//
						R.string.quydinhbanhang,//
						R.string.huongdanbanhang,//
						R.string.dichvu,//
						R.string.thuongtinnguoidung,//
						R.string.timkiem,//
						R.string.orther, //
						R.string.tintuc };//

				TabView mactivityslide_menu_tabview = (TabView) findViewById(R.id.mactivityslide_menu_tabview);
				mactivityslide_menu_tabview.setTextHeader(menus[getTabHost().getCurrentTab()]);
				mactivityslide_menu_tabview.updateTab(menus[getTabHost().getCurrentTab()]);
			}
		});
		String[] menus = new String[] {//
		Conts.HOME,//
				Conts.BANGXEPHANG,//
				Conts.LICHSUBANHANG,//
				Conts.QUYDINHBANHANG,//
				Conts.HUONGDANBANHANG,//
				Conts.DICHVU,//
				Conts.THONGTINCANHAN,//
				Conts.TIMKIEM,//
				Conts.ORTHER, //
				Conts.TINTUC };//

		for (String menu : menus) {
			addTab(RootMenuActivity.class, menu, menu, menu);
		}
		TabView mactivityslide_menu_tabview = (TabView) findViewById(R.id.mactivityslide_menu_tabview);
		mactivityslide_menu_tabview.setOnClickListener(tabOnClick, homeOnClick);
		
		configMenuLeft();

		configMenuRight();
		
		
	}

	private void configMenuRight() {
		// Menu Right
		final MenuRightView mactivity_menu_right = (MenuRightView) findViewById(R.id.mactivity_menu_right);
		mactivity_menu_right.initData();

		mactivity_menu_right.findViewById(R.id.menu_right_img_search).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((VApplication) getApplication()).callDongBoDanhBaLen(new IContsCallBack() {
					private ProgressDialog progressDialog;

					@Override
					public void onSuscess(JSONObject response) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}

						Conts.showDialogThongbao(NewMusicSlideMenuActivity.this, getString(R.string.message_dongbo));
					}

					@Override
					public void onStart() {
						if (progressDialog == null) {
							progressDialog = new VasProgessDialog(NewMusicSlideMenuActivity.this);
							progressDialog.show();
						}
					}

					@Override
					public void onError(String message) {

						Conts.showDialogThongbao(NewMusicSlideMenuActivity.this, message);
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}

				});
			}
		});
		mactivity_menu_right.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Cursor cursor = (Cursor) arg0.getItemAtPosition(position);
				final String _id = cursor.getString(cursor.getColumnIndex(VasContact._ID));

				closeMenu();

				Intent intent = new Intent(NewMusicSlideMenuActivity.this, RootMenuActivity.class);
				intent.putExtra("type", Conts.NHIEUDICHVU);
				intent.putExtra(VasContact._ID, _id + "");
				intent.putExtra("getPosition", cursor.getPosition());
				startActivity(intent);
				overridePendingTransitionStartActivity();
			}
		});
	}

	private void configMenuLeft() {
		MenuLeftView mactivity_slidemenu_menuleft = (MenuLeftView) findViewById(R.id.mactivity_slidemenu_menuleft);

		mactivity_slidemenu_menuleft.findViewById(R.id.menu_footer_t).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeMenu();

			}
		});
		mactivity_slidemenu_menuleft.findViewById(R.id.menu_footer_f).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeMenu();

			}
		});

		mactivity_slidemenu_menuleft.findViewById(R.id.menu_footer_g).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				closeMenu();
			}
		});
		mactivity_slidemenu_menuleft.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				closeMenu();

				if (position == 0) {
					// bang xep hang
					getTabHost().setCurrentTab(1);
				} else if (position == 1) {
					// lich su ban hang
					// getTabHost().setCurrentTab(2);
					Intent intent = new Intent(NewMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.LICHSUBANHANG);
					intent.putExtras(extras);
					startActivity(intent);
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);
					overridePendingTransitionStartActivity();
				} else if (position == 2) {
					// huong dan ban hang

					Intent intent = new Intent(NewMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.HUONGDANBANHANG);
					intent.putExtras(extras);
					startActivity(intent);
					overridePendingTransitionStartActivity();
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);
				} else if (position == 3) {
					// chinh sua thong tin
					// getTabHost().setCurrentTab(6);
					//
					Intent intent = new Intent(NewMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.THONGTINCANHAN);
					intent.putExtras(extras);
					startActivity(intent);
					overridePendingTransitionStartActivity();
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);

				} else if (position == 4) {
					// chinh sua thong tin
					// getTabHost().setCurrentTab(6);
					//
					Intent intent = new Intent(NewMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.MOISUDUNGUNGDUNG);
					intent.putExtras(extras);
					startActivity(intent);
					overridePendingTransitionStartActivity();
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);

				}
			}
		});
	}

	protected void closeMenu() {
		drawerLayout.closeDrawers();
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		finish(true);

	}

	public void finish(boolean b) {

		if (drawerLayout.isDrawerOpen(Gravity.START) || drawerLayout.isDrawerOpen(Gravity.END)) {
			drawerLayout.closeDrawers();
			return;
		}

		ContentValues contentValues = new ContentValues();
		contentValues.put("btn_right", getString(R.string.dongy));
		contentValues.put("name", getString(R.string.banmuondongugndung));
		contentValues.put("content", getString(R.string.close_ungdung_comfirm));
		final DangKyDialog dangKyDialog = new DangKyDialog(this, contentValues) {
			@Override
			public void mOpen() {
				super.mOpen();
				dismiss();
				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						finish();
						overridePendingTransition(R.anim.abc_nothing, R.anim.abc_slide_right_out);
					}
				}, 100);
			}
		};
		dangKyDialog.show();
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
				getTabHost().setCurrentTab(1);
			}
		}
	};

	public void openMenuLeft() {
		drawerLayout.openDrawer(Gravity.START);
	}

	public void openMenuRight() {
		drawerLayout.openDrawer(Gravity.END);
	}

	private void overridePendingTransitionStartActivity() {
		overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_nothing);
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
}
