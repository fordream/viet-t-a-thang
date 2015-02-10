package vnp.com.db;

import java.util.Map;

import org.json.JSONObject;

import vnp.com.db.datastore.AccountStore;
import vnp.com.mimusic.util.Conts;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class HuongDanBanHang {
	public static final String TABLE_NAME = "HuongDanBanHang";

	public static final String _ID = "_id";
	public static final String guide_text = "guide_text";

	public static final String user = "user";

	public static final String CREATE_DB_TABLE() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(TABLE_NAME);
		builder.append("(");
		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");

		String[] colums = new String[] {//
		user, guide_text };

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

	public static final String URL = "content://" + DBProvider.PROVIDER_NAME + "/" + TABLE_NAME;
	public static final Uri CONTENT_URI = Uri.parse(URL);

	public HuongDanBanHang() {
	}

	// matcher
	public static final int USER_MATCHER = 201;
	public static final int USER_MATCHER_ID = 202;

	public static final void addUriMatcher(UriMatcher uriMatcher, String PROVIDER_NAME) {
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME, USER_MATCHER);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME + "/#", USER_MATCHER_ID);
	}

	public static final void getType(Map<Integer, String> mMap) {
		mMap.put(USER_MATCHER, "vnd.android.cursor.dir/vnp.com.mimusic");
		mMap.put(USER_MATCHER_ID, "vnd.android.cursor.item/vnp.com.mimusic");
	}

	public static final int update(int match, SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (USER_MATCHER == match) {
			return db.update(TABLE_NAME, values, selection, selectionArgs);
		} else if (USER_MATCHER_ID == match) {
			return db.update(TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static int delete(int match, SQLiteDatabase db, Uri uri, String selection, String[] selectionArgs) {
		if (USER_MATCHER == match) {
			return db.delete(TABLE_NAME, selection, selectionArgs);
		} else if (USER_MATCHER_ID == match) {
			String id = uri.getPathSegments().get(1);
			return db.delete(TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static Cursor query(int match, SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
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
			long rowID = db.insert(TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(HuongDanBanHang.CONTENT_URI, rowID);
				return _uri;
			}
		} else if (USER_MATCHER_ID == match) {
			long rowID = db.insert(TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(HuongDanBanHang.CONTENT_URI, rowID);
				return _uri;
			}
		}

		return null;
	}

	public static void update(Context context, JSONObject response) {
		String strGuide_text = Conts.getString(response, guide_text);
		if (!Conts.isBlank(strGuide_text)) {
			String strUser = new AccountStore(context).getUser();
			String selection = String.format("%s = '%s'", user, strUser);
			Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, selection, null, null);

			boolean inesrt = true;

			if (cursor != null) {
				if (cursor.getCount() >= 1) {
					inesrt = false;
				}
				cursor.close();
			}

			ContentValues values = new ContentValues();
			values.put(user, strUser);
			values.put(guide_text, strGuide_text);

			if (inesrt) {
				context.getContentResolver().insert(CONTENT_URI, values);
			} else {
				context.getContentResolver().update(CONTENT_URI, values, selection, null);
			}

		}
	}

	public static String getTextHuongDanBanHang(Context context) {
		String strGuide_text = "";
		String strUser = new AccountStore(context).getUser();
		String selection = String.format("%s = '%s'", user, strUser);
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, selection, null, null);

		if (cursor != null) {
			if (cursor.moveToNext()) {
				strGuide_text = Conts.getStringCursor(cursor, guide_text);
			}
			cursor.close();
		}

		return strGuide_text;

	}
}
