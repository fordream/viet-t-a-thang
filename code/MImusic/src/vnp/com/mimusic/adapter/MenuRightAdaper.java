package vnp.com.mimusic.adapter;

import vnp.com.db.User;
import vnp.com.mimusic.view.MenuRightItemView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.SectionIndexer;

public abstract class MenuRightAdaper extends CursorAdapter implements SectionIndexer {
	private AlphabetIndexer mAlphabetIndexer;
	private Handler handler = new Handler() {
		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
			// notifyDataSetChanged();
		}
	};

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

	public MenuRightAdaper(Context context, Cursor cursor) {
		super(context, cursor, true);
		mAlphabetIndexer = new AlphabetIndexer(cursor, cursor.getColumnIndex(User.NAME_CONTACT), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
		mAlphabetIndexer.setCursor(cursor);

		// setFilterQueryProvider(filterQueryProvider);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MenuRightItemView(context);
		}

		((MenuRightItemView) convertView).initData(cursor, textSearch);

	}

	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			protected void publishResults(final CharSequence constraint, final FilterResults results) {
				((Activity) mContext).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						changeCursor((Cursor) results.values);
						// handler.sendEmptyMessage(0);

					}
				});

			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				final FilterResults oReturn = new FilterResults();
				String search = constraint.toString().trim();
				oReturn.values = User.querySearch(mContext, search);
				return oReturn;
			}
		};
	}

	@Override
	public void changeCursor(Cursor cursor) {
		super.changeCursor(cursor);
		mAlphabetIndexer = new AlphabetIndexer(cursor, cursor.getColumnIndex(User.NAME_CONTACT_ENG), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
		mAlphabetIndexer.setCursor(cursor);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new MenuRightItemView(context);
	}

	public abstract void openMoi(ContentValues contentValues);

	private String textSearch = "";

	public void setTextSearch(final String textSearh) {
		getFilter().filter(textSearh);
	}

	private FilterQueryProvider filterQueryProvider = new FilterQueryProvider() {

		@Override
		public Cursor runQuery(CharSequence constraint) {
			String search = constraint.toString().trim();
			return User.querySearch(mContext, search);
		}
	};
}
