package vnp.com.db.datastore;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.os.Bundle;

public class DichVuStore extends BaseStore {
	// private String user;
	private AccountStore accountStore;;

	@Override
	public String getNameSave() {
		return "DichVuStore";
	}

	public static final String ID = "id";
	public static final String service_name = "service_name";
	public static final String service_name_eng = "service_name_eng";
	public static final String service_icon = "service_icon";
	public static final String service_code = "service_code";
	public static final String service_content = "service_content";
	public static final String service_price = "service_price";
	/**
	 * 0 is dang ky 1 chua dang ky
	 */
	public static final String service_status = "service_status";

	public static String service_guide = "service_guide";

	public DichVuStore(Context context) {
		super(context);

		accountStore = new AccountStore(getContext());
	}

	private String getUser() {
		return accountStore.getUser();
	}

	public void register(String service_code, String service_status) {
		save(getUser() + service_code + "cregister", service_status);
	}

	public boolean isRegister(String service_code) {
		String service_status = getString(getUser() + service_code + "cregister");
		return "0".equals(service_status);
	}

	public JSONArray getDichvu() {
		JSONArray array = new JSONArray();
		JSONArray jsonArray = listRowId();

		for (int i = 0; i < jsonArray.length(); i++) {

			try {
				array.put(getJsonById(jsonArray.getString(i)));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// try {
		// String list = getString(user + "serviceCodeList");
		// LogUtils.e("listxxxxx", "a" + list);
		// list = list.replace("[", "").replace("]", "").replace(" ", "");
		// StringTokenizer stringTokenizer = new StringTokenizer(list, ",");
		// while (stringTokenizer.hasMoreElements()) {
		// String serviceCode = stringTokenizer.nextElement().toString();
		// array.put(getDvByServiceCode(serviceCode));
		// }
		// LogUtils.e("listxxxxx", list);
		// } catch (Exception e) {
		// }

		return array;
	}

	public JSONObject getDvByServiceCode(String serviceCode) {

		JSONObject jsonObject = new JSONObject();
		try {
			// jsonObject.put(ID, getString(user + serviceCode + ID));
			// jsonObject.put(service_name, getString(user + serviceCode +
			// service_name));
			// jsonObject.put(service_name_eng, getString(user + serviceCode +
			// service_name_eng));
			// jsonObject.put(service_icon, getString(user + serviceCode +
			// service_icon));
			// jsonObject.put(service_code, getString(user + serviceCode +
			// service_code));
			// jsonObject.put(service_content, getString(user + serviceCode +
			// service_content));
			// jsonObject.put(service_price, getString(user + serviceCode +
			// service_price));
			// jsonObject = new JSONObject(getString(user + serviceCode));

			jsonObject = getJsonById(getUser() + serviceCode);
		} catch (Exception exception) {
		}
		return jsonObject;
	}

	public void updateDichvu(JSONObject response) {
		try {
			JSONArray jsonArray = response.getJSONArray("data");

			List<String> serviceCodeList = new ArrayList<String>();
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String serviceCode = Conts.getString(jsonObject, DichVuStore.service_code);
				serviceCodeList.add(serviceCode);
				saveJsonById(getUser() + serviceCode, jsonObject);
				register(Conts.getString(jsonObject, DichVuStore.service_code), Conts.getString(jsonObject, DichVuStore.service_status));
			}

			save(getUser() + "serviceCodeList", serviceCodeList.toString());
		} catch (Exception e) {
		}
	}

	public void updateService_content(Context context, JSONObject response, Bundle bundle) {

	}

	// public static void updateService_content(Context context, JSONObject
	// response, Bundle bundle) {
	// if (bundle.containsKey(DichVu.service_code)) {
	// String strService_code = bundle.getString(DichVu.service_code);
	// ContentValues values = new ContentValues();
	// values.put(service_content, Conts.getString(response, service_content));
	//
	// context.getContentResolver().update(CONTENT_URI, values,
	// String.format("%s = '%s'", DichVu.service_code, strService_code), null);
	// }
	//
	// }
	//
}