package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.BangXepHangItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class BangXepHangAdaper extends ArrayAdapter<String> {

	public BangXepHangAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new BangXepHangItemView(parent.getContext());
		}

		return convertView;
	}
}
