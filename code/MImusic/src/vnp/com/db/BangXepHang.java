package vnp.com.db;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.mimusic.util.Conts;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

public class BangXepHang {
	public static final String TABLE_NAME = "bangxephang";

	public static final String _ID = "_id";
	public static final String ID = "id";
	public static final String USER = "user";
	public static final String position = "position";

	public static final String type = "type";
	public static final String typeDOANHTHU = "1";
	public static final String typeSOLUONG = "2";

	public static final String commission = "commission";
	public static final String quantity = "quantity";
	public static final String nickname = "nickname";
	public static final String avatar = "avatar";
	public static final String quantity_in_duration = "quantity_in_duration";
	public static final String commission_in_duration = "commission_in_duration";

	public static final String CREATE_DB_TABLE() {
		StringBuilder builder = new StringBuilder();
		builder.append("CREATE TABLE ").append(TABLE_NAME);

		builder.append("(");
		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");
		String[] colums = new String[] {//
		USER, ID, position, nickname, avatar, type, quantity, commission, quantity_in_duration, commission_in_duration //
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

	public BangXepHang() {
	}

	// matcher
	public static final int DICHVU_MATCHER = 7;
	public static final int DICHVU_MATCHER_ID = 8;

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

	public static void update(Context context, JSONObject response, Bundle bundle, String api) {
		// API.API_R024,API.API_R025
		String type = bundle.getString(BangXepHang.type);
		String user = bundle.getString(BangXepHang.USER);

		if (API.API_R024.equals(api)) {
			try {
				JSONArray jsonArray = response.getJSONArray("data");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject object = jsonArray.getJSONObject(i);
					ContentValues values = new ContentValues();
					values.put(BangXepHang.USER, user);
					values.put(BangXepHang.type, type);
					values.put(BangXepHang.ID, Conts.getString(object, BangXepHang.ID));
					values.put(BangXepHang.avatar, Conts.getString(object, BangXepHang.avatar));
					values.put(BangXepHang.commission, Conts.getString(object, BangXepHang.commission));
					values.put(BangXepHang.nickname, Conts.getString(object, BangXepHang.nickname));
					values.put(BangXepHang.quantity, Conts.getString(object, BangXepHang.quantity));

					boolean needInsert = true;
					String selection = String.format("%s ='%s' and %s = '%s' and %s = '%s'", BangXepHang.USER, user, BangXepHang.type, type, BangXepHang.ID, Conts.getString(object, BangXepHang.ID));
					Cursor cursor = getBangXepHang(context, type, Conts.getString(object, BangXepHang.ID));

					if (cursor != null) {
						if (cursor.getCount() >= 1) {
							needInsert = false;
						}
						cursor.close();
					}

					if (needInsert) {
						context.getContentResolver().insert(CONTENT_URI, values);
					} else {
						context.getContentResolver().update(CONTENT_URI, values, selection, null);
					}
				}
			} catch (Exception exception) {
			}
		} else if (API.API_R025.equals(api)) {

		}
	}

	public static Cursor getBangXepHang(Context context, String type, String id) {
		String selection = String.format("%s ='%s' and %s = '%s' and %s = '%s'", BangXepHang.USER, Conts.getUser(context), BangXepHang.type, type, BangXepHang.ID, id);
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, selection, null, null);
		return cursor;
	}

	public static Cursor getBangXepHang(Context context, String type) {
		String selection = String.format("%s ='%s' and %s = '%s'", BangXepHang.USER, Conts.getUser(context), BangXepHang.type, type);
		Cursor cursor = context.getContentResolver().query(CONTENT_URI, null, selection, null, typeSOLUONG.equals(type) ? quantity : commission);
		return cursor;
	}
}
