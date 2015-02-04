package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
		moinhieudichvu_item_icon = (ImageView) findViewById(R.id.moinhieudichvu_item_icon);
		moinhieudichvu_item_tv_name = (TextView) findViewById(R.id.moinhieudichvu_item_tv_name);
		moinhieudichvu_item_checkbox = (CheckBox) findViewById(R.id.moinhieudichvu_item_checkbox);
	}

	public ImageView moinhieudichvu_item_icon;
	public TextView moinhieudichvu_item_tv_name;
	public CheckBox moinhieudichvu_item_checkbox;

	// public void setData(Cursor cursor, List<String> listSelect, String
	// textSearch) {
	//
	// }
}