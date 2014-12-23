package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.MauMoiItemView;
import vnp.com.mimusic.view.ChonlistContactItemView;
import vnp.com.mimusic.view.MoiDanhBaItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MoiDanhBaAdapter extends ArrayAdapter<String> {

	public MoiDanhBaAdapter(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MoiDanhBaItemView(parent.getContext());
		}

		return convertView;
	}
}
