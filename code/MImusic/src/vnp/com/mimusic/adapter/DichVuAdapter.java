package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.DichVuItemView;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class DichVuAdapter extends ArrayAdapter<JSONObject> {
	JSONArray array;
	private DichVuStore dichVuStore;

	public DichVuAdapter(Context context, JSONArray array) {
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new DichVuItemView(parent.getContext());
		}
		/*
		 * kiem tra xem co hien thi item nay khong
		 */
		JSONObject cursor = getItem(position);

		String name = Conts.getString(cursor, DichVuStore.service_name);
		final String id = Conts.getString(cursor, DichVuStore.ID);
		convertView.findViewById(R.id.home_item_main).setVisibility(Conts.xDontains(textSearch, false, new String[] { name }) ? View.VISIBLE : View.GONE);

		/**
		 * set data
		 */
		((DichVuItemView) convertView).setData(cursor, position);
		final boolean isDangKy = dichVuStore.isRegister(Conts.getString(cursor, DichVuStore.service_code));//

		/**
		 * add action
		 */
		ContentValues values = new ContentValues();
		values.put("name", String.format(parent.getContext().getString(R.string.title_dangky), Conts.getString(cursor, DichVuStore.service_name)));
		values.put(DichVuStore.service_name, Conts.getString(cursor, DichVuStore.service_name));
		values.put(DichVuStore.service_code, Conts.getString(cursor, DichVuStore.service_code));
		String content = String.format(parent.getContext().getString(R.string.xacnhandangky_form), Conts.getString(cursor, DichVuStore.service_name),
				Conts.getString(cursor, DichVuStore.service_price));
		values.put("content", content);
		values.put(DichVuStore.ID, Conts.getString(cursor, DichVuStore.ID));
		values.put("type", "dangky");
		convertView.findViewById(R.id.home_item_right_control_1).setOnClickListener(new DangKyClickListener(values, isDangKy));
		convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new MoiDichVuClickListener(Conts.getString(cursor, DichVuStore.service_code)));
		return convertView;
	}

	public abstract void dangKy(ContentValues values);

	public abstract void moiDVChoNhieuNguoi(String id);

	private String textSearch = "";

	public void setTextSearch(String textSearch) {
		this.textSearch = textSearch;
	}

	private class MoiDichVuClickListener implements OnClickListener {
		private String serviceCode;

		public MoiDichVuClickListener(String id) {
			this.serviceCode = id;
		}

		@Override
		public void onClick(View v) {
			moiDVChoNhieuNguoi(serviceCode);
		}
	}

	private class DangKyClickListener implements OnClickListener {
		private ContentValues id;
		private boolean isDangky = false;

		public DangKyClickListener(ContentValues id, boolean isDangky) {
			this.id = id;
			this.isDangky = isDangky;
		}

		@Override
		public void onClick(View v) {
			if (!isDangky) {
				dangKy(id);
			}
		}
	}
}
