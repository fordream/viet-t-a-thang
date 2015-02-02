package vnp.com.mimusic.view;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MenuRightAdaper;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//vnp.com.mimusic.view.MenuRightView
public class MenuRightView extends LinearLayout {
	// private vnp.com.mimusic.view.MenuRightDetailView menu_right_detail_view;

	public MenuRightView(Context context) {
		super(context);
		init();
	}

	public MenuRightView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menu_right, this);
	}

	private MenuRightAdaper adaper;

	public void initData() {
		final EditText menu_right_editext = (EditText) findViewById(R.id.menu_right_editext);
		ImageView menu_right_img_search = (ImageView) findViewById(R.id.menu_right_img_search);
		com.woozzu.android.widget.IndexableListView menu_right_list = (com.woozzu.android.widget.IndexableListView) findViewById(R.id.menu_right_list);
		
		String where = String.format("%s = '0'", User.STATUS);
		Cursor cursor = getContext().getContentResolver().query(User.CONTENT_URI, null, where, null, User.NAME_CONTACT);
		adaper = new MenuRightAdaper(getContext(), cursor) {
			@Override
			public void openMoi(ContentValues contentValues) {

			}
		};
		adaper.setTextSearch(menu_right_editext.getText().toString().trim());
		menu_right_list.setAdapter(adaper);
		menu_right_editext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				adaper.setTextSearch(menu_right_editext.getText().toString().trim());
				//adaper.notifyDataSetChanged();
			}
		});

		menu_right_list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mOnItemClickListener.onItemClick(arg0, arg1, arg2, arg3);
			}
		});
	}

	public boolean needBack() {
		// if (menu_right_detail_view.getVisibility() == View.VISIBLE) {
		// menu_right_detail_view.close();
		// return true;
		// } else {
		return false;
		// }

	}
}
