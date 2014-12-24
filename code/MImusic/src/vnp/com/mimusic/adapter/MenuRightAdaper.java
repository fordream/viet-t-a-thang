package vnp.com.mimusic.adapter;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.view.MenuRightItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public abstract class MenuRightAdaper extends CursorAdapter {

	public MenuRightAdaper(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MenuRightItemView(context);
		}

		((MenuRightItemView) convertView).initData(cursor, textSearch);

	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new MenuRightItemView(context);
	}

	public abstract void openMoi(ContentValues contentValues);

	private String textSearch = "";

	public void setTextSearch(String textSearh) {
		this.textSearch = textSearh;
	}
}
