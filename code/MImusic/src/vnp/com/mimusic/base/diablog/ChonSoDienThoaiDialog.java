package vnp.com.mimusic.base.diablog;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.vnp.core.view.wheel.WheelView;
import com.vnp.core.view.wheel.WheelViewAdapter;

public abstract class ChonSoDienThoaiDialog extends BaseAdialog implements android.view.View.OnClickListener {

	private String id = "";

	public ChonSoDienThoaiDialog(Context context, String date) {
		super(context);
		this.id = date;
	}

	private WheelView date_wheelview_year;
	final List<ContentValues> contentValues = new ArrayList<ContentValues>();

	private void callSHowData() {
		String where = String.format("%s = '0'", User.STATUS);
		Cursor cursor = getContext().getContentResolver().query(User.CONTENT_URI, null, where, null, null);

		ContentValues values = new ContentValues();
		values.put(User.NAME_CONTACT, getContext().getResources().getString(R.string.tatca));
		values.put(User.USER, "");
		values.put(User.AVATAR, "");
		contentValues.add(values);

		int index = 0;

		while (cursor != null && cursor.moveToNext()) {
			ContentValues _values = new ContentValues();
			_values.put(User.USER, cursor.getString(cursor.getColumnIndex(User.USER)));
			_values.put(User.NAME_CONTACT, cursor.getString(cursor.getColumnIndex(User.NAME_CONTACT)));
			_values.put(User.AVATAR, cursor.getString(cursor.getColumnIndex(User.AVATAR)));
			contentValues.add(_values);

			if (id.equals(cursor.getString(cursor.getColumnIndex(User.USER)))) {
				index = contentValues.size() - 1;
			}
		}

		if (cursor != null)
			cursor.close();

		// WheelViewAdapter adapterWheel = new WheelViewAdapter() {
		//
		// @Override
		// public void unregisterDataSetObserver(DataSetObserver observer) {
		//
		// }
		//
		// @Override
		// public void registerDataSetObserver(DataSetObserver observer) {
		//
		// }
		//
		// @Override
		// public int getItemsCount() {
		// return contentValues.size();
		// }
		//
		// @Override
		// public View getItem(int index, View convertView, ViewGroup parent) {
		//
		// if (convertView == null) {
		// convertView = ((LayoutInflater)
		// parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chondichvu,
		// null);
		// }
		// ImageView imageView = (ImageView)
		// convertView.findViewById(R.id.imageView1);
		// TextView date_title = (TextView)
		// convertView.findViewById(R.id.date_title);
		// ContentValues values = contentValues.get(index);
		// date_title.setText(values.getAsString(User.NAME_CONTACT));
		//
		// String id = values.getAsString(User.USER);
		//
		// if (Conts.isBlank(id)) {
		// imageView.setImageBitmap(null);
		// } else {
		// Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(),
		// R.drawable.no_avatar);
		// ImageLoaderUtils.getInstance(parent.getContext()).DisplayImage(values.getAsString(User.AVATAR),
		// imageView, bitmap);
		// }
		// return convertView;
		// }
		//
		// @Override
		// public View getEmptyItem(View convertView, ViewGroup parent) {
		// return null;
		// }
		// };

		date_wheelview_year.setViewAdapter(adapter);
		date_wheelview_year.setCurrentItem(index);
	}

	private MAdapter adapter = new MAdapter();

	private class MAdapter implements WheelViewAdapter {
		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {

		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {

		}

		@Override
		public int getItemsCount() {
			return contentValues.size();
		}

		@Override
		public View getItem(int index, View convertView, ViewGroup parent) {

			if (convertView == null) {
				convertView = ((LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.chondichvu, null);
			}
			ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView1);
			TextView date_title = (TextView) convertView.findViewById(R.id.date_title);
			ContentValues values = contentValues.get(index);
			date_title.setText(values.getAsString(User.NAME_CONTACT));

			String id = values.getAsString(User.USER);

			if (Conts.isBlank(id)) {
				imageView.setImageBitmap(null);
			} else {
				Bitmap bitmap = BitmapFactory.decodeResource(parent.getResources(), R.drawable.no_avatar);
				ImageLoaderUtils.getInstance(parent.getContext()).DisplayImage(values.getAsString(User.AVATAR), imageView, bitmap);
			}
			return convertView;
		}

		@Override
		public View getEmptyItem(View convertView, ViewGroup parent) {
			return null;
		}

		public void setText(String string) {
			// TODO Auto-generated method stub
			
		}
	}

	private EditText menu_right_editext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		menu_right_editext = (EditText) findViewById(R.id.menu_right_editext);
		HeaderView chitietdichvu_headerview = (HeaderView) findViewById(R.id.chitietdichvu_headerview);
		chitietdichvu_headerview.setTextHeader(R.string.chonsdt);
		chitietdichvu_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitietdichvu_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);
		chitietdichvu_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDismiss(false);
			}
		});

		date_wheelview_year = (WheelView) findViewById(R.id.date_wheelview_year);

		findViewById(R.id.date_dialog_main).startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_in_bottom));
		findViewById(R.id.date_close).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(false);
			}
		});

		findViewById(R.id.date_done).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mDismiss(true);
			}
		});

		callSHowData();

		menu_right_editext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				adapter.setText(menu_right_editext.getText().toString());
			}
		});
	}

	public void mDismiss(final boolean isSendData) {
		Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.abc_slide_out_bottom);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				ChonSoDienThoaiDialog.this.dismiss();
				if (isSendData)
					sendData(contentValues.get(date_wheelview_year.getCurrentItem()));
			}
		});
		findViewById(R.id.date_dialog_main).startAnimation(animation);

	}

	public abstract void sendData(ContentValues index);

	@Override
	public int getLayout() {
		return R.layout.choncontact_dialog;
	}

	@Override
	public void onClick(View v) {
	}

}