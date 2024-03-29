package vnp.com.mimusic.view;

import vnp.com.db.VasContact;
import vnp.com.mimusic.util.Conts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

//vnp.com.mimusic.view.MenuRightItemView
public class MenuRightItemView extends LinearLayout {
	private ImageView menu_right_item_img_icon;
	private TextView menu_right_item_tv_name;
	private TextView menu_right_item_tv_link;
	private View menurightitem_main;

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
		menu_right_item_img_icon = (ImageView) findViewById(R.id.menu_right_item_img_icon);
		menu_right_item_tv_name = (TextView) findViewById(R.id.menu_right_item_tv_name);
		menu_right_item_tv_link = (TextView) findViewById(R.id.menu_right_item_tv_link);
		menurightitem_main = findViewById(R.id.menurightitem_main);
	}

	public void initData(final ContentValues contentValues) {
		menu_right_item_img_icon.setBackgroundResource(R.drawable.bangxephang_avatar_example);
		menu_right_item_tv_name.setText(contentValues.getAsString("name"));
	}

	public void initData(Cursor cursor, String textSearch) {
		try {
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

			menurightitem_main.setVisibility(Conts.xDontains(textSearch, true, new String[] { name, Conts.getStringCursor(cursor, VasContact.PHONE) }) ? View.VISIBLE : View.GONE);
			menu_right_item_tv_name.setText(name);

			String avatar = cursor.getString(cursor.getColumnIndex(VasContact.AVATAR));
			String contact_id = Conts.getStringCursor(cursor, VasContact.contact_id);

			Conts.showAvatarContact(menu_right_item_img_icon, avatar, contact_id, Conts.resavatar()[cursor.getPosition() % Conts.resavatar().length]);

			menu_right_item_tv_link.setText(Conts.getStringCursor(cursor, VasContact.PHONE));
			Conts.getSDT(menu_right_item_tv_link);
			Conts.getSDT(menu_right_item_tv_name);
			String dvDaSuDung = cursor.getString(cursor.getColumnIndex(VasContact.LISTIDDVSUDUNG));

		} catch (Exception exception) {

		}

	}

	public void showFooter() {
		findViewById(R.id.menurightitem_main_main).setVisibility(VISIBLE);
	}

	public void initData(String name) {
		menu_right_item_tv_name.setText(name + "");
	}

	public void initSdt(String sdt) {
		menu_right_item_tv_link.setText(sdt);
		Conts.getSDT(menu_right_item_tv_link);
	}

	public void initData(Cursor cursor, String textSearch, int getPosition) {
		try {
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

			menurightitem_main.setVisibility(Conts.xDontains(textSearch, true, new String[] { name, Conts.getStringCursor(cursor, VasContact.PHONE) }) ? View.VISIBLE : View.GONE);
			menu_right_item_tv_name.setText(name);

			String avatar = cursor.getString(cursor.getColumnIndex(VasContact.AVATAR));
			String contact_id = Conts.getStringCursor(cursor, VasContact.contact_id);

			Conts.showAvatarContact(menu_right_item_img_icon, avatar, contact_id, Conts.resavatar()[getPosition % Conts.resavatar().length]);

			menu_right_item_tv_link.setText(Conts.getStringCursor(cursor, VasContact.PHONE));
			Conts.getSDT(menu_right_item_tv_link);
			Conts.getSDT(menu_right_item_tv_name);
			String dvDaSuDung = cursor.getString(cursor.getColumnIndex(VasContact.LISTIDDVSUDUNG));

		} catch (Exception exception) {

		}

	}

	String number = "";;

	public void show(boolean needShow, String text) {
		text = text.trim();
		if (needShow) {
			try {
				Long.parseLong(text);
				if (!Conts.isVietTelNUmber(text, getContext())) {
					needShow = false;
				}
			} catch (Exception exception) {
				needShow = false;
			}
		}

		if (!needShow) {
			findViewById(R.id.menurightitem_main).setVisibility(View.GONE);
		} else {
			findViewById(R.id.menurightitem_main).setVisibility(View.VISIBLE);
		}

		menu_right_item_tv_name.setText(R.string.somoi);
		menu_right_item_tv_link.setText(text);
		Conts.getSDT(menu_right_item_tv_link);
		Conts.showAvatarContact(menu_right_item_img_icon, "", "", Conts.resavatar()[0 % Conts.resavatar().length]);
		number = text;
	}

	public String getNumber() {
		return number;
	}
}