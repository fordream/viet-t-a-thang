package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

//vnp.com.mimusic.view.DichVuItemView
public class DichVuItemView extends HomeItemView {
	public DichVuItemView(Context context) {
		super(context);
	}

	public DichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setData(Cursor cursor) {
		findViewById(R.id.home_item_header).setVisibility(View.GONE);
	}

	public void initData(String string) {
		((TextView) findViewById(R.id.home_item_tv_name)).setText(string);
		findViewById(R.id.home_item_img_icon).setVisibility(INVISIBLE);
		findViewById(R.id.home_item_right_control).setVisibility(GONE);
		findViewById(R.id.home_item_tv_content).setVisibility(View.GONE);
	}

}