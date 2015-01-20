package vnp.com.db;

import java.util.Map;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;

public class User {
	public static final String USER_TABLE_NAME = "users";

	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String USER = "user";

	public static final String PASSWORD = "password";
	public static final String STATUS = "status";
	public static final String NAME = "name";
	public static final String NAME_CONTACT = "name_contact";
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

	public static final String photo_id = ContactsContract.Contacts.PHOTO_ID;

	public static final String CREATE_DB_TABLE() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(USER_TABLE_NAME);
		builder.append("(");
		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");
		String[] colums = new String[] {//
		nickname, fullname, exchange_number, exchange_number_month, poundage, poundage_month, TOKEN, KEYREFRESH,

		USER, PASSWORD, STATUS, COVER, ID, birthday, address, NAME, NAME_CONTACT, photo_id,//
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

}
