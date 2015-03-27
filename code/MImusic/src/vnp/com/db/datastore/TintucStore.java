package vnp.com.db.datastore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import android.content.Context;

public class TintucStore extends BaseStore {

	@Override
	public String getNameSave() {
		return "TintucStore";
	}

	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String title = "title";
	public static final String header = "header";
	public static final String images = "images";
	public static final String public_time = "public_time";
	public static final String content = "content";
	public static final String type = "type";

	/**
	 * loaded = 1 is loaded
	 */
	// public static final String loaded = "loaded";

	public TintucStore(Context context) {
		super(context);
	}

	public void setLoaded(String id) {
		save("id", "1");
	}

	public boolean isLoaded(String id) {
		return "1".equals(getString("id"));
	}

	public void save(JSONObject response) {
		if (response != null && response.has("data")) {
			try {
				JSONArray data = response.getJSONArray("data");
				for (int i = 0; i < data.length(); i++) {
					JSONObject object = data.getJSONObject(i);
					String id = Conts.getString(object, ID);
					saveJsonById(id, object);
				}
			} catch (JSONException e) {
			}
		} else if (response != null && response.has(ID)) {
			String id = Conts.getString(response, ID);
			saveJsonById(id, response);
		}
	}
}