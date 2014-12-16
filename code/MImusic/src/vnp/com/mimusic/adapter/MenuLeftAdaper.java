package vnp.com.mimusic.adapter;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.BangXepHangItemView;
import vnp.com.mimusic.view.MenuLeftFooterView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuLeftAdaper extends ArrayAdapter<String> {

	public MenuLeftAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			LayoutInflater layoutInflater = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE));
			convertView = layoutInflater.inflate(R.layout.menu_item, null);
		}

		int resimg[] = new int[] {//
		R.drawable.menu_left_icon_1,//
				R.drawable.menu_left_icon_2,//
				R.drawable.menu_left_icon_3,//
				R.drawable.menu_left_icon_4,//
				R.drawable.menu_left_icon_5,//
		};//
		ImageView menu_item_img_icon = (ImageView) convertView.findViewById(R.id.menu_item_img_icon);
		menu_item_img_icon.setBackgroundResource(resimg[position]);

		TextView menu_item_tv = (TextView) convertView.findViewById(R.id.menu_item_tv);
		menu_item_tv.setText(getItem(position).toString());

		MenuLeftFooterView menu_left_footer = (MenuLeftFooterView) convertView.findViewById(R.id.menu_left_footer);
		return convertView;
	}
}
