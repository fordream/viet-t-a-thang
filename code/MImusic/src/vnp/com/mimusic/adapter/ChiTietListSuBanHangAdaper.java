package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ChiTietListSuBanHangAdaper extends ArrayAdapter<JSONObject> {

	private JSONArray array;

	public ChiTietListSuBanHangAdaper(Context context, JSONObject[] objects, JSONArray array) {
		super(context, 0, objects);
		this.array = array;
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
			return null;
		}
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietlistsubanhang_item, null);
		}

		return convertView;
	}
}
