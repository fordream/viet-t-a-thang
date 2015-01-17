package vnp.com.mimusic.main;

import org.json.JSONObject;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.base.diablog.ReCommnetDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.Conts.IContsCallBackData;
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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.aretha.slidemenu.ISlideMenuListener;
import com.aretha.slidemenu.SlideMenu;
import com.aretha.slidemenu.SlideMenu.OnSlideStateChangeListener;
import com.vnp.core.crash.CrashExceptionHandler;

public class BaseMusicSlideMenuActivity extends TabActivity {
	private SlideMenu mSlideMenu;
	private View recomment_bottom_top;

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(broadcastReceiver, new IntentFilter("broadcastReceivermactivity_slidemenu_menuleft"));
		registerReceiver(broadcastReceiverDongBoDanhBa, new IntentFilter("dongbodanhba"));
		((MenuLeftView) findViewById(R.id.mactivity_slidemenu_menuleft)).showData();
		// ((MenuRightView) findViewById(R.id.mactivity_menu_right)).initData();
	}

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mactivity_slidemenu);
		recomment_bottom_top = findViewById(R.id.recomment_bottom_top);
		CrashExceptionHandler.onCreate(this);
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

		mSlideMenu = (SlideMenu) findViewById(R.id.slideMenu);

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

		// menu left

		MenuLeftView mactivity_slidemenu_menuleft = (MenuLeftView) findViewById(R.id.mactivity_slidemenu_menuleft);
		mactivity_slidemenu_menuleft.findViewById(R.id.menu_footer_f).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getSlideMenu().close(true);
				Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://www.facebook.com/Idea.Sinhvu"));
				startActivity(browserIntent);
			}
		});

		mactivity_slidemenu_menuleft.findViewById(R.id.menu_footer_g).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getSlideMenu().close(true);
			}
		});

		mactivity_slidemenu_menuleft.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				getSlideMenu().close(true);

				if (position == 0) {
					// bang xep hang
					getTabHost().setCurrentTab(1);
				} else if (position == 1) {
					// lich su ban hang
					// getTabHost().setCurrentTab(2);
					Intent intent = new Intent(BaseMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.LICHSUBANHANG);
					intent.putExtras(extras);
					startActivity(intent);
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);
					overridePendingTransitionStartActivity();
				} else if (position == 2) {
					// huong dan ban hang

					Intent intent = new Intent(BaseMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.HUONGDANBANHANG);
					intent.putExtras(extras);
					startActivity(intent);
					overridePendingTransitionStartActivity();
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);
				} else {
					// chinh sua thong tin
					// getTabHost().setCurrentTab(6);
					//
					Intent intent = new Intent(BaseMusicSlideMenuActivity.this, RootMenuActivity.class);
					Bundle extras = new Bundle();
					extras.putString("type", Conts.THONGTINCANHAN);
					intent.putExtras(extras);
					startActivity(intent);
					overridePendingTransitionStartActivity();
					// overridePendingTransition(R.anim.abc_slide_right_in,
					// R.anim.abc_slide_left_out);

				}
			}
		});

		// tabview
		TabView mactivityslide_menu_tabview = (TabView) findViewById(R.id.mactivityslide_menu_tabview);
		mactivityslide_menu_tabview.setOnClickListener(tabOnClick, homeOnClick);

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
					}

					@Override
					public void onStart() {
						if (progressDialog == null) {
							progressDialog = ProgressDialog.show(BaseMusicSlideMenuActivity.this, null, getString(R.string.loading));
						}
					}

					@Override
					public void onError(String message) {
						if (progressDialog != null) {
							progressDialog.dismiss();
						}
					}

					@Override
					public void onError() {
						onError("onError");
					}
				});
			}
		});
		mactivity_menu_right.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				Cursor cursor = (Cursor) arg0.getItemAtPosition(position);
				final String _id = cursor.getString(cursor.getColumnIndex(User._ID));

				getSlideMenu().close(true);
				getSlideMenu().postDelayed(new Runnable() {
					@Override
					public void run() {

						Intent intent = new Intent(BaseMusicSlideMenuActivity.this, RootMenuActivity.class);
						intent.putExtra("type", Conts.NHIEUDICHVU);
						intent.putExtra(User._ID, _id + "");
						startActivity(intent);
						overridePendingTransitionStartActivity();
					}
				}, 100);
			}
		});
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
				Conts.hiddenKeyBoard(BaseMusicSlideMenuActivity.this);
			}

			@Override
			public void onSlideOffsetChange(float offsetPercent) {
			}
		});

		/**
		 * recommnet
		 */

		findViewById(R.id.recomment_bottom_top).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getSlideMenu().close(true);
				// TODO
				showReCmmend();
			}

		});

		findViewById(R.id.recomment_bottom_top).setVisibility(View.GONE);

		((VApplication) getApplication()).getReommend(new IContsCallBackData() {

			@Override
			public void onCallBack(Object response) {
				responseRecommend = (JSONObject) response;

				if (responseRecommend != null) {
					findViewById(R.id.recomment_bottom_top).setVisibility(View.VISIBLE);
					showReCmmend();
				}
			}
		});

	}

	protected void showReCmmend() {
		ReCommnetDialog reCommnetDialog = new ReCommnetDialog(BaseMusicSlideMenuActivity.this, responseRecommend) {
			@Override
			public void _addDv(String id) {
				Intent intent = new Intent(BaseMusicSlideMenuActivity.this, RootMenuActivity.class);
				intent.putExtra("type", Conts.CHITIETDICHVU);
				intent.putExtra(DichVu.service_code, id);
				startActivity(intent);
				overridePendingTransitionStartActivity();
			}

			@Override
			public void _addContact(String msisdn, String name) {
				Intent intent = new Intent(BaseMusicSlideMenuActivity.this, RootMenuActivity.class);
				intent.putExtra("type", Conts.NHIEUDICHVU);
				intent.putExtra("msisdn", msisdn);
				intent.putExtra("name", name);
				intent.putExtra(User._ID, "");
				startActivity(intent);
				overridePendingTransitionStartActivity();
			}
		};
		reCommnetDialog.show();
	}

	private JSONObject responseRecommend;
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
		// Toast.makeText(this, "out", Toast.LENGTH_SHORT).show();
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
						overridePendingTransition(R.anim.abc_nothing, R.anim.abc_slide_out_bottom);
					}
				}, 100);
			}
		};
		dangKyDialog.show();
	}

	private void overridePendingTransitionStartActivity() {
		overridePendingTransition(R.anim.abc_scale_in, R.anim.abc_nothing);

	}
}
