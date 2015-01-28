package vnp.com.mimusic.view;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

		Cursor cursor = getContext().getContentResolver().query(User.CONTENT_URI, null, String.format("%s = '1'", User.STATUS), null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			if (cursor.moveToNext()) {

				menu_left_tv_name.setText(String.format("%s (%s)", Conts.getName(cursor), Conts.getSDT(cursor.getString(cursor.getColumnIndex(User.USER)))));

				String cover = cursor.getString(cursor.getColumnIndex(User.COVER));
				Conts.showImage(cover, menu_left_img_cover, 0);
				String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
				Conts.showImage(avatar, menu_left_img_avatar, R.drawable.no_avatar);
			}
		}

		if (cursor != null) {
			cursor.close();
		}
	}
}