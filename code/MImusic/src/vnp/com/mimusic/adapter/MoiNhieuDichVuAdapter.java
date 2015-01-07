package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.ImageLoaderUtils;
import vnp.com.mimusic.view.MoiNhieuDichVuItemView;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CursorAdapter;
import android.widget.ImageView;

public abstract class MoiNhieuDichVuAdapter extends CursorAdapter {
	private List<String> listSelect = new ArrayList<String>();

	public abstract void addOrRemove(String _id, boolean isAdd, String icon);

	public void remove(String _id) {
		listSelect.remove(_id);
	}

	private void add(String _id, String icon) {
		if (listSelect.contains(_id)) {
			listSelect.remove(_id);
			addOrRemove(_id, false, icon);
		} else {
			listSelect.add(_id);
			addOrRemove(_id, true, icon);
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
		ImageView moinhieudichvu_item_icon = (ImageView) convertView.findViewById(R.id.moinhieudichvu_item_icon);
		final String _id = cursor.getString(cursor.getColumnIndex(DichVu._ID));
		String name = cursor.getString(cursor.getColumnIndex(DichVu.service_name));
		moinhieudichvu_item_icon.setImageResource(R.drawable.no_avatar);
		// show image
		final String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";

		ImageLoaderUtils.getInstance(context).DisplayImage(service_icon, moinhieudichvu_item_icon);
		convertView.findViewById(R.id.moinhieudichvu_item_main).setVisibility(name.toUpperCase().contains(textSearch.toUpperCase()) ? View.VISIBLE : View.GONE);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_tv_name.setText(name);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(null);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setChecked(listSelect.contains(_id));
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				add(_id, service_icon);
			}
		});

		convertView.findViewById(R.id.moinhieudichvu_item_main).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				add(_id, service_icon);
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