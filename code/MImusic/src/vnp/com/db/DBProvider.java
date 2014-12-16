package vnp.com.db;

import java.util.HashMap;
import java.util.Map;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class DBProvider extends ContentProvider {
	public static final String PROVIDER_NAME = "vnp.com.mimusic.db.DBProvider";

	public DBProvider() {
		super();
	}

	private UriMatcher uriMatcher;

	private SQLiteDatabase db;

	@Override
	public boolean onCreate() {
		db = new DBDatabaseHelper(getContext()).getWritableDatabase();

		if (uriMatcher == null) {
			uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
			User.addUriMatcher(uriMatcher, PROVIDER_NAME);
			DichVu.addUriMatcher(uriMatcher, PROVIDER_NAME);
			Recomment.addUriMatcher(uriMatcher, PROVIDER_NAME);
			BangXepHangChiTiet.addUriMatcher(uriMatcher, PROVIDER_NAME);
		}

		return (db == null) ? false : true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		int match = uriMatcher.match(uri);
		Uri _uri = User.insert(match, db, uri, values);

		if (_uri == null) {
			_uri = DichVu.insert(match, db, _uri, values);
		}

		if (_uri == null) {
			_uri = Recomment.insert(match, db, _uri, values);
		}

		if (_uri == null) {
			_uri = BangXepHangChiTiet.insert(match, db, _uri, values);
		}
		if (_uri != null) {
			getContext().getContentResolver().notifyChange(_uri, null);
		}
		return _uri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		int match = uriMatcher.match(uri);
		Cursor c = User.query(match, db, uri, projection, selection, selectionArgs, sortOrder);

		// cursor == null --> request othr
		if (c == null) {
			c = DichVu.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}

		if (c == null) {
			c = Recomment.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}

		if (c == null) {
			c = BangXepHangChiTiet.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}

		if (c != null) {
			c.setNotificationUri(getContext().getContentResolver(), uri);
		}

		if (c == null || match == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		return c;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;
		int match = uriMatcher.match(uri);

		count = User.delete(match, db, uri, selection, selectionArgs);
		// count == -2 delete other
		if (count == -2) {
			count = DichVu.delete(match, db, uri, selection, selectionArgs);
		}

		if (count == -2) {
			count = Recomment.delete(match, db, uri, selection, selectionArgs);
		}

		if (count == -2) {
			count = BangXepHangChiTiet.delete(match, db, uri, selection, selectionArgs);
		}
		//
		if (count == -2 || match == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;

		int match = uriMatcher.match(uri);

		count = User.update(match, db, uri, values, selection, selectionArgs);
		// count == -2 update other
		if (count == -2) {
			count = DichVu.update(match, db, uri, values, selection, selectionArgs);
		}

		if (count == -2) {
			count = Recomment.update(match, db, uri, values, selection, selectionArgs);
		}

		if (count == -2) {
			count = BangXepHangChiTiet.update(match, db, uri, values, selection, selectionArgs);
		}

		if (count == -2 || match == UriMatcher.NO_MATCH) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public String getType(Uri uri) {
		Map<Integer, String> mMap = new HashMap<Integer, String>();
		User.getType(mMap);
		DichVu.getType(mMap);
		Recomment.getType(mMap);
		BangXepHangChiTiet.getType(mMap);
		String type = mMap.get(uriMatcher.match(uri));

		if (type == null) {
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		} else {
			return type;
		}
	}
}