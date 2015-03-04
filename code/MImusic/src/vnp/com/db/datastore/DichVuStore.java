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
				e.printStackTrace();
			}
		}

		return array;
	}

	public JSONObject getDvByServiceCode(String serviceCode) {

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = getJsonById(getUser() + serviceCode);
		} catch (Exception exception) {
		}
		return jsonObject;
	}

	public void updateDichvu(JSONObject response) {
		try {
			JSONArray jsonArray = response.getJSONArray("data");
			List<String> serviceCodeList = new ArrayList<String>();

			save(getUser() + "listAllDcchvu", jsonArray.toString());
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

	public String getDichvuSericeCodeFirst() {
		try {
			return getDichvu().getJSONObject(0).getString(service_code);
		} catch (Exception exception) {
			return "";
		}
	}
}