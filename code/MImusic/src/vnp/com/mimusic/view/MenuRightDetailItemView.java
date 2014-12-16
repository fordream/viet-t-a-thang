package vnp.com.mimusic.view;

import java.util.ArrayList;
import java.util.List;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MenuRightDetailAdaper;
import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//vnp.com.mimusic.view.MenuRightDetailView
public class MenuRightDetailItemView extends LinearLayout {

	public MenuRightDetailItemView(Context context) {
		super(context);
		init();
	}

	public MenuRightDetailItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_right_detail_item, this);
	}

	public void initData(final ContentValues contentValues) {
		ImageView menu_right_item_img_icon = (ImageView) findViewById(R.id.menu_right_detail_item_img_icon);

		TextView menu_right_item_tv_name = (TextView) findViewById(R.id.menu_right_detail_item_tv_name);
		TextView menu_right_item_tv_link = (TextView) findViewById(R.id.menu_right_detail_item_tv_link);
		final CheckBox menu_right_checkbox = (CheckBox) findViewById(R.id.menu_right_detail_checkbox);

		menu_right_item_img_icon.setBackgroundResource(R.drawable.icon_imusiz);

		menu_right_item_tv_name.setText(contentValues.getAsString("name"));
		menu_right_item_tv_link.setText(contentValues.getAsString("link"));
		menu_right_checkbox.setOnCheckedChangeListener(null);
		menu_right_checkbox.setChecked(contentValues.getAsBoolean("check"));
		menu_right_checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				contentValues.put("check", menu_right_checkbox.isChecked());
			}
		});
	}

}
