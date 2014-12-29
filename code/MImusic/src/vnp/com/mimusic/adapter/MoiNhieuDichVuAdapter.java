package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.view.MoiNhieuDichVuItemView;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;

public abstract class MoiNhieuDichVuAdapter extends CursorAdapter {
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

		notifyDataSetChanged();
	}

	public MoiNhieuDichVuAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MoiNhieuDichVuItemView(context);
		}

		final String _id = cursor.getString(cursor.getColumnIndex(DichVu._ID));
		String name = cursor.getString(cursor.getColumnIndex(DichVu.NAME));
		convertView.findViewById(R.id.moinhieudichvu_item_main).setVisibility(name.toUpperCase().contains(textSearch.toUpperCase()) ? View.VISIBLE : View.GONE);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_tv_name.setText(name);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(null);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setChecked(listSelect.contains(_id));
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				add(_id);
			}
		});

		convertView.findViewById(R.id.moinhieudichvu_item_main).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				add(_id);
			}
		});
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new MoiNhieuDichVuItemView(context);
	}

	// public abstract void moiDVChoNhieuNguoi();

	private String textSearch = "";

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}
}