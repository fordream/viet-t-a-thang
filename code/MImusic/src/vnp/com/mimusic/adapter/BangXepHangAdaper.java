package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.view.BangXepHangItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BangXepHangAdaper extends ArrayAdapter<JSONObject> {
	private JSONArray jsonArray;
	private String type ="1";

	public BangXepHangAdaper(Context context, JSONArray jA) {
		super(context, 0, new JSONObject[] {});
		this.jsonArray = jA;
	}

	@Override
	public int getCount() {
		return jsonArray.length();
	}

	@Override
	public JSONObject getItem(int position) {
		try {
			JSONObject jo = jsonArray.getJSONObject(position);
			jo.put("position", position);
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

		return convertView;
	}

	public void setJSOnArray(JSONArray jA) {
		this.jsonArray = jA;
	}
	
	public void setType(String t) {
		type = t;
	}
}
