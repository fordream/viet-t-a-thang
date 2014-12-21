package vnp.com.mimusic.adapter;

import vnp.com.mimusic.R;
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

		convertView.findViewById(R.id.tintuc_item_main).setBackgroundColor(parent.getContext().getResources().getColor(position % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
		return convertView;
	}
}
