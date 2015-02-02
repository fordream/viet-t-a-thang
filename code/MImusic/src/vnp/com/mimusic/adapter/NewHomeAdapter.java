package vnp.com.mimusic.adapter;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.DichVu;
import vnp.com.db.Recomment;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

public abstract class NewHomeAdapter extends ArrayAdapter<NewHomeItem> {
	private List<NewHomeItem> list = new ArrayList<NewHomeItem>();

	public NewHomeAdapter(Context context) {
		super(context, 0);

		// Cursor cursorUserRecomment = Recomment.getCursorFromUser(context, 5);
		//
		// if (cursorUserRecomment != null) {
		// if (cursorUserRecomment.getCount() > 0) {
		// NewHomeItem item = new NewHomeItem();
		// item.type = 0;
		// item.name = context.getString(R.string.moithanhvien);
		//
		// list.add(item);
		// }
		//
		// while (cursorUserRecomment.moveToNext()) {
		// NewHomeItem item = new NewHomeItem();
		// item.type = 1;
		// item.name = Conts.getStringCursor(cursorUserRecomment,
		// User.NAME_CONTACT);
		// item.content = Conts.getStringCursor(cursorUserRecomment, User.USER);
		// item.avatar = Conts.getStringCursor(cursorUserRecomment,
		// User.AVATAR);
		// item.contact_id = Conts.getStringCursor(cursorUserRecomment,
		// User.contact_id);
		//
		// list.add(item);
		// }
		//
		// cursorUserRecomment.close();
		// }
		//
		// Cursor cursorDVHot = DichVu.getCursorFromUser(context, 3);
		// if (cursorDVHot != null) {
		// if (cursorDVHot.getCount() > 0) {
		// NewHomeItem item = new NewHomeItem();
		// item.type = 0;
		// item.name = context.getString(R.string.dichvuhot);
		// list.add(item);
		// }
		// int index = 0;
		// while (cursorDVHot.moveToNext()) {
		// NewHomeItem item = new NewHomeItem();
		// item.type = 2;
		// item.id = Conts.getStringCursor(cursorDVHot, DichVu.ID);
		// item.name = Conts.getStringCursor(cursorDVHot, DichVu.service_name);
		// item.content = Conts.getStringCursor(cursorDVHot,
		// DichVu.service_content);
		// item.avatar = Conts.getStringCursor(cursorDVHot,
		// DichVu.service_icon);
		// item.service_code = Conts.getStringCursor(cursorDVHot,
		// DichVu.service_code);
		// item.service_price = Conts.getStringCursor(cursorDVHot,
		// DichVu.service_price);
		// item.isDangky = "0".equals(Conts.getStringCursor(cursorDVHot,
		// DichVu.service_status));
		// item.index = index;
		//
		// if (index == 0) {
		// index = 1;
		// } else {
		// index = 0;
		// }
		// list.add(item);
		// }
		//
		// cursorDVHot.close();
		// }
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public NewHomeItem getItem(int position) {
		return list.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new NewHomeItemView(getContext());
		}

		((NewHomeItemView) convertView).initData(position, getItem(position));

		Conts.getView(convertView, R.id.new_home_header_spacing).setVisibility(getItem(position).type == 0 && position > 0 ? View.VISIBLE : View.GONE);

		convertView.findViewById(R.id.moi).setOnClickListener(new MoiOnClickListener(getItem(position).content, getItem(position).name));
		convertView.findViewById(R.id.xemall).setOnClickListener(new XemAllClickListener(getItem(position).name));

		ContentValues values = new ContentValues();
		values.put("name", String.format(getContext().getString(R.string.title_dangky), getItem(position).name));
		values.put(DichVu.service_code, getItem(position).service_code);
		String content = String.format(getContext().getString(R.string.xacnhandangky_form), getItem(position).name, getItem(position).service_price);
		values.put("content", content);
		values.put(DichVu.ID, getItem(position).id);
		values.put("type", "dangky");
		convertView.findViewById(R.id.home_item_right_control_1).setOnClickListener(new DangKyClickListener(values, getItem(position).isDangky));
		convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new MoiDichVuClickListener(getItem(position).id));
		return convertView;
	}

	private class NewHomeItemView extends LinearLayout {
		public NewHomeItemView(Context context) {
			super(context);
			init();
		}

		public void initData(int position, NewHomeItem item) {
			int type = item.type;

			findViewById(R.id.new_home_item_blog0).setVisibility(type == 0 ? View.VISIBLE : View.GONE);
			findViewById(R.id.new_home_item_blog1).setVisibility(type == 1 ? View.VISIBLE : View.GONE);
			findViewById(R.id.new_home_item_blog2).setVisibility(type == 2 ? View.VISIBLE : View.GONE);

			/**
			 * name
			 */
			Conts.setTextView(findViewById(R.id.name_1), item.name);
			Conts.setTextView(findViewById(R.id.menu_right_item_tv_name), item.name);
			Conts.setTextView(findViewById(R.id.home_item_tv_name), item.name);
			Conts.getSDT(findViewById(R.id.menu_right_item_tv_name));

			Conts.showAvatarNoImage((ImageView) findViewById(R.id.menu_right_item_img_icon), item.avatar, item.contact_id);
			ImageLoaderUtils.getInstance(getContext()).DisplayImage(item.avatar, (ImageView) findViewById(R.id.home_item_img_icon), R.drawable.no_image);
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
		}
	}

	private class MoiOnClickListener implements OnClickListener {
		private String user = "";
		private String name = "";

		public MoiOnClickListener(String user, String name) {
			this.user = user;
			this.name = name;
		}

		@Override
		public void onClick(View v) {
			moiContactUser(user, name);
		}

	}

	private class XemAllClickListener implements OnClickListener {
		private String name = "";

		public XemAllClickListener(String user) {
			this.name = user;
		}

		@Override
		public void onClick(View v) {
			xemall(name);
		}
	}

	private class DangKyClickListener implements OnClickListener {
		private ContentValues id;
		private boolean isDangky = false;

		public DangKyClickListener(ContentValues id, boolean isDangky) {
			this.id = id;
			this.isDangky = isDangky;
		}

		@Override
		public void onClick(View v) {
			if (!isDangky) {
				dangKy(id);
			}
		}
	}

	private class MoiDichVuClickListener implements OnClickListener {
		private String id;

		public MoiDichVuClickListener(String id) {
			this.id = id;
		}

		@Override
		public void onClick(View v) {
			moiDVChoNhieuNguoi(id);
		}
	}

	public abstract void moiDVChoNhieuNguoi(String id);

	public abstract void dangKy(ContentValues values);

	public abstract void xemall(String name);

	public abstract void moiContactUser(String user, String name);

	public void update() {
		list.clear();
		Cursor cursorUserRecomment = Recomment.getCursorFromUser(getContext(), 5);

		if (cursorUserRecomment != null) {
			if (cursorUserRecomment.getCount() > 0) {
				NewHomeItem item = new NewHomeItem();
				item.type = 0;
				item.name = getContext().getString(R.string.moithanhvien);

				list.add(item);
			}

			while (cursorUserRecomment.moveToNext()) {
				NewHomeItem item = new NewHomeItem();
				item.type = 1;
				item.name = Conts.getStringCursor(cursorUserRecomment, User.NAME_CONTACT);
				item.content = Conts.getStringCursor(cursorUserRecomment, User.USER);
				item.avatar = Conts.getStringCursor(cursorUserRecomment, User.AVATAR);
				item.contact_id = Conts.getStringCursor(cursorUserRecomment, User.contact_id);

				list.add(item);
			}

			cursorUserRecomment.close();
		}

		Cursor cursorDVHot = DichVu.getCursorFromUser(getContext(), 3);
		if (cursorDVHot != null) {
			if (cursorDVHot.getCount() > 0) {
				NewHomeItem item = new NewHomeItem();
				item.type = 0;
				item.name = getContext().getString(R.string.dichvuhot);
				list.add(item);
			}
			int index = 0;
			while (cursorDVHot.moveToNext()) {
				NewHomeItem item = new NewHomeItem();
				item.type = 2;
				item.id = Conts.getStringCursor(cursorDVHot, DichVu.ID);
				item.name = Conts.getStringCursor(cursorDVHot, DichVu.service_name);
				item.content = Conts.getStringCursor(cursorDVHot, DichVu.service_content);
				item.avatar = Conts.getStringCursor(cursorDVHot, DichVu.service_icon);
				item.service_code = Conts.getStringCursor(cursorDVHot, DichVu.service_code);
				item.service_price = Conts.getStringCursor(cursorDVHot, DichVu.service_price);
				item.isDangky = "0".equals(Conts.getStringCursor(cursorDVHot, DichVu.service_status));
				item.index = index;

				if (index == 0) {
					index = 1;
				} else {
					index = 0;
				}
				list.add(item);
			}

			cursorDVHot.close();
		}
	}
}
