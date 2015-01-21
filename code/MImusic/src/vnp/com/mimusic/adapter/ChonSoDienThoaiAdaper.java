package vnp.com.mimusic.adapter;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.MoiDvChoNhieuNguoiItemView;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.CheckBox;
import android.widget.SectionIndexer;

public class ChonSoDienThoaiAdaper extends CursorAdapter implements SectionIndexer {
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

	public ChonSoDienThoaiAdaper(Context context, Cursor cursor, String service_code) {
		super(context, cursor);
		mAlphabetIndexer = new AlphabetIndexer(cursor, cursor.getColumnIndex(User.NAME_CONTACT), " ABCDEFGHIJKLMNOPQRTSUVWXYZ");
		mAlphabetIndexer.setCursor(cursor);
	}

	private int index = -1;

	public int getIndex() {
		return index;
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MoiDvChoNhieuNguoiItemView(context);
		}

		final int index1 = cursor.getPosition();
		((MoiDvChoNhieuNguoiItemView) convertView).initData(cursor, textSearch, "");

		CheckBox menu_right_detail_checkbox = (CheckBox) ((MoiDvChoNhieuNguoiItemView) convertView).findViewById(R.id.menu_right_detail_checkbox);
		menu_right_detail_checkbox.setOnCheckedChangeListener(null);
		menu_right_detail_checkbox.setVisibility(View.GONE);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new MoiDvChoNhieuNguoiItemView(context);
	}

	private String textSearch = "";

	public void setTextSearch(String textSearh) {
		this.textSearch = textSearh;
	}

}