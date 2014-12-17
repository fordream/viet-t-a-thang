package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;

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

}