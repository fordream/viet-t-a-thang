//package vnp.com.db;
//
//import java.util.Map;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import vnp.com.mimusic.util.Conts;
//import android.content.ContentValues;
//import android.content.Context;
//import android.content.UriMatcher;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteQueryBuilder;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//
//public class DichVu {
//	public static final String DICHVU_TABLE_NAME = "dichvus";
//
//	public static final String _ID = "_id";
//	public static final String ID = "id";
//	public static final String service_name = "service_name";
//	public static final String service_name_eng = "service_name_eng";
//	public static final String service_icon = "service_icon";
//	public static final String service_code = "service_code";
//	public static final String service_content = "service_content";
//	public static final String service_price = "service_price";
//	/**
//	 * 0 is dang ky 1 chua dang ky
//	 */
//	public static final String service_status = "service_status";
//
//	public static String service_guide = "service_guide";
//
//	public static final String CREATE_DB_TABLE() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("CREATE TABLE ").append(DICHVU_TABLE_NAME);
//		builder.append("(");
//		builder.append(_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT").append(",");
//		String[] colums = new String[] {//
//		ID, service_name, service_icon, service_code, service_content, service_price, service_guide, service_status, service_name_eng //
//		};//
//
//		for (int i = 0; i < colums.length; i++) {
//			if (i < colums.length - 1) {
//				builder.append(colums[i]).append(" TEXT  ").append(",");
//			} else {
//				builder.append(colums[i]).append(" TEXT  ");
//			}
//		}
//		builder.append(")");
//
//		return builder.toString();
//
//	}
//
//	/**
//	 * 
//	 * 
//	 */
//
//	public static final String CONTENT_URI_STR = "content://" + DBProvider.PROVIDER_NAME + "/" + DICHVU_TABLE_NAME;
//	public static final Uri CONTENT_URI = Uri.parse(CONTENT_URI_STR);
//
//	public DichVu() {
//	}
//
//	// matcher
//	public static final int DICHVU_MATCHER = 3;
//	public static final int DICHVU_MATCHER_ID = 4;
//
//	public static final void addUriMatcher(UriMatcher uriMatcher, String PROVIDER_NAME) {
//		uriMatcher.addURI(PROVIDER_NAME, DICHVU_TABLE_NAME, DICHVU_MATCHER);
//		uriMatcher.addURI(PROVIDER_NAME, DICHVU_TABLE_NAME + "/#", DICHVU_MATCHER_ID);
//	}
//
//	public static final void getType(Map<Integer, String> mMap) {
//		mMap.put(DICHVU_MATCHER, "vnd.android.cursor.dir/vnp.com.mimusic");
//		mMap.put(DICHVU_MATCHER_ID, "vnd.android.cursor.item/vnp.com.mimusic");
//	}
//
//	public static final int update(int match, SQLiteDatabase db, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
//		if (DICHVU_MATCHER == match) {
//			return db.update(DICHVU_TABLE_NAME, values, selection, selectionArgs);
//		} else if (DICHVU_MATCHER_ID == match) {
//			return db.update(DICHVU_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
//		} else {
//			return -2;
//		}
//	}
//
//	public static int delete(int match, SQLiteDatabase db, Uri uri, String selection, String[] selectionArgs) {
//		if (DICHVU_MATCHER == match) {
//			return db.delete(DICHVU_TABLE_NAME, selection, selectionArgs);
//		} else if (DICHVU_MATCHER_ID == match) {
//			String id = uri.getPathSegments().get(1);
//			return db.delete(DICHVU_TABLE_NAME, _ID + " = " + id + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
//		} else {
//			return -2;
//		}
//	}
//
//	public static Cursor query(int match, SQLiteDatabase db, Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
//		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
//		qb.setTables(DICHVU_TABLE_NAME);
//		if (DICHVU_MATCHER == match) {
//			return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
//		} else if (DICHVU_MATCHER_ID == match) {
//			qb.appendWhere(_ID + "=" + uri.getPathSegments().get(1));
//			return qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
//		} else {
//			return null;
//		}
//	}
//
//	public static Uri insert(int match, SQLiteDatabase db, Uri uri, ContentValues values) {
//		if (DICHVU_MATCHER == match) {
//			long rowID = db.insert(DICHVU_TABLE_NAME, "", values);
//			if (rowID > 0) {
//				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
//				return _uri;
//			}
//		} else if (DICHVU_MATCHER_ID == match) {
//			long rowID = db.insert(DICHVU_TABLE_NAME, "", values);
//			if (rowID > 0) {
//				Uri _uri = ContentUris.withAppendedId(User.CONTENT_URI, rowID);
//				return _uri;
//			}
//		}
//
//		return null;
//	}
//
//	public static void updateDichvuRecomment(JSONObject jsonObject, Context context) {
//		Cursor cursor = context.getContentResolver().query(DichVu.CONTENT_URI, null, String.format("%s ='%s'", DichVu.service_code, Conts.getString(jsonObject, DichVu.service_code)), null, null);
//		boolean isNew = true;
//
//		if (cursor != null && cursor.getCount() >= 1) {
//			isNew = false;
//		}
//
//		if (cursor != null) {
//			cursor.close();
//		}
//
//		ContentValues contentValues = new ContentValues();
//		// contentValues.put(DichVu.ID, Conts.getString(jsonObject, DichVu.ID));
//		contentValues.put(DichVu.service_name, Conts.getString(jsonObject, DichVu.service_name));
//		contentValues.put(DichVu.service_icon, Conts.getString(jsonObject, DichVu.service_icon));
//		contentValues.put(DichVu.service_code, Conts.getString(jsonObject, DichVu.service_code));
//		// contentValues.put(DichVu.ID, Conts.getString(jsonObject, DichVu.ID));
//		// TODO
//
//		if (isNew) {
//			context.getContentResolver().insert(DichVu.CONTENT_URI, contentValues);
//		} else {
//			context.getContentResolver().update(DichVu.CONTENT_URI, contentValues, String.format("%s ='%s'", DichVu.service_code, Conts.getString(jsonObject, DichVu.service_code)), null);
//		}
//
//	}
//
//	public static Cursor getCursorFromUser(Context context, int maxColum) {
//		String limit = null;
//
//		if (maxColum > 0) {
//			limit = String.format("%s limit %s ", DichVu.service_name, maxColum);
//		}
//
//		return context.getContentResolver().query(DichVu.CONTENT_URI, null, null, null, limit);
//	}
//
//	public static void updateService_content(Context context, JSONObject response, Bundle bundle) {
//		if (bundle.containsKey(DichVu.service_code)) {
//			String strService_code = bundle.getString(DichVu.service_code);
//			ContentValues values = new ContentValues();
//			values.put(service_content, Conts.getString(response, service_content));
//
//			context.getContentResolver().update(CONTENT_URI, values, String.format("%s = '%s'", DichVu.service_code, strService_code), null);
//		}
//
//	}
//
//	public static void updateDichVuDangKy(Context context, Bundle bundle) {
//		String cs = bundle.getString(DichVu.service_code);
//		ContentValues contentValues = new ContentValues();
//		contentValues.put(DichVu.service_status, "0");
//		context.getContentResolver().update(DichVu.CONTENT_URI, contentValues, String.format("%s=='%s'", DichVu.service_code, cs), null);
//
//	}
//
//	public static void updateDichVu(Context context, JSONObject response) {
//		try {
//			JSONArray jsonArray = response.getJSONArray("data");
//			for (int i = 0; i < jsonArray.length(); i++) {
//				JSONObject jsonObject = jsonArray.getJSONObject(i);
//				ContentValues contentValues = new ContentValues();
//				contentValues.put(DichVu.ID, Conts.getString(jsonObject, DichVu.ID));
//				contentValues.put(DichVu.service_name, Conts.getString(jsonObject, DichVu.service_name));
//				contentValues.put(DichVu.service_name_eng, Conts.StringConnvert.convertVNToAlpha(Conts.getString(jsonObject, DichVu.service_name)));
//				contentValues.put(DichVu.service_code, Conts.getString(jsonObject, DichVu.service_code));
//				contentValues.put(DichVu.service_icon, Conts.getString(jsonObject, DichVu.service_icon));
//				contentValues.put(DichVu.service_content, Conts.getString(jsonObject, DichVu.service_content));
//				contentValues.put(DichVu.service_price, Conts.getString(jsonObject, DichVu.service_price));
//				contentValues.put(DichVu.service_status, Conts.getString(jsonObject, DichVu.service_status));
//				String service_guide = Conts.getString(jsonObject, DichVu.service_guide);
//
//				if (!Conts.isBlank(service_guide)) {
//					contentValues.put(DichVu.service_guide, service_guide);
//				}
//
//				String selection = String.format("%s='%s'", DichVu.service_code, jsonObject.getString(DichVu.service_code));
//				Cursor cursor = context.getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);
//
//				boolean isUpdate = cursor != null && cursor.getCount() >= 1;
//
//				if (cursor != null) {
//					cursor.close();
//				}
//
//				if (isUpdate) {
//					context.getContentResolver().update(DichVu.CONTENT_URI, contentValues, selection, null);
//				} else {
//					context.getContentResolver().insert(DichVu.CONTENT_URI, contentValues);
//				}
//			}
//		} catch (JSONException e) {
//		}
//	}
//}
