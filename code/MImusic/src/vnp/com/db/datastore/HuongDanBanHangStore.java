package vnp.com.db.datastore;

import android.content.Context;

public class HuongDanBanHangStore extends BaseStore {
	@Override
	public String getNameSave() {
		return "HuongDanBanHangStore";
	}

	public HuongDanBanHangStore(Context context) {
		super(context);
	}

	public static final String guide_text = "guide_text";

	public void saveHdbh(String hdbh) {
		String user = new AccountStore(getContext()).getUser();
		save(user + guide_text, hdbh);
	}

	public String getHdbh() {
		String user = new AccountStore(getContext()).getUser();
		return getString(user + guide_text);
	}
}