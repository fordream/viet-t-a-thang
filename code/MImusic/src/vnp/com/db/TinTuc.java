package vnp.com.db;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

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

public class TinTuc {
	public static final String TABLE_NAME = "tintuc";

	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String title = "title";
	public static final String header = "header";
	public static final String images = "images";
	public static final String public_time = "public_time";
	public static final String type = "type";

	// loaded = 1 is loaded
	public static final String loaded = "loaded";

	/**
	 * 0 is dang ky 1 chua dang ky
	 */

	public static final String CREATE_DB_TABLE() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(TABLE_NAME);
		builder.append("(");
		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");
		String[] colums = new String[] {//
		ID, title, images, public_time, type, loaded, header //
		};//

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

	public static final String CONTENT_URI_STR = "content://" + DBProvider.PROVIDER_NAME + "/" + TABLE_NAME;
	public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STR);

	public TinTuc() {
	}

	// matcher
	public static final int DICHVU_MATCHER = 101;
	public static final int DICHVU_MATCHER_ID = 102;

	public static final void addUriMatcher(UriMatcher uriMatcher, String PROVIDER_NAME) {
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME, DICHVU_MATCHER);
		uriMatcher.addURI(PROVIDER_NAME, TABLE_NAME + "/#", DICHVU_MATCHER_ID);
	}

	public static final void getType(Map<Integer, String> mMap) {
		mMap.put(DICHVU_MATCHER, "vnd.android.cursor.dir/vnp.com.mimusic");
		mMap.put(DICHVU_MATCHER_ID, "vnd.android.cursor.item/vnp.com.mimusic");
	}

	public static final int update(int match, SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		if (DICHVU_MATCHER == match) {
			return db.update(TABLE_NAME, values, selection, selectionArgs);
		} else if (DICHVU_MATCHER_ID == match) {
			return db.update(TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static int delete(int match, SQLiteDatabase db, Uri uri, String selection, String[] selectionArgs) {
		if (DICHVU_MATCHER == match) {
			return db.delete(TABLE_NAME, selection, selectionArgs);
		} else if (DICHVU_MATCHER_ID == match) {
			String id = uri.getPathSegments().get(1);
			return db.delete(TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
		} else {
			return -2;
		}
	}

	public static Cursor query(int match, SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(TABLE_NAME);
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
			long rowID = db.insert(TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
				return _uri;
			}
		} else if (DICHVU_MATCHER_ID == match) {
			long rowID = db.insert(TABLE_NAME, "", values);
			if (rowID > 0) {
				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
				return _uri;
			}
		}

		return null;
	}

	public static void updateTintuc(Context context, JSONObject response) {
		if (response != null && response.has("data")) {
			try {
				JSONArray array = response.getJSONArray("data");
				for (int i = 0; i < array.length(); i++) {
					updateTintuc(context, array.getJSONObject(i));
				}
			} catch (Exception e) {
			}
		} else if (response != null && response.has(ID)) {
			String id = Conts.getString(response, ID);
			if (!Conts.isBlank(id)) {
				ContentValues values = new ContentValues();
				values.put(ID, id);
				values.put(header, Conts.getString(response, header));
				values.put(title, Conts.getString(response, title));
				values.put(images, Conts.getString(response, images));
				values.put(public_time, Conts.getString(response, public_time));
				values.put(type, Conts.getString(response, type));

				boolean needInsert = true;
				Cursor cursor = queryFromId(context, id);

				if (cursor != null) {
					if (cursor.getCount() >= 1) {
						needInsert = false;
					}

					cursor.close();
				}
				if (needInsert) {
					context.getContentResolver().insert(CONTENT_URI, values);
				} else {
					context.getContentResolver().update(CONTENT_URI, values, String.format("%s = '%s'", ID, id), null);
				}
			}
		}
	}

	public static Cursor queryFromId(Context context, String id) {
		if (Conts.isBlank(id)) {
			return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
		}
		return context.getContentResolver().query(CONTENT_URI, null, String.format("%s = '%s'", ID, id), null, null);
	}
}
