package vnp.com.mimusic.view;

import java.util.ArrayList;
import java.util.List;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MenuRightAdaper;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import android.content.ContentValues;
import android.content.Context;
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
//private vnp.com.mimusic.view.MenuRightDetailView menu_right_detail_view;

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
		//menu_right_detail_view = (vnp.com.mimusic.view.MenuRightDetailView) findViewById(R.id.menu_right_detail_view);
	}

	public void initData() {
		final EditText menu_right_editext = (EditText) findViewById(R.id.menu_right_editext);
		ImageView menu_right_img_search = (ImageView) findViewById(R.id.menu_right_img_search);

		ListView menu_right_list = (ListView) findViewById(R.id.menu_right_list);

		final List<ContentValues> list = new ArrayList<ContentValues>();

		for (int i = 0; i < 100; i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("check", false);
			contentValues.put("name", "Trần Hữu Nhật Minh " + i);
			contentValues.put("link", "imusiz | imusiz | imusiz" + i);
			list.add(contentValues);
		}
		menu_right_list.setAdapter(new MenuRightAdaper(getContext(), list) {
			@Override
			public void openMoi(ContentValues contentValues) {
				//menu_right_detail_view.visibility(View.VISIBLE);
				//menu_right_detail_view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_right_in));
			}
		});

		menu_right_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				mOnItemClickListener.onItemClick(arg0, arg1, arg2, arg3);
			}
		});
		menu_right_img_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt = menu_right_editext.getText().toString();

			}
		});
	}

	public boolean needBack() {
		//if (menu_right_detail_view.getVisibility() == View.VISIBLE) {
		//	menu_right_detail_view.close();
		//	return true;
		//} else {
			return false;
		//}

	}
}
