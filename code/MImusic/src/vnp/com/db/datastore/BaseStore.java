package vnp.com.db.datastore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.content.SharedPreferences;

public abstract class BaseStore {

	private Context context;

	/**
	 * 
	 * @return
	 */
	public Context getContext() {
		return context;
	}

	/**
	 * 
	 * @param context
	 */
	public BaseStore(Context context) {
		this.context = context;

	}

	public abstract String getNameSave();

	/**
	 * 
	 * @param key
	 * @param content
	 */
	public void save(String key, String content) {
		preferences().edit().putString(key, content).commit();
	}

	private SharedPreferences preferences() {
		return context.getSharedPreferences(getNameSave(), 0);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return preferences().getString(key, "");
	}

	public String key() {
		return "id";
	}

	public void saveJsonById(String id, JSONObject content) {
		if (!Conts.isBlank(id)) {
			save(id, content.toString());
			saveIdTOList(id);
		}
	}

	public JSONObject getJsonById(String id) {

		String content = getString(id);
		JSONObject object = new JSONObject();
		try {
			object = new JSONObject(content);
		} catch (Exception e) {
		}

		return object;

	}

	private void saveIdTOList(String id) {
		JSONArray array = listRowId();
		boolean needAddd = true;
		for (int i = 0; i < array.length(); i++) {
			try {
				if (id.equals(array.get(i).toString())) {
					needAddd = false;
					break;
				}
			} catch (JSONException e) {
			}

		}

		if (needAddd) {
			array.put(id);
		}

		save("array", array.toString());
	}

	public JSONArray listRowId() {
		String contentId = getString("array");
		JSONArray array = new JSONArray();
		try {
			array = new JSONArray(contentId);
		} catch (Exception e) {
		}

		return array;
	}
}
