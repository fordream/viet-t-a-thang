package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.view.MoiDvChoNhieuNguoiItemView;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public abstract class MoiDvChoNhieuNguoiAdaper extends CursorAdapter {
	private List<String> listSelect = new ArrayList<String>();

	public abstract void addOrRemove(String _id, boolean isAdd);

	public void remove(String _id) {
		listSelect.remove(_id);
	}

	private void add(String _id) {
		if (listSelect.contains(_id)) {
			listSelect.remove(_id);
			addOrRemove(_id, false);
		} else {
			listSelect.add(_id);
			addOrRemove(_id, true);
		}
	}

	public MoiDvChoNhieuNguoiAdaper(Context context, Cursor c) {
		super(context, c);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MoiDvChoNhieuNguoiItemView(context);
		}

		((MoiDvChoNhieuNguoiItemView) convertView).initData(cursor, textSearch);

		CheckBox menu_right_detail_checkbox = (CheckBox) ((MoiDvChoNhieuNguoiItemView) convertView).findViewById(R.id.menu_right_detail_checkbox);
		menu_right_detail_checkbox.setOnCheckedChangeListener(null);

		final String _id = cursor.getString(cursor.getColumnIndex(User._ID));
		menu_right_detail_checkbox.setChecked(listSelect.contains(_id));
		menu_right_detail_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				add(_id);
			}
		});

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
