package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.db.VasContact;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

//vnp.com.mimusic.view.NewHomeItemView

public class NewHomeItemView extends LinearLayout {
	public NewHomeItemView(Context context) {
		super(context);
		init();
	}

	public void initData(int position, NewHomeItem item) {
		int type = item.type;

		findViewById(R.id.new_home_item_blog0).setVisibility(type == 0 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog1).setVisibility(type == 1 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog2).setVisibility(type == 2 ? View.VISIBLE : View.GONE);

		Conts.setTextView(findViewById(R.id.name_1), item.name);
		Conts.setTextView(findViewById(R.id.menu_right_item_tv_name), item.name);
		Conts.setTextView(findViewById(R.id.home_item_tv_name), item.name);
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_name));

		if (type == 1) {
			Conts.showAvatarContact((ImageView) findViewById(R.id.menu_right_item_img_icon), item.avatar, item.contact_id, Conts.resavatar()[position % Conts.resavatar().length]);
		} else {
			Conts.showLogoDichvu((ImageView) findViewById(R.id.home_item_img_icon), item.avatar);
		}
		/**
		 * content
		 */

		Conts.setTextView(findViewById(R.id.menu_right_item_tv_link), item.content);
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_link));
		Conts.setTextView(findViewById(R.id.home_item_tv_content), item.content);
		Conts.setTextView(findViewById(R.id.home_item_right_control_1_tv), getContext().getString(item.isDangky ? R.string.dangdung : R.string.dangky));

		findViewById(R.id.new_home_item_blog2).setBackgroundResource(item.index == 0 ? R.drawable.home_dv_bg_0 : R.drawable.home_dv_bg_1);
		findViewById(R.id.home_item_right_control).setBackgroundResource(item.index == 0 ? R.drawable.home_dv_bg_x_0 : R.drawable.home_dv_bg_x_1);
		findViewById(R.id.home_item_img_icon).setBackgroundResource(item.index % 2 == 0 ? R.drawable.new_dichvu_icon_bg_0 : R.drawable.new_dichvu_icon_bg_1);
	}

	public NewHomeItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_item, this);
		findViewById(R.id.new_home_item_blog0).setVisibility(View.GONE);
		findViewById(R.id.new_home_item_blog1).setVisibility(View.GONE);
		findViewById(R.id.new_home_item_blog2).setVisibility(View.GONE);
	}

	public void updateUser(Cursor cursor) {
		findViewById(R.id.new_home_item_blog1).setVisibility(View.VISIBLE);

		ImageView icon = Conts.getView(this, R.id.menu_right_item_img_icon);
		TextView name = Conts.getView(this, R.id.menu_right_item_tv_name);
		TextView sdt = Conts.getView(this, R.id.menu_right_item_tv_link);
		Conts.showAvatarContact((ImageView) findViewById(R.id.menu_right_item_img_icon), Conts.getStringCursor(cursor, VasContact.AVATAR), Conts.getStringCursor(cursor, VasContact.contact_id),
				Conts.resavatar()[cursor.getPosition() % Conts.resavatar().length]);

		Conts.setTextViewCursor(name, cursor, VasContact.NAME_CONTACT);
		Conts.getSDT(name);

		Conts.setTextViewCursor(sdt, cursor, VasContact.PHONE);
		Conts.getSDT(sdt);
	}

	public void updateDichvu(Cursor cursor) {

	}

	public void showHeader(String header) {
		int type = 0;
		findViewById(R.id.new_home_item_blog0).setVisibility(type == 0 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog1).setVisibility(type == 1 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog2).setVisibility(type == 2 ? View.VISIBLE : View.GONE);

		Conts.setTextView(findViewById(R.id.name_1), header);
		Conts.setTextView(findViewById(R.id.menu_right_item_tv_name), header);
		Conts.setTextView(findViewById(R.id.home_item_tv_name), header);
	}

	public void showContact(String name, String sdt, String avatar, String contact_id, int position) {

		int type = 1;
		findViewById(R.id.new_home_item_blog0).setVisibility(type == 0 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog1).setVisibility(type == 1 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog2).setVisibility(type == 2 ? View.VISIBLE : View.GONE);

		Conts.setTextView(findViewById(R.id.menu_right_item_tv_name), name);
		Conts.setTextView(findViewById(R.id.menu_right_item_tv_link), sdt);
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_name));
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_link));
		Conts.showAvatarContact((ImageView) findViewById(R.id.menu_right_item_img_icon), avatar, contact_id, Conts.resavatar()[position % Conts.resavatar().length]);

	}

	public void showDichvu(JSONObject item, int position) {

		int type = 2;

		findViewById(R.id.new_home_item_blog0).setVisibility(type == 0 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog1).setVisibility(type == 1 ? View.VISIBLE : View.GONE);
		findViewById(R.id.new_home_item_blog2).setVisibility(type == 2 ? View.VISIBLE : View.GONE);

		final boolean isDangKy = new DichVuStore(getContext()).isRegister(Conts.getString(item, DichVuStore.service_code));
		Conts.setTextView(findViewById(R.id.name_1), item, DichVuStore.service_name);
		Conts.setTextView(findViewById(R.id.menu_right_item_tv_name), item, DichVuStore.service_name);
		Conts.setTextView(findViewById(R.id.home_item_tv_name), item, DichVuStore.service_name);
		Conts.setTextView(findViewById(R.id.menu_right_item_tv_name), item, DichVuStore.service_name);

		String avatar = Conts.getString(item, DichVuStore.service_icon);
		Conts.showLogoDichvu((ImageView) findViewById(R.id.home_item_img_icon), avatar);
		/**
		 * content
		 */
		String content = Conts.getString(item, DichVuStore.service_content);
		Conts.setTextView(findViewById(R.id.menu_right_item_tv_link), content);
		Conts.getSDT(findViewById(R.id.menu_right_item_tv_link));
		Conts.setTextView(findViewById(R.id.home_item_tv_content), content);
		Conts.setTextView(findViewById(R.id.home_item_right_control_1_tv), getContext().getString(isDangKy ? R.string.dangdung : R.string.dangky));

		findViewById(R.id.new_home_item_blog2).setBackgroundResource(position % 2 == 0 ? R.drawable.tranfer : R.drawable.new_dichvu_item_2_bg);
		findViewById(R.id.home_item_right_control).setBackgroundResource(position % 2 == 0 ? R.drawable.home_dv_bg_x_0 : R.drawable.home_dv_bg_x_1);
		findViewById(R.id.home_item_img_icon).setBackgroundResource(position % 2 == 0 ? R.drawable.new_dichvu_icon_bg_0 : R.drawable.new_dichvu_icon_bg_1);

	}
}