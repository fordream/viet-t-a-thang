package vnp.com.mimusic.adapter;

import vnp.com.db.BangXepHang;
import vnp.com.mimusic.view.BangXepHangItemView;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

public class BangXepHangCursorAdaper extends CursorAdapter {
	public BangXepHangCursorAdaper(Context context, Cursor c) {
		super(context, c);
	}

	private String type = BangXepHang.typeDOANHTHU;

	public void setType(String type) {
		this.type = type;
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
		return new BangXepHangItemView(arg0);
	}

	@Override
	public Filter getFilter() {
		return new Filter() {

			@Override
			protected void publishResults(final CharSequence constraint, final FilterResults results) {
				((Activity) mContext).runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (((Cursor) results.values).getCount() == 0) {
							message.setText(noDataText);
						} else {
							message.setText("");
						}
						changeCursor((Cursor) results.values);

					}
				});

			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				final FilterResults oReturn = new FilterResults();
				oReturn.values = BangXepHang.getBangXepHang(mContext, type);
				return oReturn;
			}
		};
	}

	private TextView message;
	String noDataText;

	public void setText(TextView message, String noDataText) {
		this.message = message;
		this.noDataText = noDataText;
	}
}
