package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.MauMoiItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MauMoiAdaper extends ArrayAdapter<String> {

	public MauMoiAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MauMoiItemView(parent.getContext());
		}

		((MauMoiItemView)convertView).setBackgroundItemColor(position);
		return convertView;
	}
}
