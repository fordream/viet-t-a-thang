package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.MauMoiItemView;
import vnp.com.mimusic.view.ChonlistContactItemView;
import vnp.com.mimusic.view.MoiDanhBaItemView;
import vnp.com.mimusic.view.MoiNhieuDichVuItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MoiNhieuDichVuAdapter extends ArrayAdapter<String> {

	public MoiNhieuDichVuAdapter(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MoiNhieuDichVuItemView(parent.getContext());
		}

		return convertView;
	}
}
