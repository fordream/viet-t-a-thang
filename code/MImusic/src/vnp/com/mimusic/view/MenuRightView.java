package vnp.com.mimusic.view;

import vnp.com.db.VasContact;
import vnp.com.mimusic.adapter.MenuRightAdaper;
import vnp.com.mimusic.util.Conts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.viettel.vtt.vdealer.R;

public class MenuRightView extends LinearLayout {
	private MenuRightItemView header;

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
		findViewById(R.id.xleft).setOnClickListener(null);
		findViewById(R.id.menu_right_search).setOnClickListener(null);

		header = new MenuRightItemView(getContext());
		header.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String number = header.getNumber();

				if (handler != null) {
					Message message = new Message();
					message.what = 0;
					message.obj = number;
					handler.sendMessage(message);
				}
			}
		});
		menu_right_list = (com.woozzu.android.widget.IndexableListView) findViewById(R.id.menu_right_list);
		menu_right_list.addFooterView(header);
		header.show(false, "");
	}

	com.woozzu.android.widget.IndexableListView menu_right_list;
	private MenuRightAdaper adaper;

	public void initData() {
		final EditText menu_right_editext = (EditText) findViewById(R.id.menu_right_editext);

		menu_right_list.setOnTouchListener(onTouchListener);
		if (adaper == null || adaper != null && adaper.getCount() == 0) {

			Cursor cursor = VasContact.queryContactSearch(getContext(), "");
			if (adaper == null) {
				adaper = new MenuRightAdaper(getContext(), cursor) {
					@Override
					public void openMoi(ContentValues contentValues) {

					}

					@Override
					public void changeCursor(Cursor cursor) {
						super.changeCursor(cursor);
						if (cursor != null) {
							if (cursor.getCount() == 0) {
								header.show(true, getTextSearch());
							} else {
								header.show(false, "");
							}
						}
					}
				};
				menu_right_list.setAdapter(adaper);
				adaper.setTextSearch(menu_right_editext.getText().toString().trim());
			} else {
				adaper.changeCursor(cursor);
				if (cursor != null && cursor.moveToNext()) {
					header.show(false, "");
				}

			}
		} else {
			header.show(false, "");
		}

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
				// adaper.notifyDataSetChanged();
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

	private OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final EditText menu_right_editext = (EditText) findViewById(R.id.menu_right_editext);

			Conts.hiddenKeyBoard(menu_right_editext);
			return false;
		}
	};
	private Handler handler;

	public void setHandler(Handler handler) {
		this.handler = handler;
	}
}
