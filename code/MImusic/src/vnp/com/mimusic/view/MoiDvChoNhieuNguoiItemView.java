package vnp.com.mimusic.view;

import vnp.com.db.VasContact;
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

import com.viettel.vtt.vdealer.R;

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
		if (cursor.getString(cursor.getColumnIndex(VasContact.NAME)) != null) {
			name = cursor.getString(cursor.getColumnIndex(VasContact.NAME));
		}

		if (cursor.getString(cursor.getColumnIndex(VasContact.NAME_CONTACT)) != null) {
			if (name == null || name != null && name.trim().equals("")) {
				name = cursor.getString(cursor.getColumnIndex(VasContact.NAME_CONTACT));
			}
		}

		if (name == null || name != null && name.trim().equals("")) {
			name = cursor.getString(cursor.getColumnIndex(VasContact.PHONE));
		}

		if (name == null)
			name = "";

		View main = findViewById(R.id.menurightitem_main);
		main.setVisibility(Conts.xDontains(textSearch, false, new String[] { name }) ? View.VISIBLE : View.GONE);
		boolean needShow = Conts.xDontains(textSearch, true, new String[] { name, Conts.getStringCursor(cursor, VasContact.PHONE) });
		main.setVisibility(needShow ? View.VISIBLE : View.GONE);
		((TextView) findViewById(R.id.menu_right_item_tv_name)).setText(name);

		String avatar = cursor.getString(cursor.getColumnIndex(VasContact.AVATAR));
		String contact_id = Conts.getStringCursor(cursor, VasContact.contact_id);

		Conts.showAvatarContact(((ImageView) findViewById(R.id.menu_right_item_img_icon)), avatar, contact_id, Conts.resavatar()[cursor.getPosition() % Conts.resavatar().length]);

		Conts.setTextViewCursor(findViewById(R.id.menu_right_item_tv_link), cursor, VasContact.PHONE);

		Conts.getSDT(findViewById(R.id.menu_right_item_tv_link));
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_name));
		findViewById(R.id.menu_right_item_tv_link).setVisibility(View.VISIBLE);

	}

	public void initData(String name) {
		((TextView) findViewById(R.id.menu_right_item_tv_name)).setText(name);
		((ImageView) findViewById(R.id.menu_right_item_img_icon)).setVisibility(View.GONE);
		CheckBox menu_right_detail_checkbox = (CheckBox) findViewById(R.id.menu_right_detail_checkbox);
		menu_right_detail_checkbox.setOnCheckedChangeListener(null);
		menu_right_detail_checkbox.setVisibility(View.GONE);
	}

	public void updateBackground(int position) {
		findViewById(R.id.menurightitem_main).setBackgroundColor(getContext().getResources().getColor(position % 2 == 0 ? R.color.f6f6f6 : R.color.ffffff));
	}
}