package vnp.com.db.datastore;

import android.content.Context;

public class TintucStore extends BaseStore {

	public TintucStore(Context context) {
		super(context);
	}

	public void save(String content) {
		save("save", content);
	}

	public String get() {
		return getString("save");
	}
}