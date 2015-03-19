package vnp.com.mimusic.view;

import vnp.com.db.datastore.AccountStore;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

//vnp.com.mimusic.view.CoverAvatarView
public class CoverAvatarView extends LinearLayout {

	public CoverAvatarView(Context context) {
		super(context);
		init();
	}

	public CoverAvatarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.coveravatar, this);
	}

	public void showData() {
		ImageView menu_left_img_avatar = (ImageView) findViewById(R.id.menu_left_img_avatar);
		ImageView menu_left_img_cover = (ImageView) findViewById(R.id.menu_left_img_cover);
		TextView menu_left_tv_name = (TextView) findViewById(R.id.menu_left_tv_name);

		// Cursor cursor =
		// getContext().getContentResolver().query(User.CONTENT_URI, null,
		// String.format("%s = '1'", User.STATUS), null, null);
		// if (cursor != null && cursor.getCount() >= 1) {
		// if (cursor.moveToNext()) {
		AccountStore accountStore = new AccountStore(getContext());
		menu_left_tv_name.setText(String.format("%s (%s)"
				, accountStore.getStringInFor(AccountStore.fullname)
				, Conts.getSDT(accountStore.getUser())));

		String cover = accountStore.getStringInFor(AccountStore.cover);
		Conts.displayImageCover(cover, menu_left_img_cover);
		String avatar = accountStore.getStringInFor(AccountStore.avatar);
		Conts.showInforAvatar(avatar, menu_left_img_avatar);
		// }
		// }

		// if (cursor != null) {
		// cursor.close();
		// }
	}
}