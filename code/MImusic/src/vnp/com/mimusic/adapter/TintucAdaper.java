package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.TintucItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class TintucAdaper extends ArrayAdapter<String> {

	public TintucAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new TintucItemView(parent.getContext());
		}

		return convertView;
	}
}
