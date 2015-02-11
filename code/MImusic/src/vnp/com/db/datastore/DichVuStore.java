package vnp.com.db.datastore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import android.content.Context;

public class DichVuStore extends BaseStore {
	private String user;

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
		save(user + service_code, service_status);
	}

	public boolean isRegister(String service_code) {
		String service_status = getString(user + service_code);
		return "0".equals(service_status);
	}

	public JSONArray getDichvu() {
		try {
			return new JSONArray(getString(user + "getDichvuALL"));
		} catch (JSONException e) {
		}

		return new JSONArray();
	}

	public JSONObject getDvByServiceCode(String serviceCode) {
		try {
			return new JSONObject(getString(user + serviceCode));
		} catch (JSONException e) {
		}

		return new JSONObject();
	}

	public void updateDichvu(JSONObject response) {
		try {
			JSONArray jsonArray = response.getJSONArray("data");
			save(user + "getDichvuALL", jsonArray.toString());
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String _service_code = Conts.getString(jsonObject, DichVuStore.service_code);
				save(user + _service_code, jsonObject.toString());
				register(Conts.getString(jsonObject, DichVuStore.service_code), Conts.getString(jsonObject, DichVuStore.service_status));
			}
		} catch (Exception e) {
		}
	}
}