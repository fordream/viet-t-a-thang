package vnp.com.mimusic.view;

import java.util.ArrayList;
import java.util.List;

import vnp.com.mimusic.adapter.MenuRightDetailAdaper;
import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.viettel.vtt.vdealer.R;

//vnp.com.mimusic.view.MenuRightDetailView
public class MenuRightDetailView extends LinearLayout {

	public MenuRightDetailView(Context context) {
		super(context);
		init();
	}

	public MenuRightDetailView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.menuright_detail, this);
		setOnClickListener(null);
		ListView menu_right_list = (ListView) findViewById(R.id.menu_right_detail_list);
		View header = new MenuRightItemView(getContext());
		header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				close();
			}

		});
		menu_right_list.addHeaderView(header);
		initData();
	}

	public void close() {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_right_out);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				visibility(View.GONE);
			}
		});
		MenuRightDetailView.this.startAnimation(animation);
	}

	@Override
	public void startAnimation(Animation animation) {
		// super.startAnimation(animation);

		findViewById(R.id.menu_right_detail_main).startAnimation(animation);
	}

	protected void visibility(int gone) {
		setVisibility(gone);
	}

	public void initData() {
		final EditText menu_right_editext = (EditText) findViewById(R.id.menu_right_detail_editext);
		ImageView menu_right_img_search = (ImageView) findViewById(R.id.menu_right_detail_img_search);

		ListView menu_right_list = (ListView) findViewById(R.id.menu_right_detail_list);

		final List<ContentValues> list = new ArrayList<ContentValues>();

		for (int i = 0; i < 100; i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("check", false);
			contentValues.put("name", "Dịch vụ Imusiz " + i);
			contentValues.put("link", "http://imusiz.vn/" + i);
			list.add(contentValues);
		}
		menu_right_list.setAdapter(new MenuRightDetailAdaper(getContext(), list));
		menu_right_img_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String txt = menu_right_editext.getText().toString();
			}
		});
	}

}
