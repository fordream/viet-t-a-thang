package vnp.com.mimusic;

import vnp.com.api.MImusicBin;
import vnp.com.api.MImusicService;
import vnp.com.db.User;
import android.app.Application;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.IBinder;
import android.provider.ContactsContract;

public class VApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	/**
	 * service config
	 */
	private MImusicService mImusicService;

	public MImusicService getmImusicService() {
		return mImusicService;
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

	/**
	 * use dongbo danhba
	 */
	private boolean isRunDongBo = false;

	public void dongbodanhba() {
		if (!isRunDongBo) {
			isRunDongBo = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					ContentResolver cr = getContentResolver();
					Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
					while (cur != null && cur.moveToNext()) {
						String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
						String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
							Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
							while (pCur.moveToNext()) {
								String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
								if (name == null || name != null && name.trim().equals("")) {
									name = phoneNo;
								}

								// save to contact DB
								ContentValues values = new ContentValues();
								values.put(User.USER, phoneNo);
								values.put(User.NAME_CONTACT, name);
								String selection = String.format("%s ='%s'", User.USER, phoneNo);
								Cursor cursor = getContentResolver().query(User.CONTENT_URI, null, selection, null, null);

								if (cursor != null && cursor.getCount() >= 1) {
									cursor.close();
									getContentResolver().update(User.CONTENT_URI, values, selection, null);
								} else {
									cursor.close();
									values.put(User.STATUS, "0");
									getContentResolver().insert(User.CONTENT_URI, values);
								}

							}
							pCur.close();
						}
					}

					isRunDongBo = false;
				}
			}).start();
		}
	}

	// public static final String KEY = "KEY";
	// public static final String METHOD = "METHOD";
	//
	// public void callApi(Intent intent) {
	// if (intent != null) {
	// Conts.execute((RequestMethod) intent.getSerializableExtra(METHOD),
	// intent.getStringExtra(KEY), this, new Bundle(), new IContsCallBack() {
	// @Override
	// public void onSuscess(JSONObject response) {
	//
	// }
	//
	// @Override
	// public void onError(String message) {
	//
	// }
	//
	// @Override
	// public void onError() {
	//
	// }
	// });
	// }
	// }
}