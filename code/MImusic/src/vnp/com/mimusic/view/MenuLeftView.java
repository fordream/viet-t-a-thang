package vnp.com.mimusic.view;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MenuLeftAdaper;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MenuLeftView extends LinearLayout {

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
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_left, this);

		ListView menu_left_list = (ListView) findViewById(R.id.menu_left_list);
		String menuleft_list[] = getResources().getStringArray(R.array.menuleft_list);

		menu_left_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mOnItemClickListener.onItemClick(parent, view, position, id);
			}
		});

		menu_left_list.setAdapter(new MenuLeftAdaper(getContext(), menuleft_list));
		// ImageView menu_left_img_avatar = (ImageView)
		// findViewById(R.id.menu_left_img_avatar);
		// TextView menu_left_tv_name = (TextView)
		// findViewById(R.id.menu_left_tv_name);
	}

	public void showData() {
		ImageView menu_left_img_avatar = (ImageView) findViewById(R.id.menu_left_img_avatar);
		ImageView menu_left_img_cover = (ImageView) findViewById(R.id.menu_left_img_cover);
		TextView menu_left_tv_name = (TextView) findViewById(R.id.menu_left_tv_name);

		Cursor cursor = getContext().getContentResolver().query(User.CONTENT_URI, null, String.format("%s = '1'", User.STATUS), null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			menu_left_tv_name.setText(String.format("%s(%s)", Conts.getName(cursor), cursor.getString(cursor.getColumnIndex(User.USER))));

			String cover = cursor.getString(cursor.getColumnIndex(User.COVER));

			Conts.showImage(cover, menu_left_img_cover, 0);
			// if (!Conts.isBlank(cover)) {
			// BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// Bitmap bitmap = BitmapFactory.decodeFile(cover, options);
			// menu_left_img_cover.setImageBitmap(bitmap);
			// }
			String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
			Conts.showImage(avatar, menu_left_img_avatar, R.drawable.no_avatar);

			// if (!Conts.isBlank(avatar)) {
			// BitmapFactory.Options options = new BitmapFactory.Options();
			// options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			// Bitmap bitmap = BitmapFactory.decodeFile(avatar, options);
			// menu_left_img_avatar.setImageBitmap(bitmap);
			// } else {
			// menu_left_img_avatar.setImageBitmap(BitmapFactory.decodeResource(getResources(),
			// R.drawable.no_avatar));
			//
			// }
			cursor.close();
		}
	}
}
