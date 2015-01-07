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
import android.widget.ImageView;
import android.widget.TextView;

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
		TextView sdt = (TextView) convertView.findViewById(R.id.sdt);
		TextView dichvu = (TextView) convertView.findViewById(R.id.dichvu);
		TextView giacuoc = (TextView) convertView.findViewById(R.id.giacuoc);
		TextView time_ban = (TextView) convertView.findViewById(R.id.time_ban);
		TextView time_trave = (TextView) convertView.findViewById(R.id.time_trave);
		TextView status = (TextView) convertView.findViewById(R.id.status);

		ImageView avatar = (ImageView) convertView.findViewById(R.id.avatar);
		JSONObject jsonObject = getItem(position);
		try {
			sdt.setText(jsonObject.getString("phone_custom"));
			dichvu.setText(jsonObject.getString("service_name"));
			giacuoc.setText(jsonObject.getString("price"));
			time_ban.setText(jsonObject.getString("time_sale"));
			time_trave.setText(jsonObject.getString("time_return"));
			String textstatus = jsonObject.getString("status");
			textstatus = "1".equals(textstatus)? parent.getContext().getString(R.string.thanhcong):
				("2".equals(textstatus)?parent.getContext().getString(R.string.thatbai):parent.getContext().getString(R.string.tatca) );
			status.setText(textstatus);
		} catch (JSONException e) {
		}

		return convertView;
	}
}
