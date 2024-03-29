package vnp.com.db.datastore;

import java.util.Set;

import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.os.Bundle;

public class AccountStore extends BaseStore {
	public static final String ID = "id";
	public static final String user = "user";
	public static final String AVATAR = "avatar";
	public static final String password = "password";
	public static final String status = "status";
	public static final String email = "email";
	public static final String avatar = "avatar";
	public static final String soluong = "soluong";
	public static final String doanhthu = "doanhthu";
	public static final String LISTIDUSERDAMOI = "listiduserdamoi";
	public static final String cover = "COVER";
	public static final String birthday = "birthday";
	public static final String address = "address";
	public static final String token = "token";
	public static final String keyRefresh = "keyRefresh";
	public static final String nickname = "nickname";
	public static final String fullname = "fullname";
	public static final String exchange_number = "exchange_number";
	public static final String exchange_number_month = "exchange_number_month";
	public static final String poundage = "poundage";
	public static final String poundage_month = "poundage_month";

	public AccountStore(Context context) {
		super(context);
	}

	public void save(JSONObject jsonObject, String p) {
		if (!Conts.isBlank(Conts.getString(jsonObject, "phone"))) {
			save(user, Conts.getString(jsonObject, "phone"));
		}

		if (!Conts.isBlank(p)) {
			save(password, p);
		}
		save(keyRefresh, Conts.getString(jsonObject, keyRefresh));
		save(token, Conts.getString(jsonObject, token));

	}

	public String getToken() {
		return getString(token);
	}

	public String getUser() {
		return getString(user);
	}

	public String getPassword() {
		return getString(password);
	}

	public String getRefreshToken() {
		return getString(keyRefresh);

	}

	public void saveUserInFor(JSONObject response) {
		String user = getUser();
		save(user + address, Conts.getString(response, address));
		save(user + ID, Conts.getString(response, ID));
		save(user + exchange_number, Conts.getString(response, exchange_number));
		save(user + exchange_number_month, Conts.getString(response, exchange_number_month));
		save(user + fullname, Conts.getString(response, fullname));
		save(user + nickname, Conts.getString(response, nickname));
		save(user + poundage, Conts.getString(response, poundage));
		save(user + poundage_month, Conts.getString(response, poundage_month));
		save(user + birthday, Conts.getString(response, birthday));
		String avatar = Conts.getString(response, AVATAR);
		if (!Conts.isBlank(avatar)) {
			save(user + AVATAR, avatar);
		}
	}

	public void updateInFor(Context context, Bundle bundle) {
		String user = getUser();
		Set<String> keys = bundle.keySet();
		for (String key : keys) {
			save(user + key, bundle.getString(key));
		}
	}

	public String getStringInFor(String key) {
		return getString(getUser() + key);
	}

	public void saveCover(String path) {
		String user = getUser();
		save(user + cover, path);
	}

	public void saveAvatar(String path) {
		String user = getUser();
		save(user + avatar, path);
	}

	@Override
	public String getNameSave() {
		return "AccountStore";
	}

}