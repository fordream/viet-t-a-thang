package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MenuLeftAdaper;
import android.content.Context;
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
		ImageView menu_left_img_avatar = (ImageView) findViewById(R.id.menu_left_img_avatar);
		TextView menu_left_tv_name = (TextView) findViewById(R.id.menu_left_tv_name);
	}
}
