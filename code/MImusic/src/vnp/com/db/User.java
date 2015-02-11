package vnp.com.db;

import java.util.Map;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class User {
	public static final String USER_TABLE_NAME = "users";

	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String USER = "user";

	public static final String PASSWORD = "password";
//	public static final String STATUS = "status";
	public static final String NAME = "name";
	public static final String NAME_CONTACT = "name_contact";
	public static final String NAME_CONTACT_ENG = "name_contact_ENG";
	public static final String EMAIL = "email";
	public static final String AVATAR = "avatar";
	public static final String LISTIDDVSUDUNG = "listiddvsudung";
	public static final String LISTIDTENDVSUDUNG = "LISTIDTENDVSUDUNG";
	public static final String SOLUONG = "soluong";
	public static final String DOANHTHU = "doanhthu";
	public static final String LISTIDUSERDAMOI = "listiduserdamoi";
	public static final String COVER = "COVER";

	public static final String birthday = "birthday";
	public static final String address = "address";

	public static final String TOKEN = "token";
	public static final String KEYREFRESH = "keyRefresh";

	public static final String nickname = "nickname";
	public static final String fullname = "fullname";
	public static final String exchange_number = "exchange_number";
	public static final String exchange_number_month = "exchange_number_month";
	public static final String poundage = "poundage";
	public static final String poundage_month = "poundage_month";

	public static final String contact_id = "contact_id";

	public static final String CREATE_DB_TABLE() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(USER_TABLE_NAME);
		builder.append("(");
		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");
		String[] colums = new String[] {//
		nickname, fullname, exchange_number, exchange_number_month, poundage, poundage_month, TOKEN, KEYREFRESH,

		USER, PASSWORD
//		, STATUS
		, COVER, NAME_CONTACT_ENG, ID, birthday, address, NAME, NAME_CONTACT, contact_id,//
				EMAIL, AVATAR, LISTIDDVSUDUNG, SOLUONG, DOANHTHU, LISTIDUSERDAMOI, LISTIDTENDVSUDUNG //
		};
		for (int i = 0; i < colums.length; i++) {
			if (i < colums.length - 1) {
				builder.append(colums[i]).append(" TEXT  ").append(",");
			} else {
				builder.append(colums[i]).append(" TEXT  ");
			}
		}
		builder.append(")");

		return builder.toString();

	}

	/**
	 * 
	 * 
	 */

	public static final String URL = "content://" + DBProvider.PROVIDER_NAME + "/" + USER_TABLE_NAME;
	public static final Uri CONTENT_URI = Uri.parse(URL);

	public User() {
	}

	// matcher
	public static final int USER_MATCHER = 1;
	public static final int USER_MATCHER_ID = 2;

	public static final void addUriMatcher(UriMatcher uriMatcher, String PROVIDER_NAME) {
		uriMatcher.addURI(PROVIDER_NAME, USER_TABLE_NAME, USER_MATCHER);
		uriMatcher.addURI(PROVIDER_NAME, USER_TABLE_NAME + "/#", USER_MATCHER_ID);
	}

	public static final void getType(Map<Integer, String> mMap) {
		mMap.put(USER_MATCHER, "vnd.android.cursor.dir/vnp.com.mimusic");
		mMap.put(USER_MATCHER_ID, "vnd.android.cursor.item/vnp.com.mimusic");
	}

	public static final int update(int match, SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (USER_MATCHER == match) {
			return db.update(USER_TABLE_NAME, values, selection, selectionArgs);
		} else if (USER_MATCHER_ID == match) {
			return db.update(USER_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static int delete(int match, SQLiteDatabase db, Uri uri, String selection, String[] selectionArgs) {
		if (USER_MATCHER == match) {
			return db.delete(USER_TABLE_NAME, selection, selectionArgs);
		} else if (USER_MATCHER_ID == match) {
			String id = uri.getPathSegments().get(1);
			return db.delete(USER_TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static Cursor query(int match, SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(USER_TABLE_NAME);
		if (USER_MATCHER == match) {
			return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		} else if (USER_MATCHER_ID == match) {
			qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
			return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		} else {
			return null;
		}
	}

	public static Uri insert(int match, SQLiteDatabase db, Uri uri, ContentValues values) {
		if (USER_MATCHER == match) {
			long rowID = db.insert(USER_TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
				return _uri;
			}
		} else if (USER_MATCHER_ID == match) {
			long rowID = db.insert(USER_TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
				return _uri;
			}
		}

		return null;
	}

	public static Cursor querySearch(Context mContext, String search) {
		StringBuilder selection = new StringBuilder();
		selection.append(NAME_CONTACT_ENG).append(" LIKE '%").append(search.toLowerCase()).append("%' ");
		selection.append(" OR ").append(USER).append(" LIKE '%").append(search.toLowerCase()).append("%' ");
		return mContext.getContentResolver().query(CONTENT_URI, null, selection.toString(), null, NAME_CONTACT_ENG);
	}

	public static boolean haveContact(String phone, Context context) {
		boolean hasContact = false;
		Cursor cursor = context.getContentResolver().query(User.CONTENT_URI, null, String.format("%s='%s'", User.USER, phone), null, null);

		if (cursor != null) {
			if (cursor.moveToNext()) {
				hasContact = true;
			}
			cursor.close();
		}

		return hasContact;
	}

	public static String getName(Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndex(User.fullname));

		if (name == null || name != null && name.trim().equals("")) {
			name = cursor.getString(cursor.getColumnIndex(User.NAME_CONTACT));
		}

		if (name != null && !name.trim().equals("")) {
		} else {
			name = cursor.getString(cursor.getColumnIndex(User.USER));
		}

		if (cursor != null) {
			// cursor.close();
		}

		return name;
	}

//	public static void updateReGetToken(Context mImusicService, JSONObject response) {
//		// TODO Auto-generated method stub
//		ContentValues contentValues = new ContentValues();
//		try {
//			contentValues.put(User.KEYREFRESH, response.getString(User.KEYREFRESH));
//			contentValues.put(User.TOKEN, response.getString(User.TOKEN));
//			mImusicService.getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
//		} catch (JSONException e) {
//		}
//		
//	}

	// public static String getToken(Context activity) {
	// String token = null;
	// String selection = User.STATUS + "='1'";
	// Cursor cursor = activity.getContentResolver().query(User.CONTENT_URI,
	// null, selection, null, null);
	// if (cursor != null && cursor.getCount() >= 1) {
	// if (cursor.moveToNext()) {
	// token = Conts.getStringCursor(cursor, User.TOKEN);
	// }
	// }
	//
	// if (cursor != null) {
	// cursor.close();
	// }
	// return token;
	// }

	// public static String getUser(Context context) {
	// if (context == null) {
	// return "";
	// }
	// String user = "";
	// Cursor cursor = context.getContentResolver().query(User.CONTENT_URI,
	// null, User.STATUS + "='1'", null, null);
	// if (cursor != null && cursor.getCount() >= 1) {
	// cursor.moveToNext();
	// user = cursor.getString(cursor.getColumnIndex(User.USER));
	// }
	//
	// if (cursor != null) {
	// cursor.close();
	// }
	// return user;
	// }

	// public static String getPassword(Context context) {
	// String user = "";
	// Cursor cursor = context.getContentResolver().query(User.CONTENT_URI,
	// null, User.STATUS + "='1'", null, null);
	// if (cursor != null && cursor.getCount() >= 1) {
	// cursor.moveToNext();
	// user = cursor.getString(cursor.getColumnIndex(User.PASSWORD));
	// }
	//
	// if (cursor != null) {
	// cursor.close();
	// }
	// return user;
	//
	// }

	// public static String getRefreshToken(Context context) {
	// String token = null;
	// String selection = User.STATUS + "='1'";
	// Cursor cursor = context.getContentResolver().query(User.CONTENT_URI,
	// null, selection, null, null);
	// if (cursor != null && cursor.getCount() >= 1) {
	// cursor.moveToNext();
	// token = cursor.getString(cursor.getColumnIndex(User.KEYREFRESH));
	// }
	//
	// if (cursor != null) {
	// cursor.close();
	// }
	// return token;
	// }

//	public static Cursor queryUser(Context context) {
//		return context.getContentResolver().query(User.CONTENT_URI, null, String.format("%s = '1'", User.STATUS), null, null);
//	}

//	public static void updateUserInFor(Context context, JSONObject response) {
//		ContentValues contentValues = new ContentValues();
//		contentValues.put(User.address, Conts.getString(response, User.address));
//		contentValues.put(User.ID, Conts.getString(response, User.ID));
//		contentValues.put(User.exchange_number, Conts.getString(response, User.exchange_number));
//		contentValues.put(User.exchange_number_month, Conts.getString(response, User.exchange_number_month));
//		contentValues.put(User.fullname, Conts.getString(response, User.fullname));
//		contentValues.put(User.nickname, Conts.getString(response, User.nickname));
//		contentValues.put(User.poundage, Conts.getString(response, User.poundage));
//		contentValues.put(User.poundage_month, Conts.getString(response, User.poundage_month));
//		contentValues.put(User.birthday, Conts.getString(response, User.birthday));
//		String avatar = Conts.getString(response, User.AVATAR);
//		// LogUtils.e("avatar", avatar + "");
//		if (!Conts.isBlank(avatar)) {
//			contentValues.put(User.AVATAR, avatar);
//		}
//
//		context.getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
//	}
//
//	public static void updateInForLogin(Context context, JSONObject jsonObject, String p) {
//		String token = Conts.getString(jsonObject, "token");
//		String keyRefresh = Conts.getString(jsonObject, "keyRefresh");
//		String phone_number = Conts.getString(jsonObject, "phone");
//		ContentValues values = new ContentValues();
//		values.put(User.USER, phone_number);
//		if (!Conts.isBlank(p)) {
//			values.put(User.PASSWORD, p);
//		}
//		values.put(User.AVATAR, Conts.getString(jsonObject, User.AVATAR));
//		values.put(User.TOKEN, token);
//		values.put(User.KEYREFRESH, keyRefresh);
//		values.put(User.STATUS, "1");
//		String selection = String.format("%s='%s'", User.USER, phone_number);
//		Cursor cursor = context.getContentResolver().query(User.CONTENT_URI, null, selection, null, null);
//
//		boolean isUpdate = cursor != null && cursor.getCount() >= 1;
//		cursor.close();
//
//		if (isUpdate) {
//			context.getContentResolver().update(User.CONTENT_URI, values, selection, null);
//		} else {
//			context.getContentResolver().insert(User.CONTENT_URI, values);
//		}
//	}
//
//	public static void updateInFor(Context context, Bundle bundle) {
//		ContentValues contentValues = new ContentValues();
//		Set<String> keys = bundle.keySet();
//		for (String key : keys) {
//			contentValues.put(key, bundle.getString(key));
//		}
//
//		context.getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s=='1'", User.STATUS), null);
//	}
}
