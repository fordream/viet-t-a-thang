package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.User;
import vnp.com.mimusic.view.MenuRightItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

public abstract class NewMenuRightAdaper extends ArrayAdapter<ContentValues> implements SectionIndexer {
	private List<ContentValues> list = new ArrayList<ContentValues>();
	Cursor cursor;

	public NewMenuRightAdaper(Context context, int resource, List<ContentValues> objects, Cursor cursor) {
		super(context, resource, 0, objects);
		list = objects;
		mAlphabetIndexer = new AlphabetIndexer(cursor, cursor.getColumnIndex(User.NAME_CONTACT), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
		mAlphabetIndexer.setCursor(cursor);
		this.cursor = cursor;

	}

	@Override
	public int getCount() {
		return cursor.getCount();
	}

	private AlphabetIndexer mAlphabetIndexer;

	@Override
	public int getPositionForSection(int sectionIndex) {
		return mAlphabetIndexer.getPositionForSection(sectionIndex);
	}

	/**
	 * Returns the section index for a given position in the list by querying
	 * the item and comparing it with all items in the section array.
	 */
	@Override
	public int getSectionForPosition(int position) {
		return mAlphabetIndexer.getSectionForPosition(position);
	}

	/**
	 * Returns the section array constructed from the alphabet provided in the
	 * constructor.
	 */
	@Override
	public Object[] getSections() {
		return mAlphabetIndexer.getSections();
	}

	// @Override
	// public void bindView(View convertView, Context context, Cursor cursor) {
	// if (convertView == null) {
	// convertView = new MenuRightItemView(context);
	// }
	//
	// ((MenuRightItemView) convertView).initData(cursor, textSearch);
	//
	// }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MenuRightItemView(parent.getContext());
		}
		cursor.moveToPosition(position);
		
		((MenuRightItemView) convertView).initData(cursor, textSearch);
		return convertView;
	}

	// @Override
	// public Filter getFilter() {
	// return new Filter() {
	//
	// @Override
	// protected void publishResults(final CharSequence constraint, final
	// FilterResults results) {
	// // changeCursor((Cursor) results.values);
	// // handler.sendEmptyMessage(0);
	//
	// }
	//
	// @Override
	// protected FilterResults performFiltering(CharSequence constraint) {
	// // final FilterResults oReturn = new FilterResults();
	// // String search = constraint.toString().trim();
	// // oReturn.values = User.querySearch(mContext, search);
	// return oReturn;
	// }
	// };
	// }

	// @Override
	// public View newView(Context context, Cursor cursor, ViewGroup parent) {
	// return new MenuRightItemView(context);
	// }

	public abstract void openMoi(ContentValues contentValues);

	private String textSearch = "";

	public void setTextSearch(final String textSearh) {
		this.textSearch = textSearh;
		getFilter().filter(textSearh);
	}

	// private FilterQueryProvider filterQueryProvider = new
	// FilterQueryProvider() {
	//
	// @Override
	// public Cursor runQuery(CharSequence constraint) {
	// String search = constraint.toString().trim();
	// return User.querySearch(mContext, search);
	// }
	// };
}
