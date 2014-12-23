package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

//vnp.com.mimusic.view.DichVuItemView
public class MoiNhieuDichVuItemView extends LinearLayout {
	public MoiNhieuDichVuItemView(Context context) {
		super(context);
		init();
	}

	public MoiNhieuDichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinhieudichvu_item, this);
	}

}