package vnp.com.db.datastore;

import org.json.JSONObject;

import vnp.com.db.User;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;

public class AccountStore extends BaseStore {
	public static final String ID = "id";
	public static final String user = "user";

	public static final String password = "password";
	public static final String status = "status";
	public static final String name = "name";
	public static final String NAME_CONTACT = "name_contact";
	public static final String NAME_CONTACT_ENG = "name_contact_ENG";
	public static final String email = "email";
	public static final String avatar = "avatar";
	public static final String soluong = "soluong";
	public static final String doanhthu = "doanhthu";
	public static final String LISTIDUSERDAMOI = "listiduserdamoi";
	public static final String COVER = "COVER";
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
		save(user, Conts.getString(jsonObject, "phone"));
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

}