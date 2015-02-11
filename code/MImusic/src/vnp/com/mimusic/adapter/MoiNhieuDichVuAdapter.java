package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.MoiNhieuDichVuItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;

public abstract class MoiNhieuDichVuAdapter extends ArrayAdapter<JSONObject> {
	JSONArray array;
	private DichVuStore dichVuStore;

	public MoiNhieuDichVuAdapter(Context context, JSONArray array) {
		super(context, 0);
		this.array = array;
		dichVuStore = new DichVuStore(getContext());
	}

	@Override
	public int getCount() {
		return array.length();
	}

	@Override
	public JSONObject getItem(int position) {
		try {
			return array.getJSONObject(position);
		} catch (JSONException e) {
			return new JSONObject();
		}

	}

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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new MoiNhieuDichVuItemView(parent.getContext());
		}
		JSONObject cursor = getItem(position);
		ImageView moinhieudichvu_item_icon = (ImageView) convertView.findViewById(R.id.moinhieudichvu_item_icon);
		final String id = Conts.getString(cursor, DichVuStore.ID);
		final String cs = Conts.getString(cursor, DichVuStore.service_code);
		String name = Conts.getString(cursor, DichVuStore.service_name);
		moinhieudichvu_item_icon.setImageResource(R.drawable.no_avatar);
		// show image
		final String service_icon = Conts.getString(cursor, DichVuStore.service_icon) + "";

		// ImageLoaderUtils.getInstance(context).displayImage(service_icon,
		// moinhieudichvu_item_icon, R.drawable.no_image);
		Conts.showLogoDichvu(moinhieudichvu_item_icon, service_icon);
		String service_code = Conts.getString(cursor, DichVuStore.service_code);
		View main = convertView.findViewById(R.id.moinhieudichvu_item_main);

		boolean needShow = Conts.xDontains(textSearch, false, new String[] { name });

		main.setVisibility(needShow ? View.VISIBLE : View.GONE);

		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_tv_name.setText(name);

		Conts.setTextView(Conts.getView(convertView, R.id.content), cursor, DichVuStore.service_content);

		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(null);
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setChecked(listSelect.contains(id));
		((MoiNhieuDichVuItemView) convertView).moinhieudichvu_item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				add(id, service_icon, cs);
			}
		});

		convertView.findViewById(R.id.moinhieudichvu_item_main).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				add(id, service_icon, cs);
			}
		});

		((MoiNhieuDichVuItemView) convertView).findViewById(R.id.moinhieudichvu_item_main).setBackgroundColor(
				parent.getContext().getResources().getColor(position % 2 == 0 ? R.color.f6f6f6 : R.color.ffffff));
		return convertView;
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