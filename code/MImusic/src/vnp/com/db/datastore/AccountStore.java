package vnp.com.db.datastore;

import java.util.Set;

import org.json.JSONObject;

import vnp.com.db.User;
import vnp.com.mimusic.util.Conts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

public class AccountStore extends BaseStore {
	public static final String ID = "id";
	public static final String user = "user";

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

		save(keyRefresh, Conts.getString(jsonObject, keyRefresh));
		save(token, Conts.getString(jsonObject, token));
		if (!Conts.isBlank(p)) {
			save(password, p);
		}
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
		save(user + User.address, Conts.getString(response, User.address));
		save(user + User.ID, Conts.getString(response, User.ID));
		save(user + User.exchange_number, Conts.getString(response, User.exchange_number));
		save(user + User.exchange_number_month, Conts.getString(response, User.exchange_number_month));
		save(user + User.fullname, Conts.getString(response, User.fullname));
		save(user + User.nickname, Conts.getString(response, User.nickname));
		save(user + User.poundage, Conts.getString(response, User.poundage));
		save(user + User.poundage_month, Conts.getString(response, User.poundage_month));
		save(user + User.birthday, Conts.getString(response, User.birthday));
		String avatar = Conts.getString(response, User.AVATAR);
		if (!Conts.isBlank(avatar)) {
			save(user + User.AVATAR, avatar);
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

}