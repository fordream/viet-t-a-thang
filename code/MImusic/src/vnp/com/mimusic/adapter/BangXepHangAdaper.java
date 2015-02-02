package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.BangXepHangItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BangXepHangAdaper extends ArrayAdapter<JSONObject> {
	// private JSONArray jsonArray;
	private String type = "1";
	List<JSONObject> list = new ArrayList<JSONObject>();

	public BangXepHangAdaper(Context context, JSONArray jA) {
		super(context, 0, new JSONObject[] {});
		// this.jsonArray = jA;

		for (int i = 0; i < jA.length(); i++) {
			try {
				list.add(jA.getJSONObject(i));
			} catch (JSONException e) {
			}
		}
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public JSONObject getItem(int position) {
		try {
			JSONObject jo = list.get(position);
			jo.put("position", (position));
			jo.put("type", type);
			return jo;
		} catch (JSONException e) {
		}

		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new BangXepHangItemView(parent.getContext());
		}

		((BangXepHangItemView) convertView).setData(getItem(position));
		((BangXepHangItemView) convertView).setDataColor(position);
		return convertView;
	}

	// public void setJSOnArray(JSONArray jA) {
	// list.clear();
	// for (int i = 0; i < jA.length(); i++) {
	// try {
	// list.add(jA.getJSONObject(i));
	// } catch (JSONException e) {
	// }
	// }
	// }

	public void setType(String t) {
		type = t;
		Collections.sort(list, new Comparator<JSONObject>() {

			@Override
			public int compare(JSONObject lhs, JSONObject rhs) {
				if (lhs != null && rhs != null) {
					String key = "commission";
					if (!"1".equals(type)) {
						key = "quantity";
					}

					return Conts.getString(lhs, key).compareTo(Conts.getString(rhs, key)) > 0 ? -1 : 1;
				}
				return 0;
			}
		});
	}
}
