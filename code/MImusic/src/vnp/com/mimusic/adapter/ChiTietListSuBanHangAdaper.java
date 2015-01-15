package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.graphics.BitmapFactory;
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
		sdt.setText(Conts.getString(jsonObject, "phone_custom"));
		dichvu.setText(Conts.getString(jsonObject, "service_name"));
		giacuoc.setText(Conts.getString(jsonObject, "price"));
		time_ban.setText(Conts.getString(jsonObject, "time_sale"));
		time_trave.setText(Conts.getString(jsonObject, "time_return"));
		String textstatus = Conts.getString(jsonObject, "status");
		textstatus = "1".equals(textstatus) ? parent.getContext().getString(R.string.thanhcong) : ("2".equals(textstatus) ? parent.getContext().getString(R.string.thatbai) : parent.getContext()
				.getString(R.string.tatca));
		status.setText(textstatus);

		ImageLoaderUtils.getInstance(getContext()).DisplayImage(Conts.getString(jsonObject, "avatar"), avatar, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.no_avatar));
		
		return convertView;
	}
}
