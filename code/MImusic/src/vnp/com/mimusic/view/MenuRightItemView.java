package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.MenuRightItemView
public class MenuRightItemView extends LinearLayout {

	public MenuRightItemView(Context context) {
		super(context);
		init();
	}

	public MenuRightItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_right_item, this);
	}

	public void initData(final ContentValues contentValues) {
		ImageView menu_right_item_img_icon = (ImageView) findViewById(R.id.menu_right_item_img_icon);

		TextView menu_right_item_tv_name = (TextView) findViewById(R.id.menu_right_item_tv_name);
		TextView menu_right_item_tv_link = (TextView) findViewById(R.id.menu_right_item_tv_link);
		final Button menu_right_checkbox = (Button) findViewById(R.id.menu_right_bnt_moi);

		menu_right_item_img_icon.setBackgroundResource(R.drawable.avatar_example);

		menu_right_item_tv_name.setText(contentValues.getAsString("name"));
		menu_right_item_tv_link.setText(contentValues.getAsString("link"));
	}
}