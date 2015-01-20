package vnp.com.mimusic.view;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoiDvChoNhieuNguoiItemView extends LinearLayout {

	public MoiDvChoNhieuNguoiItemView(Context context) {
		super(context);
		init();
	}

	public MoiDvChoNhieuNguoiItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moidvchonhieunguoi_item, this);
	}

	public void initData(Cursor cursor, String textSearch, String service_code) {
		String name = "";
		if (cursor.getString(cursor.getColumnIndex(User.NAME)) != null) {
			name = cursor.getString(cursor.getColumnIndex(User.NAME));
		}

		if (cursor.getString(cursor.getColumnIndex(User.NAME_CONTACT)) != null) {
			if (name == null || name != null && name.trim().equals("")) {
				name = cursor.getString(cursor.getColumnIndex(User.NAME_CONTACT));
			}
		}

		if (name == null || name != null && name.trim().equals("")) {
			name = cursor.getString(cursor.getColumnIndex(User.USER));
		}

		if (name == null)
			name = "";

		View main = findViewById(R.id.menurightitem_main);
		main.setVisibility(name.toUpperCase().contains(textSearch.toUpperCase()) ? View.VISIBLE : View.GONE);
		boolean needShow = Conts.contains(name, textSearch);

		if (!Conts.contains(cursor.getString(cursor.getColumnIndex(User.LISTIDDVSUDUNG)), service_code)) {
			needShow = false;
		}

		main.setVisibility(needShow ? View.VISIBLE : View.GONE);

		((TextView) findViewById(R.id.menu_right_item_tv_name)).setText(name);
		String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
		if (avatar == null) {

		} else {
			((ImageView) findViewById(R.id.menu_right_item_img_icon)).setBackgroundResource(R.drawable.no_avatar);
		}

		Conts.setTextViewCursor(findViewById(R.id.menu_right_item_tv_link), cursor, User.USER);
		
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_link));
		findViewById(R.id.menu_right_item_tv_link).setVisibility(View.VISIBLE);
	}

	public void initData(String name) {
		((TextView) findViewById(R.id.menu_right_item_tv_name)).setText(name);
		((ImageView) findViewById(R.id.menu_right_item_img_icon)).setVisibility(View.GONE);
		CheckBox menu_right_detail_checkbox = (CheckBox) findViewById(R.id.menu_right_detail_checkbox);
		menu_right_detail_checkbox.setOnCheckedChangeListener(null);
		menu_right_detail_checkbox.setVisibility(View.GONE);
	}
}