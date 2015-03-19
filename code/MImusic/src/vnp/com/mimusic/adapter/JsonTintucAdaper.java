package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.datastore.TintucStore;
import vnp.com.mimusic.view.TintucItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.viettel.vtt.vdealer.R;

public class JsonTintucAdaper extends BaseAdapter {
	private JSONArray array = new JSONArray();
	private Context context;
	private TintucStore tintucStore;

	public JsonTintucAdaper(Context context) {
		this.context = context;
		tintucStore = new TintucStore(context);
	}

	public void getData() {
		array = tintucStore.listRowId();
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return array.length();
	}

	@Override
	public Object getItem(int position) {
		try {
			return array.get(position).toString();
		} catch (JSONException e) {
			return "";
		}
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new TintucItemView(parent.getContext());
		}
		
		convertView.findViewById(R.id.tintuc_item_main).setBackgroundResource(position % 2 == 0 ? R.drawable.new_tintuc_bg1 : R.drawable.new_tintuc_bg2);
		String id = getItem(position).toString();
		JSONObject object = tintucStore.getJsonById(id);
		((TintucItemView) convertView).setData(object);
		return convertView;
	}

}
