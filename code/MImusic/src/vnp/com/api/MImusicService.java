package vnp.com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;

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
				if (contsCallBack != null)
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
				callUpdateData();

				if (contsCallBack != null)
					contsCallBack.onSuscess(jsonObject);

				// TODO
			}

			@Override
			public void onError(String message) {
				if (contsCallBack != null)
					contsCallBack.onError(message);
			}

			@Override
			public void onError() {
				if (contsCallBack != null)
					contsCallBack.onError();
			}
		});
	}

	private void callUpdateData() {
		/**
		 * get list dich vu
		 */
		execute(RequestMethod.GET, API.API_R004, new Bundle(), null);
		/**
		 * get infor
		 */
		execute(RequestMethod.GET, API.API_R006, new Bundle(), new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				sendBroadcast(new Intent("broadcastReceivermactivity_slidemenu_menuleft"));
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String message) {

			}

			@Override
			public void onError() {

			}
		});

		callDongBoDanhBaLen(new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {

			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String message) {

			}

			@Override
			public void onError() {

			}
		});
	}

	public void callDongBoDanhBaLen(final IContsCallBack contsCallBack) {

		if (contsCallBack != null) {
			contsCallBack.onStart();
		}
		new AsyncTask<String, String, StringBuilder>() {
			List<String> listSdt = new ArrayList<String>();

			@Override
			protected StringBuilder doInBackground(String... params) {
				StringBuilder conttacts = new StringBuilder();
				ContentResolver cr = getContentResolver();
				Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
				while (cur != null && cur.moveToNext()) {
					String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
					if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
						while (pCur.moveToNext()) {
							String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							if (!Conts.isBlank(phoneNo)) {
								phoneNo = phoneNo.replace(" ", "");
							}
							if (Conts.isBlank(name)) {
								name = phoneNo;
							}

							if (!Conts.isBlank(phoneNo)) {
								listSdt.add(phoneNo);
								if (conttacts.length() == 0) {
									conttacts.append(String.format("{\"phone\":\"%s\",\"name\":\"%s\"}", phoneNo, name));
								} else {
									conttacts.append(String.format(",{\"phone\":\"%s\",\"name\":\"%s\"}", phoneNo, name));
								}
							}
						}

						pCur.close();
					}
				}
				return conttacts;
			}

			protected void onPostExecute(StringBuilder result) {
				if (result.length() == 0) {
					dongboDanhBaXuong(contsCallBack, listSdt);
				} else {
					Bundle bundle = new Bundle();
					bundle.putString("contacts", String.format("[%s]", result.toString()));
					MImusicService.this.execute(RequestMethod.POST, API.API_R011, bundle, new IContsCallBack() {
						@Override
						public void onSuscess(JSONObject response) {
							startDongBoDAnhBaXuong();
						}

						@Override
						public void onStart() {
						}

						@Override
						public void onError(String message) {
							startDongBoDAnhBaXuong();
						}

						@Override
						public void onError() {
							startDongBoDAnhBaXuong();
						}

						private void startDongBoDAnhBaXuong() {
							dongboDanhBaXuong(contsCallBack, listSdt);
						}
					});
				}
			};
		}.execute("");

	}

	public void dongboDanhBaXuong(final IContsCallBack contsCallBack, final List<String> numbers) {

		String number = numbers.get(0);
		numbers.remove(0);
		Bundle bundle = new Bundle();
		bundle.putString("phonenumber", number);
		execute(RequestMethod.GET, API.API_R012, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				sendBroadcast(new Intent("dongbodanhba"));
				contsCallBack.onSuscess(response);
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String message) {
				if (contsCallBack != null) {
					contsCallBack.onError(message);
				}
			}

			@Override
			public void onError() {
				if (contsCallBack != null) {
					contsCallBack.onError();
				}
			}
		});
	}

	public void execute(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {

		Conts.executeNoProgressBar(requestMethod, api, this, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				if (contsCallBack != null)
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
				} else if (API.API_R004.equals(api)) {
					updateDichVu(response);
				} else if (API.API_R012.equals(api)) {
					updateDongBoXuong(response);
				}

				if (contsCallBack != null)
					contsCallBack.onSuscess(response);
			}

			@Override
			public void onError(String message) {
				if (contsCallBack != null)
					contsCallBack.onError(message);
			}

			@Override
			public void onError() {
				if (contsCallBack != null)
					contsCallBack.onError();
			}
		});
	}

	private void updateDongBoXuong(JSONObject response) {
		try {
			JSONArray array = response.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String phone = Conts.getString(jsonObject, "phone");
				String name = Conts.getString(jsonObject, "name");

				ContentValues contentValues = new ContentValues();
				contentValues.put(User.USER, phone);
				contentValues.put(User.NAME_CONTACT, name);
				contentValues.put(User.STATUS, "0");
				if (Conts.haveContact(phone, this)) {
					getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '%s'", User.USER, phone), null);
				} else {
					getContentResolver().insert(User.CONTENT_URI, contentValues);
				}

				if (jsonObject.has("services")) {
					JSONArray services = jsonObject.getJSONArray("services");
					// id,service_name,service_code,service_icon
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateDichVu(JSONObject response) {
		try {
			JSONArray jsonArray = response.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				ContentValues contentValues = new ContentValues();
				contentValues.put(DichVu.ID, jsonObject.getString(DichVu.ID));
				contentValues.put(DichVu.service_name, jsonObject.getString(DichVu.service_name));
				contentValues.put(DichVu.service_code, jsonObject.getString(DichVu.service_code));
				contentValues.put(DichVu.service_icon, jsonObject.getString(DichVu.service_icon));
				contentValues.put(DichVu.service_content, jsonObject.getString(DichVu.service_content));
				contentValues.put(DichVu.service_price, jsonObject.getString(DichVu.service_price));
				contentValues.put(DichVu.service_status, jsonObject.getString(DichVu.service_status));

				String selection = String.format("%s='%s'", DichVu.ID, jsonObject.getString(DichVu.ID));
				Cursor cursor = getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

				if (cursor != null && cursor.getCount() >= 1) {
					cursor.close();
					getContentResolver().update(DichVu.CONTENT_URI, contentValues, selection, null);
				} else {
					getContentResolver().insert(DichVu.CONTENT_URI, contentValues);
				}
			}
		} catch (JSONException e) {
		}
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