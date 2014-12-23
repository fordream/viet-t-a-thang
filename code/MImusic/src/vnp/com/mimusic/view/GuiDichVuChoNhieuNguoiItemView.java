package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GuiDichVuChoNhieuNguoiItemView extends LinearLayout {

	public GuiDichVuChoNhieuNguoiItemView(Context context) {
		super(context);
		init();
	}

	public GuiDichVuChoNhieuNguoiItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.guidichvuchonhieunguoi_item, this);

		findViewById(R.id.guidichvuchonhieunguoi_item_dichvuitemview).findViewById(R.id.home_item_right_control).setVisibility(View.INVISIBLE);
		//findViewById(R.id.guidichvuchonhieunguoi_item_menurightitemview).findViewById(R.id.menu_right_bnt_moi).setVisibility(View.INVISIBLE);
	}

	public void show(int i, String string) {
		TextView guidichvuchonhieunguoi_item_txt_header = (TextView) findViewById(R.id.guidichvuchonhieunguoi_item_txt_header);
		guidichvuchonhieunguoi_item_txt_header.setText(string);

		findViewById(R.id.guidichvuchonhieunguoi_item_txt_header).setVisibility(i == 0 ? VISIBLE : GONE);
		findViewById(R.id.guidichvuchonhieunguoi_item_menurightitemview).setVisibility(i == 1 ? VISIBLE : GONE);
		findViewById(R.id.guidichvuchonhieunguoi_item_dichvuitemview).setVisibility(i == 2 ? VISIBLE : GONE);
	}
}
