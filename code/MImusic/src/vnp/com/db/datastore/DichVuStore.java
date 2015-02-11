package vnp.com.db.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import android.content.Context;
import android.os.Bundle;

public class DichVuStore extends BaseStore {
	private String user;

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
		user = new AccountStore(getContext()).getUser();
	}

	public void register(String service_code, String service_status) {
		save(user + service_code + "cregister", service_status);
	}

	public boolean isRegister(String service_code) {
		String service_status = getString(user + service_code + "cregister");
		return "0".equals(service_status);
	}

	public JSONArray getDichvu() {
		JSONArray array = new JSONArray();
		try {
			String list = getString(user + "serviceCodeList");
			list = list.replace("[", "").replace("]", "").replace(" ", "");
			StringTokenizer stringTokenizer = new StringTokenizer(list, ",");
			while (stringTokenizer.hasMoreElements()) {
				String serviceCode = stringTokenizer.nextElement().toString();
				array.put(getDvByServiceCode(serviceCode));
			}
		} catch (Exception e) {
		}

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
			jsonObject = new JSONObject(getString(user + serviceCode));
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
				save(user + serviceCode, jsonObject.toString());

				save(user + serviceCode + ID, Conts.getString(jsonObject, DichVuStore.ID));
				save(user + serviceCode + service_name, Conts.getString(jsonObject, DichVuStore.service_name));
				save(user + serviceCode + service_name_eng, Conts.getString(jsonObject, DichVuStore.service_name_eng));
				save(user + serviceCode + service_icon, Conts.getString(jsonObject, DichVuStore.service_icon));
				save(user + serviceCode + service_code, Conts.getString(jsonObject, DichVuStore.service_code));
				save(user + serviceCode + service_content, Conts.getString(jsonObject, DichVuStore.service_content));
				save(user + serviceCode + service_price, Conts.getString(jsonObject, DichVuStore.service_price));

				register(Conts.getString(jsonObject, DichVuStore.service_code), Conts.getString(jsonObject, DichVuStore.service_status));
			}

			save(user + "serviceCodeList", serviceCodeList.toString());

			LogUtils.e("serviceCodeList.toString()", serviceCodeList.toString());
		} catch (Exception e) {
			LogUtils.e("serviceCodeList.toString()", e);
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