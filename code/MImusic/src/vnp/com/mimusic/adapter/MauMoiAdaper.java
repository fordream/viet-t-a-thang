package vnp.com.mimusic.adapter;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.MauMoiItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class MauMoiAdaper extends ArrayAdapter<String> {

	public MauMoiAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	private int mPosition = 0;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MauMoiItemView(parent.getContext());
		}

		((MauMoiItemView) convertView).setBackgroundItemColor(position);

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

		return convertView;
	}
}
