package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.TintucItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TintucAdaper extends ArrayAdapter<JSONObject> {
	private JSONArray jsonArray;

	public TintucAdaper(Context context, JSONArray jsonArray) {
		super(context, 0, new JSONObject[] {});
		this.jsonArray = jsonArray;

		if (jsonArray.length() == 0) {
//			JSONObject jsonObject = new JSONObject();
			// "id":"12454",
			// "title":"Tiêu đề tin tức",
			// "header":"Nội dung tóm tắt",
			// "image":"/image.png",
			// "public_time":"12h12 12/12/2014",
			// "type":1
//			try {
//				jsonObject.put("id", "12454");
//				jsonObject.put("title", "Tiêu đề tin tức");
//				jsonObject.put("header", "Nội dung tóm tắt");
//				jsonObject.put("image", "/image.png");
//				jsonObject.put("public_time", "12h12 12/12/2014");
//				jsonObject.put("type", "1");
//			} catch (JSONException e) {
//			}

//			jsonArray.put(jsonObject);
		}
	}

	@Override
	public int getCount() {
		return jsonArray.length();
	}

	@Override
	public JSONObject getItem(int position) {
		try {
			return jsonArray.getJSONObject(position);
		} catch (JSONException e) {
		}

		return null;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new TintucItemView(parent.getContext());
		}
		convertView.findViewById(R.id.tintuc_item_main).setBackgroundColor(parent.getContext().getResources().getColor(position % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		((TintucItemView) convertView).setData(getItem(position));
		return convertView;
	}

	public void setJSOnArray(JSONArray jsonArray2) {
		this.jsonArray = jsonArray2;
	}
}
