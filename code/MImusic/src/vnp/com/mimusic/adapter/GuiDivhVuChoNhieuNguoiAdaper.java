package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.BangXepHangItemView;
import vnp.com.mimusic.view.GuiDichVuChoNhieuNguoiItemView;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class GuiDivhVuChoNhieuNguoiAdaper extends ArrayAdapter<String> {

	public GuiDivhVuChoNhieuNguoiAdaper(Context context, String[] objects) {
		super(context, 0, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (convertView == null) {
			convertView = new GuiDichVuChoNhieuNguoiItemView(parent.getContext());
		}

		if (position == 0) {
			((GuiDichVuChoNhieuNguoiItemView) convertView).show(0, "Gửi cho");
		} else if (position == 2) {
			((GuiDichVuChoNhieuNguoiItemView) convertView).show(0, "Danh sách các dịch vụ");
		} else if (position == 1) {
			((GuiDichVuChoNhieuNguoiItemView) convertView).show(1, "Danh sách các dịch vụ");
		} else {
			((GuiDichVuChoNhieuNguoiItemView) convertView).show(2, "Danh sách các dịch vụ");
		}
		return convertView;
	}
}
