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
			Account.addUriMatcher(uriMatcher, PROVIDER_NAME);
			HuongDanBanHang.addUriMatcher(uriMatcher, PROVIDER_NAME);
//			TinTuc.addUriMatcher(uriMatcher, PROVIDER_NAME);
			DichVu.addUriMatcher(uriMatcher, PROVIDER_NAME);
			Recomment.addUriMatcher(uriMatcher, PROVIDER_NAME);
			BangXepHang.addUriMatcher(uriMatcher, PROVIDER_NAME);
			MauMoi.addUriMatcher(uriMatcher, PROVIDER_NAME);
		}

		return (db == null) ? false : true;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// openDB();
		int match = uriMatcher.match(uri);
		Uri _uri = User.insert(match, db, uri, values);
		if (_uri == null) {
			_uri = Account.insert(match, db, _uri, values);
		}
		if (_uri == null) {
			_uri = DichVu.insert(match, db, _uri, values);
		}

		if (_uri == null) {
//			_uri = TinTuc.insert(match, db, _uri, values);
		}
		if (_uri == null) {
			_uri = HuongDanBanHang.insert(match, db, _uri, values);
		}

		if (_uri == null) {
			_uri = Recomment.insert(match, db, _uri, values);
		}

		if (_uri == null) {
			_uri = BangXepHang.insert(match, db, _uri, values);
		}

		if (_uri == null) {
			_uri = MauMoi.insert(match, db, _uri, values);
		}

		if (_uri != null) {
			getContext().getContentResolver().notifyChange(_uri, null);
		}
		return _uri;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		// openDB();
		int match = uriMatcher.match(uri);
		Cursor c = User.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		// cursor == null --> request othr
		if (c == null) {
			c = Account.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}
		// cursor == null --> request othr
		if (c == null) {
			c = DichVu.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}

		if (c == null) {
			c = HuongDanBanHang.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}
		// cursor == null --> request othr
		if (c == null) {
//			c = TinTuc.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}
		if (c == null) {
			c = Recomment.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}

		if (c == null) {
			c = BangXepHang.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
		}
		if (c == null) {
			c = MauMoi.query(match, db, uri, projection, selection, selectionArgs, sortOrder);
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
		// openDB();
		int count = 0;
		int match = uriMatcher.match(uri);

		count = User.delete(match, db, uri, selection, selectionArgs);
		if (count == -2) {
			count = Account.delete(match, db, uri, selection, selectionArgs);
		}
		// count == -2 delete other
		if (count == -2) {
			count = DichVu.delete(match, db, uri, selection, selectionArgs);
		}
		if (count == -2) {
			count = HuongDanBanHang.delete(match, db, uri, selection, selectionArgs);
		}
		// count == -2 delete other
		if (count == -2) {
//			count = TinTuc.delete(match, db, uri, selection, selectionArgs);
		}
		if (count == -2) {
			count = Recomment.delete(match, db, uri, selection, selectionArgs);
		}

		if (count == -2) {
			count = BangXepHang.delete(match, db, uri, selection, selectionArgs);
		}

		if (count == -2) {
			count = MauMoi.delete(match, db, uri, selection, selectionArgs);
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
		// openDB();
		int count = 0;

		int match = uriMatcher.match(uri);

		count = User.update(match, db, uri, values, selection, selectionArgs);
		if (count == -2) {
			count = Account.update(match, db, uri, values, selection, selectionArgs);
		}
		// count == -2 update other
		if (count == -2) {
			count = DichVu.update(match, db, uri, values, selection, selectionArgs);
		}
		if (count == -2) {
//			count = TinTuc.update(match, db, uri, values, selection, selectionArgs);
		}
		if (count == -2) {
			count = HuongDanBanHang.update(match, db, uri, values, selection, selectionArgs);
		}
		if (count == -2) {
			count = Recomment.update(match, db, uri, values, selection, selectionArgs);
		}

		if (count == -2) {
			count = BangXepHang.update(match, db, uri, values, selection, selectionArgs);
		}
		if (count == -2) {
			count = MauMoi.update(match, db, uri, values, selection, selectionArgs);
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
		Account.getType(mMap);
		DichVu.getType(mMap);
		Recomment.getType(mMap);
//		TinTuc.getType(mMap);
		HuongDanBanHang.getType(mMap);
		BangXepHang.getType(mMap);
		MauMoi.getType(mMap);
		String type = mMap.get(uriMatcher.match(uri));

		if (type == null) {
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		} else {
			return type;
		}
	}
}