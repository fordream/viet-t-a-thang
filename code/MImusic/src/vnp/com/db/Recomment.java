package vnp.com.db;

import java.util.Map;

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

public class Recomment {
	public static final String RECOMMENT_TABLE_NAME = "recomment";
	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String user = "user";
	public static final String phone = "phone";
	public static final String name = "name";
	public static final String service_code = "service_code";

	// type 0 is recomment phone
	// type 1 recomment service
	public static final String type = "type";

	public static final String CREATE_DB_TABLE() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(RECOMMENT_TABLE_NAME);

		builder.append("(");
		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");
		String[] colums = new String[] {//
		user, ID, service_code, phone, name, type };//

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

	public static final String CONTENT_URI_STR = "content://" + DBProvider.PROVIDER_NAME + "/" + RECOMMENT_TABLE_NAME;
	public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STR);

	public Recomment() {
	}

	// matcher
	public static final int DICHVU_MATCHER = 5;
	public static final int DICHVU_MATCHER_ID = 6;

	public static final void addUriMatcher(UriMatcher uriMatcher, String PROVIDER_NAME) {
		uriMatcher.addURI(PROVIDER_NAME, RECOMMENT_TABLE_NAME, DICHVU_MATCHER);
		uriMatcher.addURI(PROVIDER_NAME, RECOMMENT_TABLE_NAME + "/#", DICHVU_MATCHER_ID);
	}

	public static final void getType(Map<Integer, String> mMap) {
		mMap.put(DICHVU_MATCHER, "vnd.android.cursor.dir/vnp.com.mimusic");
		mMap.put(DICHVU_MATCHER_ID, "vnd.android.cursor.item/vnp.com.mimusic");
	}

	public static final int update(int match, SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (DICHVU_MATCHER == match) {
			return db.update(RECOMMENT_TABLE_NAME, values, selection, selectionArgs);
		} else if (DICHVU_MATCHER_ID == match) {
			return db.update(RECOMMENT_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static int delete(int match, SQLiteDatabase db, Uri uri, String selection, String[] selectionArgs) {
		if (DICHVU_MATCHER == match) {
			return db.delete(RECOMMENT_TABLE_NAME, selection, selectionArgs);
		} else if (DICHVU_MATCHER_ID == match) {
			String id = uri.getPathSegments().get(1);
			return db.delete(RECOMMENT_TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static Cursor query(int match, SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(RECOMMENT_TABLE_NAME);
		if (DICHVU_MATCHER == match) {
			return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		} else if (DICHVU_MATCHER_ID == match) {
			qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
			return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		} else {
			return null;
		}
	}

	public static Uri insert(int match, SQLiteDatabase db, Uri uri, ContentValues values) {
		if (DICHVU_MATCHER == match) {
			long rowID = db.insert(RECOMMENT_TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
				return _uri;
			}
		} else if (DICHVU_MATCHER_ID == match) {
			long rowID = db.insert(RECOMMENT_TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
				return _uri;
			}
		}

		return null;
	}

	public static String getListReCommentDichvu(Context context) {
//		String selection = String.format("%s = '%s') GROUP BY ( %s", Recomment.user, Conts.getUser(context), Recomment.service_code);
//		if (context == null) {
//			return "";
//		}
		// Cursor cursor =
		// context.getContentResolver().query(Recomment.CONTENT_URI, null,
		// selection, null, String.format("%s", Recomment.service_code));
		// String dichvu = "";
		// if (cursor != null) {
		// while (cursor.moveToNext()) {
		// String service_code = Conts.getStringCursor(cursor,
		// Recomment.service_code);
		//
		// if (!Conts.isBlank(service_code)) {
		// if (Conts.isBlank(dichvu)) {
		// dichvu = String.format("'%s'", service_code);
		// } else {
		// dichvu = String.format("%s,'%s'", dichvu, service_code);
		// }
		// }
		// }
		//
		// cursor.close();
		// }
		//
		// return String.format("%s", dichvu);

		return getServiceList(context);
	}

	public static String getListPhone(Context context) {
		// String selection = String.format("%s = '%s') GROUP BY ( %s",
		// Recomment.user, Conts.getUser(context), Recomment.phone);
		// Cursor cursor =
		// context.getContentResolver().query(Recomment.CONTENT_URI, null,
		// selection, null, String.format("%s", Recomment.service_code));
		// String dichvu = "";
		// if (cursor != null) {
		// while (cursor.moveToNext()) {
		// String service_code = Conts.getStringCursor(cursor, Recomment.phone);
		//
		// if (!Conts.isBlank(service_code)) {
		// if (Conts.isBlank(dichvu)) {
		// dichvu = String.format("'%s'", service_code);
		// } else {
		// dichvu = String.format("%s,'%s'", dichvu, service_code);
		// }
		// }
		// }
		//
		// cursor.close();
		// }
		//
		// return String.format("(%s)", dichvu);

		return getPhoneList(context);
	}

	public static Cursor getCursorFromDichvu(Context context, int maxColum) {
		String selection = String.format("%s in (%s)", DichVu.service_code, getListReCommentDichvu(context));
		String limit = null;
		if (maxColum > 0) {
			limit = String.format("%s limit %s ", DichVu.service_name, maxColum);
		}

		return context.getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, limit);

	}

	public static Cursor getCursorFromUser(Context context, int maxColum) {
		String selection = String.format("%s in (%s)", User.USER, getListPhone(context));
		String limit = null;
		if (maxColum > 0) {
			limit = String.format("%s limit %s ", User.NAME_CONTACT, maxColum);
		} else {
			limit = User.NAME_CONTACT;
		}

		return context.getContentResolver().query(User.CONTENT_URI, null, selection, null, limit);
	}

	public static void saveServiceCodeList(Context context, String serviceCodes) {
		DataStore.getInstance().init(context);
		DataStore.getInstance().save("saveServiceCodeList", serviceCodes);
	}

	public static String getServiceList(Context context) {
		DataStore.getInstance().init(context);
		return DataStore.getInstance().get("saveServiceCodeList", "");
	}

	public static void savePhoneList(Context mImusicService, String phones) {
		DataStore.getInstance().init(mImusicService);
		DataStore.getInstance().save("savePhoneList", phones);
	}

	public static String getPhoneList(Context context) {
		DataStore.getInstance().init(context);
		return DataStore.getInstance().get("savePhoneList", "");
	}

}
