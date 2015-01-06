package vnp.com.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.text.GetChars;
import android.widget.Toast;

public class MImusicService extends Service {

	private MImusicBin mImusicBin;
	public static final String ACTION = "vnp.com.api.MImusicService";
	public static final String KEY = "KEY";
	public static final String VALUE = "VALUE";
	public static final String METHOD = "METHOD";

	public MImusicService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mImusicBin = new MImusicBin(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mImusicBin;
	}

	private List<String> listCallBack = new ArrayList<String>();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if ("add".equals(intent.getStringExtra(KEY))) {
			if (!listCallBack.contains(intent.getStringExtra(VALUE))) {
				listCallBack.add(intent.getStringExtra(VALUE));
			}
		} else if ("remove".equals(intent.getStringExtra(KEY))) {
			if (listCallBack.contains(intent.getStringExtra(VALUE))) {
				listCallBack.remove(intent.getStringExtra(VALUE));
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 
	 * @param is3G
	 * @param u
	 * @param p
	 * @param contsCallBack
	 */
	public void login(boolean is3G, final String u, final String p, final IContsCallBack contsCallBack) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(User.STATUS, "0");
		getContentResolver().update(User.CONTENT_URI, contentValues, null, null);
		Bundle bundle = new Bundle();
		bundle.putString("u", u);
		bundle.putString("p", p);
		Conts.executeNoProgressBar(is3G ? RequestMethod.GET : RequestMethod.POST, is3G ? "authenticate" : "signin", this, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				contsCallBack.onStart();
			}

			@Override
			public void onSuscess(JSONObject jsonObject) {
				try {
					String token = jsonObject.getString("token");
					String keyRefresh = jsonObject.getString("keyRefresh");
					String phone_number = jsonObject.getString("phone");
					ContentValues values = new ContentValues();
					values.put(User.USER, phone_number);
					values.put(User.PASSWORD, p);
					values.put(User.TOKEN, token);
					values.put(User.KEYREFRESH, keyRefresh);
					values.put(User.STATUS, "1");

					String selection = String.format("%s='%s'", User.USER, phone_number);
					Cursor cursor = getContentResolver().query(User.CONTENT_URI, null, selection, null, null);

					boolean isUpdate = cursor != null && cursor.getCount() >= 1;
					cursor.close();

					if (isUpdate) {
						getContentResolver().update(User.CONTENT_URI, values, selection, null);
					} else {
						getContentResolver().insert(User.CONTENT_URI, values);
					}
				} catch (Exception e) {
				}
				contsCallBack.onSuscess(jsonObject);
			}

			@Override
			public void onError(String message) {
				contsCallBack.onError(message);
			}

			@Override
			public void onError() {
				contsCallBack.onError();
			}
		});
	}
}