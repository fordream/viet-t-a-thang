package vnp.com.mimusic.view;

import vnp.com.db.User;
import vnp.com.db.datastore.AccountStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MenuLeftAdaper;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MenuLeftView extends LinearLayout {
	private AccountStore accountStore;

	public MenuLeftView(Context context) {
		super(context);
		init();
	}

	public MenuLeftView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	private void init() {
		accountStore = new AccountStore(getContext());
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_left, this);
		findViewById(R.id.xleft).setOnClickListener(null);
		findViewById(R.id.menu_left_header).setOnClickListener(null);
		findViewById(R.id.menu_left_footer).setOnClickListener(null);
		ListView menu_left_list = (ListView) findViewById(R.id.menu_left_list);
		String menuleft_list[] = getResources().getStringArray(R.array.menuleft_list);

		menu_left_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mOnItemClickListener.onItemClick(parent, view, position, id);
			}
		});

		menu_left_list.setAdapter(new MenuLeftAdaper(getContext(), menuleft_list));
	}

	public void showData() {
		ImageView menu_left_img_avatar = (ImageView) findViewById(R.id.menu_left_img_avatar);
		ImageView menu_left_img_cover = (ImageView) findViewById(R.id.menu_left_img_cover);
		TextView menu_left_tv_name = (TextView) findViewById(R.id.menu_left_tv_name);
		menu_left_tv_name.setText(String.format("%s (%s)"
				, accountStore.getStringInFor(AccountStore.fullname)
				, Conts.getSDT(accountStore.getUser())));
		String cover = accountStore.getStringInFor(AccountStore.cover);
		Conts.displayImageCover(cover, menu_left_img_cover);
		String avatar = accountStore.getStringInFor(AccountStore.avatar);
		Conts.showInforAvatar(avatar, menu_left_img_avatar);
	}
}
