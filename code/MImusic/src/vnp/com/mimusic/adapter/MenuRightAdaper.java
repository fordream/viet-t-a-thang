package vnp.com.mimusic.adapter;

import vnp.com.db.User;
import vnp.com.mimusic.view.MenuRightItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.SectionIndexer;

public abstract class MenuRightAdaper extends CursorAdapter  {
//	private AlphabetIndexer mAlphabetIndexer;
//
//	@Override
//	public int getPositionForSection(int sectionIndex) {
//		return mAlphabetIndexer.getPositionForSection(sectionIndex);
//	}
//
//	/**
//	 * Returns the section index for a given position in the list by querying
//	 * the item and comparing it with all items in the section array.
//	 */
//	@Override
//	public int getSectionForPosition(int position) {
//		return mAlphabetIndexer.getSectionForPosition(position);
//	}
//
//	/**
//	 * Returns the section array constructed from the alphabet provided in the
//	 * constructor.
//	 */
//	@Override
//	public Object[] getSections() {
//		return mAlphabetIndexer.getSections();
//	}

	public MenuRightAdaper(Context context, Cursor cursor) {
		super(context, cursor);
//		mAlphabetIndexer = new AlphabetIndexer(cursor, cursor.getColumnIndex(User.NAME_CONTACT), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
//		mAlphabetIndexer.setCursor(cursor);
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
