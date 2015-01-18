package vnp.com.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.ImageLoader;
import vnp.com.mimusic.util.LogUtils;
import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Base64;

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

	public void refreshToken(final IContsCallBack contsCallBack) {
		Bundle bundle = new Bundle();
		bundle.putString("key", Conts.getRefreshToken(this));
		bundle.putString(User.KEYREFRESH, Conts.getRefreshToken(this));
		execute(RequestMethod.GET, API.API_R013, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				callUpdateData();

				/*
				 * get thong tin dich vu
				 */
				execute(RequestMethod.GET, API.API_R004, new Bundle(), new vnp.com.mimusic.util.Conts.IContsCallBack() {

					@Override
					public void onStart() {

					}

					@Override
					public void onError() {
						onError("");
					}

					@Override
					public void onError(String message) {
						callRecocmment(contsCallBack);
					}

					@Override
					public void onSuscess(JSONObject response) {
						callRecocmment(contsCallBack);
					}
				});

			}

			@Override
			public void onStart() {
				if (contsCallBack != null)
					contsCallBack.onStart();
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
		if (!Conts.isBlank(u))
			bundle.putString("u", u);
		if (!Conts.isBlank(p))
			bundle.putString("p", p);

		LogUtils.e("api login", (is3G ? RequestMethod.GET : RequestMethod.POST) + " " + (is3G ? API.API_R001 : API.API_R002));
		Conts.executeNoProgressBar((is3G ? RequestMethod.GET : RequestMethod.POST), (is3G ? API.API_R001 : API.API_R002), this, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				if (contsCallBack != null)
					contsCallBack.onStart();
			}

			@Override
			public void onSuscess(JSONObject jsonObject) {
				try {
					// LogUtils.e("AAAA", jsonObject.toString());
					String token = jsonObject.getString("token");
					String keyRefresh = jsonObject.getString("keyRefresh");
					String phone_number = jsonObject.getString("phone");
					ContentValues values = new ContentValues();
					values.put(User.USER, phone_number);
					if (!Conts.isBlank(p))
						values.put(User.PASSWORD, p);
					values.put(User.AVATAR, Conts.getString(jsonObject, User.AVATAR));
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

				/*
				 * get thong tin dich vu
				 */
				execute(RequestMethod.GET, API.API_R004, new Bundle(), new vnp.com.mimusic.util.Conts.IContsCallBack() {

					@Override
					public void onStart() {

					}

					@Override
					public void onError() {
						onError("");
					}

					@Override
					public void onError(String message) {
						callRecocmment(contsCallBack);
					}

					@Override
					public void onSuscess(JSONObject response) {
						callRecocmment(contsCallBack);
					}
				});
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

	private void callRecocmment(final IContsCallBack contsCallBack) {
		execute(RequestMethod.GET, API.API_R026, new Bundle(), new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				contsCallBack.onSuscess(null);
			}

			@Override
			public void onStart() {
				//contsCallBack.onSuscess(null);
			}

			@Override
			public void onError(String message) {
				contsCallBack.onSuscess(null);
			}

			@Override
			public void onError() {
				contsCallBack.onSuscess(null);
			}
		});
	}

	private void callUpdateData() {

		/**
		 * get recomment
		 */

		/**
		 * get list dich vu
		 */
		// execute(RequestMethod.GET, API.API_R004, new Bundle(), null);
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

					// String photo_id = Conts.getStringCursor(cur,
					// ContactsContract.Contacts.PHOTO_ID);

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

		// String number = numbers.get(0);
		// numbers.remove(0);
		Bundle bundle = new Bundle();
		// bundle.putString("phonenumber", number);
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
			public void onSuscess(final JSONObject response) {
				new AsyncTask<String, String, String>() {
					@Override
					protected String doInBackground(String... params) {
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
						} else if (API.API_R013.equals(api)) {
							updateReGetToken(response);
						} else if (API.API_R019.equals(api)) {
							updateKiemTraDieuKienThueBao(response);
						} else if (API.API_R026.equals(api)) {
							saveRecomend(response);
						}
						return null;
					}

					protected void onPostExecute(String result) {
						if (contsCallBack != null)
							contsCallBack.onSuscess(response);

						if (API.API_R013.equals(api)) {
							callUpdateData();
						}
					};
				}.execute("");

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

	private void updateKiemTraDieuKienThueBao(JSONObject response) {
		LogUtils.e("updateKiemTraDieuKienThueBao", response.toString());
	}

	private void updateDongBoXuong(JSONObject response) {

		try {
			String user = Conts.getUser(MImusicService.this);
			JSONArray array = response.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String phone = Conts.getString(jsonObject, "phone");
				String name = Conts.getString(jsonObject, "name");
				ContentValues contentValues = new ContentValues();
				contentValues.put(User.USER, phone);
				contentValues.put(User.NAME_CONTACT, name);
				contentValues.put(User.STATUS, user.equals(phone) ? "1" : "0");

				String service_codes = "";
				String service_codes_name = "";
				if (jsonObject.has("services")) {
					JSONArray services = jsonObject.getJSONArray("services");

					String format = "%s";
					int count = 0;
					for (int in = 0; in < services.length(); in++) {
						count++;

						// id,service_name,service_code,service_icon
						String service_code = services.getJSONObject(in).getString("service_code");
						if (Conts.isBlank(service_codes)) {
							service_codes = service_code;
							if (count <= 3)
								service_codes_name = String.format(format, services.getJSONObject(in).getString(DichVu.service_name));
						} else {
							service_codes = service_codes + "," + service_code;
							if (count <= 3)
								service_codes_name = service_codes_name + " | " + String.format(format, services.getJSONObject(in).getString(DichVu.service_name));
						}

						if ("%s".equals(format)) {
							format = "<font color='red'>%s</font>";
						} else {
							format = "%s";
						}
					}
				}

				contentValues.put(User.LISTIDDVSUDUNG, service_codes);
				contentValues.put(User.LISTIDTENDVSUDUNG, service_codes_name);
				if (Conts.haveContact(phone, this)) {
					getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '%s'", User.USER, phone), null);
				} else {
					getContentResolver().insert(User.CONTENT_URI, contentValues);
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
				contentValues.put(DichVu.ID, Conts.getString(jsonObject, DichVu.ID));
				contentValues.put(DichVu.service_name, Conts.getString(jsonObject, DichVu.service_name));
				contentValues.put(DichVu.service_code, Conts.getString(jsonObject, DichVu.service_code));
				contentValues.put(DichVu.service_icon, Conts.getString(jsonObject, DichVu.service_icon));
				contentValues.put(DichVu.service_content, Conts.getString(jsonObject, DichVu.service_content));
				contentValues.put(DichVu.service_price, Conts.getString(jsonObject, DichVu.service_price));
				contentValues.put(DichVu.service_status, Conts.getString(jsonObject, DichVu.service_status));
				String selection = String.format("%s='%s'", DichVu.service_code, jsonObject.getString(DichVu.service_code));
				Cursor cursor = getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

				boolean isUpdate = cursor != null && cursor.getCount() >= 1;

				if (cursor != null) {
					cursor.close();
				}

				if (isUpdate) {
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

	private void updateInFor(JSONObject response) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(User.address, Conts.getString(response, User.address));
		contentValues.put(User.ID, Conts.getString(response, User.ID));
		contentValues.put(User.exchange_number, Conts.getString(response, User.exchange_number));
		contentValues.put(User.exchange_number_month, Conts.getString(response, User.exchange_number_month));
		contentValues.put(User.fullname, Conts.getString(response, User.fullname));
		contentValues.put(User.nickname, Conts.getString(response, User.nickname));
		contentValues.put(User.poundage, Conts.getString(response, User.poundage));
		contentValues.put(User.poundage_month, Conts.getString(response, User.poundage_month));
		contentValues.put(User.birthday, Conts.getString(response, User.birthday));
		String avatar = Conts.getString(response, User.AVATAR);
		LogUtils.e("avatar", avatar + "");
		if (!Conts.isBlank(avatar)) {
			contentValues.put(User.AVATAR, avatar);
		}

		getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
	}

	private void updateReGetToken(JSONObject response) {

		// {"message":"Refresh token success","errorCode":0,"phone":null,"keyRefresh":"1E5571EE-CEF5-483A-50DF-20A6A1D57489","token":"57D4A6E1-B325-A2D6-3CC1-036C6730D1A3"}
		ContentValues contentValues = new ContentValues();
		try {
			contentValues.put(User.KEYREFRESH, response.getString(User.KEYREFRESH));
			contentValues.put(User.TOKEN, response.getString(User.TOKEN));
			getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
		} catch (JSONException e) {
		}

	}

	public void executeUpdateAvatar(String path, final IContsCallBack iContsCallBack) {
		Bundle bundle = new Bundle();
		bundle.putString("images", path);// path
		execute(RequestMethod.GET, API.API_R023, bundle, iContsCallBack);
	}

	private JSONObject recommend;

	public void saveRecomend(JSONObject response) {
		recommend = response;
	}

	public JSONObject getRecommend() {
		return recommend;
	}

	public void executeHttps(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		// contsCallBack.onStart();
		// ProgressConnect connect = new ProgressConnect(this);
		// connect.execute(api, requestMethod, bundle, contsCallBack);

		execute(requestMethod, api, bundle, contsCallBack);
	}

	public void executeUpdateHttpsAvatar(String path, IContsCallBack iContsCallBack) {
		Bundle bundle = new Bundle();

		bundle.putString("images", Conts.encodeToString(this, path));// path
		// todo
		// bundle.putString("file", path);// path
		execute(RequestMethod.POST, API.API_R023, bundle, iContsCallBack);
	}
}