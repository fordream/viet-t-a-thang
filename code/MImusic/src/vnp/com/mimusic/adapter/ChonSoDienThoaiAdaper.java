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
import android.widget.CheckBox;

public class ChonSoDienThoaiAdaper extends CursorAdapter {

	public ChonSoDienThoaiAdaper(Context context, Cursor c, String service_code) {
		super(context, c);
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

		final String _id = cursor.getString(cursor.getColumnIndex(User._ID));
		final String user = cursor.getString(cursor.getColumnIndex(User.USER));

//		convertView.findViewById(R.id.menurightitem_main).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				if (index == index1) {
//					index = -1;
//				} else {
//					index = index1;
//				}
//
//				notifyDataSetChanged();
//			}
//		});
//
//		convertView.findViewById(R.id.menurightitem_main).setBackgroundColor((index1 == index )? Color.RED : Color.WHITE);
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