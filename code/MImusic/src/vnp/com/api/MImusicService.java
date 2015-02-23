package vnp.com.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.BangXepHang;
import vnp.com.db.MauMoi;
import vnp.com.db.Recomment;
import vnp.com.db.User;
import vnp.com.db.datastore.AccountStore;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.db.datastore.HuongDanBanHangStore;
import vnp.com.db.datastore.TintucStore;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.LogUtils;
import android.app.Service;
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
	private Map<String, String> avatarHashmap = new HashMap<String, String>();
	private AccountStore accountStore;
	private DichVuStore dichVuStore;

	public MImusicService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mImusicBin = new MImusicBin(this);
		accountStore = new AccountStore(this);
		dichVuStore = new DichVuStore(this);
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
		bundle.putString("key", accountStore.getRefreshToken());
		bundle.putString(User.KEYREFRESH, accountStore.getRefreshToken());
		execute(RequestMethod.GET, API.API_R013, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {

				/*
				 * get thong tin dich vu
				 */

				exeDichvu(contsCallBack);
				// execute(RequestMethod.GET, API.API_R004, new Bundle(), new
				// vnp.com.mimusic.util.Conts.IContsCallBack() {
				//
				// @Override
				// public void onStart() {
				//
				// }
				//
				// @Override
				// public void onError() {
				// onError("");
				// }
				//
				// @Override
				// public void onError(String message) {
				// if (contsCallBack != null) {
				// contsCallBack.onError(message);
				// }
				// }
				//
				// @Override
				// public void onSuscess(JSONObject response) {
				// if (contsCallBack != null) {
				// contsCallBack.onSuscess(response);
				// }
				//
				// callUpdateData();
				// }
				// });

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
		});
	}

	/**
	 * 
	 * @param is3G
	 * @param u
	 * @param p
	 * @param contsCallBack
	 */
	public void login(final boolean is3G, final String u, final String p, final IContsCallBack contsCallBack) {
		Bundle bundle = new Bundle();
		if (!Conts.isBlank(u)) {
			bundle.putString("u", u);
		}

		if (!Conts.isBlank(p)) {
			bundle.putString("p", p);
		}

		Conts.executeNoProgressBar((is3G ? RequestMethod.GET : RequestMethod.POST), (is3G ? API.API_R001 : API.API_R002), this, bundle, new IContsCallBack() {

			@Override
			public void onStart() {
				if (contsCallBack != null) {
					contsCallBack.onStart();
				}

			}

			@Override
			public void onSuscess(JSONObject jsonObject) {
				long time = System.currentTimeMillis();
				accountStore.save(jsonObject, p);
				exeDichvu(contsCallBack);
			}

			@Override
			public void onError(String message) {
				if (contsCallBack != null) {
					contsCallBack.onError(message);
				}
			}

		});

	}

	private void callUpdateData() {

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
				Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
				while (cur != null && cur.moveToNext()) {
					String contact_id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
					String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					String photo_id = Conts.getStringCursor(cur, ContactsContract.Contacts.PHOTO_ID);

					if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
						Cursor pCur = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
								new String[] { contact_id }, null);
						while (pCur.moveToNext()) {
							String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							if (!Conts.isBlank(phoneNo)) {
								phoneNo = phoneNo.replace(" ", "");
							}

							if (Conts.isBlank(name)) {
								name = phoneNo;
							}

							if (!Conts.isBlank(phoneNo)) {
								if (!listSdt.contains(phoneNo)) {

									listSdt.add(phoneNo);
									if (!Conts.isBlank(phoneNo) && !Conts.isBlank(photo_id)) {
										addPhoneId(phoneNo, contact_id);

									}
									if (conttacts.length() == 0) {
										conttacts.append(String.format("{\"phone\":\"%s\",\"name\":\"%s\"}", phoneNo, name));
									} else {
										conttacts.append(String.format(",{\"phone\":\"%s\",\"name\":\"%s\"}", phoneNo, name));
									}
								}
							}
						}

						if (pCur != null) {
							pCur.close();
						}
					}
				}

				if (cur != null) {
					cur.close();
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

						private void startDongBoDAnhBaXuong() {
							dongboDanhBaXuong(contsCallBack, listSdt);
						}
					});
				}
			};
		}.execute("");

	}

	private void addPhoneId(String phoneNo, String photo_id) {

		if (!Conts.isBlank(phoneNo) && !Conts.isBlank(photo_id)) {
			if (phoneNo.startsWith("+840")) {
				phoneNo = "84" + phoneNo.substring("+840".length(), phoneNo.length());
			} else if (phoneNo.startsWith("+84")) {
				phoneNo = "84" + phoneNo.substring("+84".length(), phoneNo.length());
			} else if (phoneNo.startsWith("840")) {
				phoneNo = "84" + phoneNo.substring("840".length(), phoneNo.length());
			} else if (phoneNo.startsWith("84")) {
				phoneNo = "84" + phoneNo.substring("84".length(), phoneNo.length());
			} else if (phoneNo.startsWith("0")) {
				phoneNo = "84" + phoneNo.substring("0".length(), phoneNo.length());
			}

			if (avatarHashmap.containsKey(phoneNo)) {
				avatarHashmap.remove(phoneNo);
			}

			avatarHashmap.put(phoneNo, photo_id);
		}
	}

	public void dongboDanhBaXuong(final IContsCallBack contsCallBack, final List<String> numbers) {

		Bundle bundle = new Bundle();
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
							updateUserInFor(response);
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
						} else if (API.API_R022.equals(api)) {
							updateMauMoi(response, bundle.getString(DichVuStore.service_code));
						} else if (API.API_R027.equals(api)) {
							new TintucStore(MImusicService.this).save(response);
						} else if (API.API_R028.equals(api)) {
							new TintucStore(MImusicService.this).save(response);
						} else if (API.API_R010.equals(api)) {
							String strGuide_text = Conts.getString(response, HuongDanBanHangStore.guide_text);
							if (!Conts.isBlank(strGuide_text)) {
								new HuongDanBanHangStore(MImusicService.this).saveHdbh(strGuide_text);
							}
						} else if (API.API_R005.equals(api)) {
							dichVuStore.updateService_content(MImusicService.this, response, bundle);
						} else if (API.API_R024.equals(api)) {
							BangXepHang.update(MImusicService.this, response, bundle, API.API_R024);
						} else if (API.API_R025.equals(api)) {
							BangXepHang.update(MImusicService.this, response, bundle, API.API_R025);
						} else if (API.API_R015.equals(api)) {
							updateMoitheodichvu(bundle);
						} else if (API.API_R016.equals(api)) {
							updateMoitheodichvu(bundle);
						}
						return null;
					}

					protected void onPostExecute(String result) {
						if (contsCallBack != null) {
							contsCallBack.onSuscess(response);
						}
					};
				}.execute("");

			}

			@Override
			public void onError(String message) {
				if (contsCallBack != null)
					contsCallBack.onError(message);
			}

		});
	}

	private void updateKiemTraDieuKienThueBao(JSONObject response) {
	}

	private void updateMauMoi(JSONObject response, String service_code) {
		try {
			String user = accountStore.getUser();
			JSONArray array = response.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String id = Conts.getString(jsonObject, MauMoi.ID);
				String content = Conts.getString(jsonObject, MauMoi.content);

				ContentValues values = new ContentValues();
				values.put(MauMoi.ID, id);
				values.put(MauMoi.content, content);
				values.put(MauMoi.service_code, service_code);
				values.put(MauMoi.user, user);

				String where = String.format("%s = '%s' and %s = '%s'  and %s = '%s'", MauMoi.user, user, MauMoi.service_code, service_code, MauMoi.ID, id);
				Cursor cursor = getContentResolver().query(MauMoi.CONTENT_URI, null, where, null, null);

				boolean needInsert = true;
				if (cursor != null) {
					if (cursor.getCount() >= 1) {
						needInsert = false;
					}
					cursor.close();
				}

				if (needInsert) {
					getContentResolver().insert(MauMoi.CONTENT_URI, values);
				}
			}
		} catch (JSONException e) {
		}
	}

	private void updateDongBoXuongRecomment(String phone, String name) {
		if (!Conts.isBlank(phone)) {
			ContentValues contentValues = new ContentValues();
			contentValues.put(User.PHONE, phone);
			contentValues.put(User.NAME_CONTACT, name);

			contentValues.put(User.NAME_CONTACT_ENG, Conts.StringConnvert.convertVNToAlpha(name));
			String contact_id = "";
			if (avatarHashmap.containsKey(phone)) {
				contact_id = avatarHashmap.get(phone);
			}

			if (Conts.isBlank(contact_id)) {
				contact_id = "";
			}

			contentValues.put(User.contact_id, contact_id);

			if (User.haveContact(phone, this)) {
				getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '%s'", User.PHONE, phone), null);
			} else {
				getContentResolver().insert(User.CONTENT_URI, contentValues);
			}
		}
	}

	private void updateMoitheodichvu(Bundle bundle) {
		// TODO
		// customers 01674537885 or {123,123,123}
		String sdt = bundle.getString("customers");

		if (!Conts.isBlank(sdt)) {
			sdt = sdt.replace("{", "").replace("}", "");

			StringTokenizer stringTokenizer = new StringTokenizer(sdt, ",");
			while (stringTokenizer.hasMoreElements()) {
				String object = stringTokenizer.nextElement().toString();
				if (!Conts.isBlank(object)) {
					User.updateTimeMoi(MImusicService.this, object);
					
				}
			}
		}
	}

	private void updateDongBoXuong(JSONObject response) {

		try {
			String user = accountStore.getUser();
			JSONArray array = response.getJSONArray("data");
			for (int i = 0; i < array.length(); i++) {
				JSONObject jsonObject = array.getJSONObject(i);
				String phone = Conts.getString(jsonObject, "phone");
				String name = Conts.getString(jsonObject, "name");
				ContentValues contentValues = new ContentValues();
				contentValues.put(User.PHONE, phone);
				contentValues.put(User.NAME_CONTACT, name);
				contentValues.put(User.NAME_CONTACT_ENG, Conts.StringConnvert.convertVNToAlpha(name));
				// contentValues.put(User.STATUS, user.equals(phone) ? "1" :
				// "0");

				String contact_id = "";
				if (avatarHashmap.containsKey(phone)) {
					contact_id = avatarHashmap.get(phone);
				}

				if (Conts.isBlank(contact_id)) {
					contact_id = "";
				}

				contentValues.put(User.contact_id, contact_id);

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
								service_codes_name = String.format(format, services.getJSONObject(in).getString(DichVuStore.service_name));
						} else {
							service_codes = service_codes + "," + service_code;
							if (count <= 3)
								service_codes_name = service_codes_name + " | " + String.format(format, services.getJSONObject(in).getString(DichVuStore.service_name));
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
				if (User.haveContact(phone, this)) {
					getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '%s'", User.PHONE, phone), null);
				} else {
					getContentResolver().insert(User.CONTENT_URI, contentValues);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateDichVu(JSONObject response) {
		// DichVu.updateDichVu(this, response);
		// LogUtils.e("servicex", response.toString());
		dichVuStore.updateDichvu(response);
	}

	protected void updateDichVuDangKy(Bundle bundle) {

		// DichVu.updateDichVuDangKy(this, bundle);

		dichVuStore.register(bundle.getString(DichVuStore.service_code), "0");
	}

	protected void updateInFor(Bundle bundle) {

		// User.updateInFor(this, bundle);
		accountStore.updateInFor(this, bundle);
	}

	/**
	 * save infor
	 * 
	 * @param response
	 */

	private void updateUserInFor(JSONObject response) {
		accountStore.saveUserInFor(response);
		// User.updateUserInFor(this, response);
	}

	private void updateReGetToken(JSONObject response) {
		accountStore.save(response, "");

		// {"message":"Refresh token success","errorCode":0,"phone":null,"keyRefresh":"1E5571EE-CEF5-483A-50DF-20A6A1D57489","token":"57D4A6E1-B325-A2D6-3CC1-036C6730D1A3"}
		// User.updateReGetToken(this, response);

	}

	public void executeUpdateAvatar(String path, final IContsCallBack iContsCallBack) {
		Bundle bundle = new Bundle();
		bundle.putString("images", path);// path
		execute(RequestMethod.GET, API.API_R023, bundle, iContsCallBack);
	}

	// private JSONObject recommend;

	public void saveRecomend(JSONObject response) {
		if (response != null && response.has("data")) {
			try {
				String serviceCodes = "";
				String phones = "";
				String user = accountStore.getUser();

				JSONArray array = response.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					final JSONObject jsonObject = array.getJSONObject(i);
					String service_code = Conts.getString(jsonObject, Recomment.service_code);

					// create service code

					if (Conts.isBlank(serviceCodes)) {
						if (!Conts.isBlank(service_code)) {
							serviceCodes = "'" + service_code + "'";
						}
					} else {
						if (!Conts.isBlank(service_code)) {
							serviceCodes = serviceCodes + "," + "'" + service_code + "'";
						}
					}
					JSONArray contacts = jsonObject.getJSONArray("contacts");
					for (int index = 0; index < contacts.length(); index++) {
						final JSONObject cotnact = contacts.getJSONObject(index);
						String phone = Conts.getString(cotnact, Recomment.phone);
						String name = Conts.getString(cotnact, Recomment.name);

						ContentValues values = new ContentValues();
						values.put(Recomment.service_code, service_code);
						values.put(Recomment.phone, phone);
						values.put(Recomment.name, name);
						values.put(Recomment.user, user);

						if (Conts.isBlank(phones)) {
							if (!Conts.isBlank(phone)) {
								phones = "'" + phone + "'";
							}
						} else {
							if (!Conts.isBlank(phone)) {
								phones = phones + "," + "'" + phone + "'";
							}
						}

						updateDongBoXuongRecomment(phone, name);
					}
				}

				// save service code

				Recomment.saveServiceCodeList(MImusicService.this, serviceCodes);
				Recomment.savePhoneList(MImusicService.this, phones);
			} catch (JSONException e) {
			}
		}

	}

	public void executeHttps(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		execute(requestMethod, api, bundle, contsCallBack);
	}

	public void executeUpdateHttpsAvatar(String path, IContsCallBack iContsCallBack) {
		Bundle bundle = new Bundle();
		String images = Conts.encodeToString(this, path);
		bundle.putString("images", images);// path
		execute(RequestMethod.POST, API.API_R023, bundle, iContsCallBack);
	}

	private void exeDichvu(final IContsCallBack contsCallBack) {

		final boolean isLoaded = dichVuStore.getDichvu().length() > 0;
		if (isLoaded && contsCallBack != null) {
			contsCallBack.onSuscess(null);
		}

		execute(RequestMethod.GET, API.API_R004, new Bundle(), new vnp.com.mimusic.util.Conts.IContsCallBack() {
			@Override
			public void onStart() {
			}

			@Override
			public void onError(String message) {
				if (contsCallBack != null && !isLoaded) {
					contsCallBack.onError(message);
				}
			}

			@Override
			public void onSuscess(JSONObject response) {
				if (contsCallBack != null && !isLoaded) {
					contsCallBack.onSuscess(response);
				}

				if (!isLoaded) {
					callUpdateData();
				}
			}
		});

	}
}