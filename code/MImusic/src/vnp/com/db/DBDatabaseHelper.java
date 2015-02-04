package vnp.com.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "midb";

	private static final int DATABASE_VERSION = 7;

	public DBDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(User.CREATE_DB_TABLE());
		db.execSQL(TinTuc.CREATE_DB_TABLE());
		db.execSQL(DichVu.CREATE_DB_TABLE());
		db.execSQL(Recomment.CREATE_DB_TABLE());
		db.execSQL(BangXepHang.CREATE_DB_TABLE());
		db.execSQL(MauMoi.CREATE_DB_TABLE());
		db.execSQL(HuongDanBanHang.CREATE_DB_TABLE());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + User.USER_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + DichVu.DICHVU_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + Recomment.RECOMMENT_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + BangXepHang.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + MauMoi.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TinTuc.TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + HuongDanBanHang.TABLE_NAME);
		onCreate(db);
	}
}