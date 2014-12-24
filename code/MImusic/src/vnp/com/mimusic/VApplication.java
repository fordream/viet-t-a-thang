package vnp.com.mimusic;

import vnp.com.db.User;
import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

public class VApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
	}

	private boolean isRunDongBo = false;

	public void dongbodanhba() {
		if (!isRunDongBo) {
			isRunDongBo = true;
			new Thread(new Runnable() {
				@Override
				public void run() {
					ContentResolver cr = getContentResolver();
					Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
					while (cur != null && cur.moveToNext()) {
						String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
						String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
						if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
							Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[] { id }, null);
							while (pCur.moveToNext()) {
								String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
								if (name == null || name != null && name.trim().equals("")) {
									name = phoneNo;
								}
								
								// save to contact DB
								ContentValues values = new ContentValues();
								values.put(User.USER, phoneNo);
								values.put(User.NAME_CONTACT, name);
								String selection = String.format("%s ='%s'", User.USER, phoneNo);
								Cursor cursor = getContentResolver().query(User.CONTENT_URI, null, selection, null, null);

								if (cursor != null && cursor.getCount() >= 1) {
									cursor.close();
									getContentResolver().update(User.CONTENT_URI, values, selection, null);
									Log.e("dkm", String.format("update name : %s phone :%s", name, phoneNo));
								} else {
									cursor.close();
									getContentResolver().insert(User.CONTENT_URI, values);
									Log.e("dkm", String.format("insert name : %s phone :%s", name, phoneNo));
								}

							}
							pCur.close();
						}
					}

					isRunDongBo = false;
				}
			}).start();
		}
	}
}