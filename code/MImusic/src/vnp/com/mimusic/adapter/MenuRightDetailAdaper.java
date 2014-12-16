package vnp.com.mimusic.adapter;

import java.util.List;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.BangXepHangItemView;
import vnp.com.mimusic.view.MenuLeftFooterView;
import vnp.com.mimusic.view.MenuRightDetailItemView;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MenuRightDetailAdaper extends ArrayAdapter<ContentValues> {
	private List<ContentValues> objects;

	public MenuRightDetailAdaper(Context context, List<ContentValues> objects) {
		super(context, 0, objects);
		this.objects = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new MenuRightDetailItemView(parent.getContext());
		}
		((MenuRightDetailItemView) convertView).initData(objects.get(position));

		return convertView;
	}
}
