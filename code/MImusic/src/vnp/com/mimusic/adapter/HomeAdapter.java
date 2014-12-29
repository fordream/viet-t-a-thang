package vnp.com.mimusic.adapter;

import java.util.List;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.HomeItemView;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public abstract class HomeAdapter extends ArrayAdapter<ContentValues> {
	public HomeAdapter(Context context, List<ContentValues> objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new HomeItemView(parent.getContext());
		}

		((HomeItemView) convertView).setData(getItem(position), position);

		convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//if (!((ContentValues) getItem(position)).getAsBoolean("dangky")){
					moiDVChoNhieuNguoi();
				//}
			}
		});

		return convertView;
	}

	public abstract void moiDVChoNhieuNguoi();

}
