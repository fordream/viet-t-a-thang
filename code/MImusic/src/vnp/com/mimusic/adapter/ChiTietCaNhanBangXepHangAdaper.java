package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.ChiTietCaNhanBangXepHangItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ChiTietCaNhanBangXepHangAdaper extends ArrayAdapter<String> {

	public ChiTietCaNhanBangXepHangAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new ChiTietCaNhanBangXepHangItemView(parent.getContext());
		}

		return convertView;
	}
}
