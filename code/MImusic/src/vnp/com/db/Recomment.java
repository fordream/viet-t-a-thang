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
				Uri _uri = ContentUris.withAppendedId(VasContact.CONTENT_URI, rowID);
				return _uri;
			}
		} else if (DICHVU_MATCHER_ID == match) {
			long rowID = db.insert(RECOMMENT_TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(VasContact.CONTENT_URI, rowID);
				return _uri;
			}
		}

		return null;
	}

	public static String getListReCommentDichvu(Context context) {

		return getServiceList(context);
	}

	public static String getListPhone(Context context) {

		return getPhoneList(context);
	}

	public static Cursor getCursorFromUser(Context context, int maxColum) {
		String selection = String.format("%s in (%s)", VasContact.PHONE, getListPhone(context));
		String limit = null;
		if (maxColum > 0) {
			// DESC
			limit = String.format("%s , %s limit %s ", VasContact.time_moi, VasContact.NAME_CONTACT, maxColum);
		} else {
			limit = VasContact.time_moi + "," + VasContact.NAME_CONTACT_ENG;
		}

		return context.getContentResolver().query(VasContact.CONTENT_URI, null, selection, null, limit);
	}

	public static Cursor getCursorFromUser(Context context, String search) {
		String limit = String.format("%s , %s ", VasContact.time_moi, VasContact.NAME_CONTACT);
		// StringBuilder selection = new StringBuilder();
		// selection.append(String.format("%s in (%s)", VasContact.PHONE,
		// getListPhone(context))).append(" and (");
		// selection.append(VasContact.NAME_CONTACT_ENG).append(" LIKE '%").append(search.toLowerCase()).append("%' ");
		// selection.append(" OR ").append(VasContact.PHONE).append(" LIKE '%").append(search.toLowerCase()).append("%' ");
		// selection.append(")");

		StringBuilder selection = new StringBuilder();
		selection.append(String.format("  %s in (%s)", VasContact.PHONE, getListPhone(context)));
		selection.append(" and (");

		selection.append(VasContact.NAME_CONTACT_ENG).append(" LIKE '%").append(search.toLowerCase()).append("%' ");
		selection.append(" OR ").append(VasContact.PHONE).append(" LIKE '%").append(search.toLowerCase()).append("%' ");
		if (search.startsWith("0")) {
			selection.append(" OR ").append(VasContact.PHONE).append(" LIKE '84").append(search.substring(1, search.length()).toLowerCase()).append("%' ");
			selection.append(" OR ").append(VasContact.PHONE).append(" LIKE '+84").append(search.substring(1, search.length()).toLowerCase()).append("%' ");
		}
		selection.append(")");
		return context.getContentResolver().query(VasContact.CONTENT_URI, null, selection.toString(), null, limit);
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
