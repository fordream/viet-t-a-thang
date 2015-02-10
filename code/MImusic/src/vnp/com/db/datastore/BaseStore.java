package vnp.com.db.datastore;

import android.content.Context;
import android.content.SharedPreferences;

public class BaseStore {
	private SharedPreferences preferences;
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
		preferences = context.getSharedPreferences(getClass().getSimpleName(), 0);
	}

	/**
	 * 
	 * @param key
	 * @param content
	 */
	public void save(String key, String content) {
		preferences.edit().putString(key, content).commit();
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		return preferences.getString(key, "");
	}

	public String key() {
		return "id";
	}
}
