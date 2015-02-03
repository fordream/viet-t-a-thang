package vnp.com.mimusic;

import vnp.com.api.MImusicBin;
import vnp.com.api.MImusicService;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.FontsUtils;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

public class VApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		FontsUtils.getInstance().init(this);
		ImageLoaderUtils.getInstance(this);
	}

	/**
	 * service config
	 */
	private MImusicService mImusicService;

	// public MImusicService getmImusicService() {
	// return mImusicService;
	// }
	public void execute(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.execute(requestMethod, api, bundle, contsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.execute(requestMethod, api, bundle, contsCallBack);

		}
	}

	public void executeUpdateAvatar(final String path, final IContsCallBack iContsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.executeUpdateAvatar(path, iContsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.executeUpdateAvatar(path, iContsCallBack);
		}
	}

	public void init(final IServiceConfig config) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
					config.onServiceDisconnected();
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					config.onServiceConnected();
				}
			}, BIND_AUTO_CREATE);
		} else {
			config.onServiceConnected();
		}
	}

	public interface IServiceConfig {
		public void onServiceConnected();

		public void onServiceDisconnected();
	}

	public void login(final boolean b, final String u, final String p, final IContsCallBack iContsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.login(b, u, p, iContsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.login(b, u, p, iContsCallBack);
		}
	}

	public void refreshToken(final IContsCallBack iContsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.refreshToken(iContsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.refreshToken(iContsCallBack);
		}
	}

	public void callDongBoDanhBaLen(final IContsCallBack iContsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.callDongBoDanhBaLen(iContsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.callDongBoDanhBaLen(iContsCallBack);
		}
	}

	public void executeHttps(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.executeHttps(requestMethod, api, bundle, contsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.executeHttps(requestMethod, api, bundle, contsCallBack);
		}
	}

	// public void saveRecomend(final JSONObject response) {
	// if (mImusicService == null) {
	// Intent service = new Intent(MImusicService.ACTION);
	// bindService(service, new ServiceConnection() {
	//
	// @Override
	// public void onServiceDisconnected(ComponentName name) {
	// mImusicService = null;
	// }
	//
	// @Override
	// public void onServiceConnected(ComponentName name, IBinder service) {
	// mImusicService = ((MImusicBin) service).getService();
	// mImusicService.saveRecomend(response);
	// }
	// }, BIND_AUTO_CREATE);
	// } else {
	// mImusicService.saveRecomend(response);
	// }
	// }

	// public void getReommend(final IContsCallBackData contsCallBackData) {
	// if (mImusicService == null) {
	// Intent service = new Intent(MImusicService.ACTION);
	// bindService(service, new ServiceConnection() {
	//
	// @Override
	// public void onServiceDisconnected(ComponentName name) {
	// mImusicService = null;
	// }
	//
	// @Override
	// public void onServiceConnected(ComponentName name, IBinder service) {
	// mImusicService = ((MImusicBin) service).getService();
	// contsCallBackData.onCallBack(mImusicService.getRecommend());
	// }
	// }, BIND_AUTO_CREATE);
	// } else {
	// contsCallBackData.onCallBack(mImusicService.getRecommend());
	// }
	// }

	public void executeUpdateHttpsAvatar(final String path, final IContsCallBack iContsCallBack) {
		if (mImusicService == null) {
			Intent service = new Intent(MImusicService.ACTION);
			bindService(service, new ServiceConnection() {

				@Override
				public void onServiceDisconnected(ComponentName name) {
					mImusicService = null;
				}

				@Override
				public void onServiceConnected(ComponentName name, IBinder service) {
					mImusicService = ((MImusicBin) service).getService();
					mImusicService.executeUpdateHttpsAvatar(path, iContsCallBack);
				}
			}, BIND_AUTO_CREATE);
		} else {
			mImusicService.executeUpdateHttpsAvatar(path, iContsCallBack);
		}
	}

	/**
	 * use dongbo danhba
	 */
	// private boolean isRunDongBo = false;
	//
	// public void dongbodanhba() {
	// if (!isRunDongBo) {
	// isRunDongBo = true;
	// new Thread(new Runnable() {
	// @Override
	// public void run() {
	// ContentResolver cr = getContentResolver();
	// Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null,
	// null, null);
	// while (cur != null && cur.moveToNext()) {
	// String id =
	// cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
	// String name =
	// cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	// if
	// (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))
	// > 0) {
	// Cursor pCur =
	// cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
	// ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]
	// { id }, null);
	// while (pCur.moveToNext()) {
	// String phoneNo =
	// pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	// if (!Conts.isBlank(phoneNo)) {
	// phoneNo = phoneNo.replace(" ", "");
	// }
	// if (name == null || name != null && name.trim().equals("")) {
	// name = phoneNo;
	// }
	//
	// // save to contact DB
	// ContentValues values = new ContentValues();
	// values.put(User.USER, phoneNo);
	// values.put(User.NAME_CONTACT, name);
	// String selection = String.format("%s ='%s'", User.USER, phoneNo);
	// Cursor cursor = getContentResolver().query(User.CONTENT_URI, null,
	// selection, null, null);
	//
	// if (cursor != null && cursor.getCount() >= 1) {
	// cursor.close();
	// getContentResolver().update(User.CONTENT_URI, values, selection, null);
	// } else {
	// cursor.close();
	// values.put(User.STATUS, "0");
	// getContentResolver().insert(User.CONTENT_URI, values);
	// }
	// }
	// pCur.close();
	// }
	// }
	//
	// isRunDongBo = false;
	// }
	// }).start();
	// }
	// }
}