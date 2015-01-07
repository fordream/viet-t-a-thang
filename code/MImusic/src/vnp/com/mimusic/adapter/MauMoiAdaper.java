package vnp.com.mimusic.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.MauMoiItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MauMoiAdaper extends ArrayAdapter<JSONObject> {
	private JSONArray array;

	public MauMoiAdaper(Context context, JSONObject[] objects, JSONArray array) {
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
		}

		return null;
	}

	private int mPosition = 0;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MauMoiItemView(parent.getContext());
		}

		((MauMoiItemView) convertView).setBackgroundItemColor(position);

		JSONObject jsonObject = getItem(position);
		((MauMoiItemView) convertView).setData(jsonObject);
		final RadioButton maumoi_item_checkbox = (RadioButton) convertView.findViewById(R.id.maumoi_item_checkbox);
		maumoi_item_checkbox.setOnCheckedChangeListener(null);
		maumoi_item_checkbox.setChecked(mPosition == position);

		maumoi_item_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mPosition = position;
				notifyDataSetChanged();
			}
		});

		convertView.findViewById(R.id.maumoi_item_main).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mPosition = position;
				notifyDataSetChanged();
			}
		});

		return convertView;
	}

	public String getTemplate_id() {
		try {
			return array.getJSONObject(mPosition).getString("id");
		} catch (JSONException e) {
			return "";
		}
	}
}
