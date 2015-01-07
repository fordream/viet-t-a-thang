package vnp.com.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;

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
		Conts.executeNoProgressBar(is3G ? RequestMethod.GET : RequestMethod.POST, is3G ? API.API_R001 : API.API_R002, this, bundle, new IContsCallBack() {
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

	public void execute(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {

		Conts.executeNoProgressBar(requestMethod, api, this, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				contsCallBack.onStart();
			}

			@Override
			public void onSuscess(JSONObject response) {
				if (API.API_R006.equals(api)) {
					updateInFor(response);
				} else if (API.API_R007.equals(api)) {
					updateInFor(bundle);
				} else if (API.API_R017.equals(api)) {
					updateDichVuDangKy(bundle);
				}

				contsCallBack.onSuscess(response);
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

	protected void updateDichVuDangKy(Bundle bundle) {
		String cs = bundle.getString(DichVu.service_code);
		ContentValues contentValues = new ContentValues();
		contentValues.put(DichVu.service_status, "0");
		getContentResolver().update(DichVu.CONTENT_URI, contentValues, String.format("%s=='%s'", DichVu.service_code, cs), null);
	}

	protected void updateInFor(Bundle bundle) {
		ContentValues contentValues = new ContentValues();
		Set<String> keys = bundle.keySet();
		for (String key : keys) {
			contentValues.put(key, bundle.getString(key));
		}
		getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
	}

	/**
	 * save infor
	 * 
	 * @param response
	 */

	public void updateInFor(JSONObject response) {
		ContentValues contentValues = new ContentValues();
		try {
			contentValues.put(User.address, response.getString(User.address));
			contentValues.put(User.ID, response.getString(User.ID));
			contentValues.put(User.exchange_number, response.getString(User.exchange_number));
			contentValues.put(User.exchange_number_month, response.getString(User.exchange_number_month));
			contentValues.put(User.fullname, response.getString(User.fullname));
			contentValues.put(User.nickname, response.getString(User.nickname));
			contentValues.put(User.poundage, response.getString(User.poundage));
			contentValues.put(User.poundage_month, response.getString(User.poundage_month));
			contentValues.put(User.birthday, response.getString(User.birthday));
			getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
		} catch (JSONException e) {
		}
	}
}