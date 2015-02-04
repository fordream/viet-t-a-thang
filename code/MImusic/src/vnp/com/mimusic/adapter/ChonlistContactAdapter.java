package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.ChonlistContactItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class ChonlistContactAdapter extends ArrayAdapter<String> {

	public ChonlistContactAdapter(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new ChonlistContactItemView(parent.getContext());
		}

		return convertView;
	}
}
