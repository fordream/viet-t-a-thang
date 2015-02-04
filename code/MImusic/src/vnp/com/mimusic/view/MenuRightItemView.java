package vnp.com.mimusic.view;

import vnp.com.db.User;
import vnp.com.mimusic.R;
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

			menurightitem_main.setVisibility(Conts.xDontains(textSearch, true, new String[] { name, Conts.getStringCursor(cursor, User.USER) }) ? View.VISIBLE : View.GONE);
			menu_right_item_tv_name.setText(name);

			String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
			String contact_id = Conts.getStringCursor(cursor, User.contact_id);

			Conts.showAvatarNoImage(menu_right_item_img_icon, avatar, contact_id);

			menu_right_item_tv_link.setText(Conts.getStringCursor(cursor, User.USER));
			Conts.getSDT(menu_right_item_tv_link);
			Conts.getSDT(menu_right_item_tv_name);
			String dvDaSuDung = cursor.getString(cursor.getColumnIndex(User.LISTIDDVSUDUNG));

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
}