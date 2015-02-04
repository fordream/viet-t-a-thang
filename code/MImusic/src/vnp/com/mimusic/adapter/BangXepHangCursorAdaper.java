package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.BangXepHangItemView;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;

public class BangXepHangCursorAdaper extends CursorAdapter {
	public BangXepHangCursorAdaper(Context context, Cursor c) {
		super(context, c);
	}

	// private JSONArray jsonArray;
	private String type = "1";

	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	//
	// if (convertView == null) {
	// convertView = new BangXepHangItemView(parent.getContext());
	// }
	//
	// ((BangXepHangItemView) convertView).setData(getItem(position));
	// ((BangXepHangItemView) convertView).setDataColor(position);
	// return convertView;
	// }

	public void setType(String t) {
		type = t;
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {

		if (convertView == null) {
			convertView = new BangXepHangItemView(context);
		}

		((BangXepHangItemView) convertView).setData(cursor);
		((BangXepHangItemView) convertView).setDataColor(cursor.getPosition());
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return new BangXepHangItemView(arg0);
	}

}
