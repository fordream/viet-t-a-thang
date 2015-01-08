package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
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

	public List<String> getListSelect() {
		return listSelect;
	}

	public abstract void addOrRemove(String _id, boolean isAdd, String icon);

	public void remove(String _id) {
		listSelect.remove(_id);
	}

	private void add(String _id, String icon, String cs) {
		map.put(_id, cs);
		if (listSelect.contains(_id)) {
			listSelect.remove(_id);
			addOrRemove(_id, false, icon);
		} else {
			listSelect.add(_id);
			addOrRemove(_id, true, icon);
		}

		notifyDataSetChanged();
	}

	private String lISTIDDVSUDUNG;

	public MoiNhieuDichVuAdapter(Context context, Cursor c, String lISTIDDVSUDUNG) {
		super(context, c, true);
		this.lISTIDDVSUDUNG = lISTIDDVSUDUNG;
		if (Conts.isBlank(lISTIDDVSUDUNG)) {
			lISTIDDVSUDUNG = "";
		}
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new MoiNhieuDichVuItemView(context);
		}
		ImageView moinhieudichvu_item_icon = (ImageView) convertView.findViewById(R.id.moinhieudichvu_item_icon);
		final String _id = cursor.getString(cursor.getColumnIndex(DichVu._ID));
		final String cs = cursor.getString(cursor.getColumnIndex(DichVu.service_code));
		String name = cursor.getString(cursor.getColumnIndex(DichVu.service_name));
		moinhieudichvu_item_icon.setImageResource(R.drawable.no_avatar);
		// show image
		final String service_icon = cursor.getString(cursor.getColumnIndex(DichVu.service_icon)) + "";

		ImageLoaderUtils.getInstance(context).DisplayImage(service_icon, moinhieudichvu_item_icon);

		String service_code = cursor.getString(cursor.getColumnIndex(DichVu.service_code));
		View main = convertView.findViewById(R.id.moinhieudichvu_item_main);

		boolean needShow = Conts.contains(name, textSearch);
		if (!Conts.contains(lISTIDDVSUDUNG, service_code)) {
			needShow = false;
		}

		main.setVisibility(needShow ? View.VISIBLE : View.GONE);

		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_tv_name.setText(name);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(null);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setChecked(listSelect.contains(_id));
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				add(_id, service_icon, cs);
			}
		});

		convertView.findViewById(R.id.moinhieudichvu_item_main).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				add(_id, service_icon, cs);
			}
		});
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new MoiNhieuDichVuItemView(context);
	}

	private String textSearch = "";

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}

	private Map<String, String> map = new HashMap<String, String>();

	public String getService_code(String _id) {
		return map.get(_id);
	}
}