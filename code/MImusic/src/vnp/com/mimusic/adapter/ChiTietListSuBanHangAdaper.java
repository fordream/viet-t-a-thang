package vnp.com.mimusic.adapter;

import vnp.com.mimusic.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ChiTietListSuBanHangAdaper extends ArrayAdapter<String> {

	public ChiTietListSuBanHangAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chitietlistsubanhang_item, null);
		}

		return convertView;
	}
}
