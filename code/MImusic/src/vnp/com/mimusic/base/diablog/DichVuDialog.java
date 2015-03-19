package vnp.com.mimusic.base.diablog;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.base.BaseAdialog;
import vnp.com.mimusic.util.Conts;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;
import com.vnp.core.view.wheel.WheelView;
import com.vnp.core.view.wheel.WheelViewAdapter;

public abstract class DichVuDialog extends BaseAdialog implements android.view.View.OnClickListener {

	private String id = "";

	public DichVuDialog(Context context, String date) {
		super(context);
		this.id = date;
	}

	private WheelView date_wheelview_year;
	final List<ContentValues> contentValues = new ArrayList<ContentValues>();

	private void callSHowData() {
		// Cursor cursor =
		// getContext().getContentResolver().query(DichVuStore.CONTENT_URI,
		// null, null, null, null);
		Cursor cursor = null;
		ContentValues values = new ContentValues();
		values.put(DichVuStore.service_name, getContext().getResources().getString(R.string.tatca));
		values.put(DichVuStore.service_icon, "");
		values.put(DichVuStore.ID, "");
		contentValues.add(values);

		int index = 0;

		while (cursor != null && cursor.moveToNext()) {
			ContentValues _values = new ContentValues();
			_values.put(DichVuStore.service_name, cursor.getString(cursor.getColumnIndex(DichVuStore.service_name)));
			_values.put(DichVuStore.service_icon, cursor.getString(cursor.getColumnIndex(DichVuStore.service_icon)));
			_values.put(DichVuStore.ID, cursor.getString(cursor.getColumnIndex(DichVuStore.ID)));
			contentValues.add(_values);

			if (id.equals(cursor.getString(cursor.getColumnIndex(DichVuStore.ID)))) {
				index = contentValues.size() - 1;
			}
		}

		if (cursor != null)
			cursor.close();
		WheelViewAdapter adapterWheel = new WheelViewAdapter() {

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
				date_title.setText(values.getAsString(DichVuStore.service_name));

				String id = values.getAsString(DichVuStore.ID);

				if (Conts.isBlank(id)) {
					imageView.setImageBitmap(null);
				} else {
					// ImageLoaderUtils.getInstance(parent.getContext()).displayImage(values.getAsString(DichVu.service_icon),
					// imageView, R.drawable.no_image);
				}
				Conts.showLogoDichvu(imageView, values.getAsString(DichVuStore.service_icon));
				return convertView;
			}

			@Override
			public View getEmptyItem(View convertView, ViewGroup parent) {
				return null;
			}
		};

		date_wheelview_year.setViewAdapter(adapterWheel);
		date_wheelview_year.setCurrentItem(index);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				DichVuDialog.this.dismiss();
				if (isSendData)
					sendData(contentValues.get(date_wheelview_year.getCurrentItem()));
			}
		});
		findViewById(R.id.date_dialog_main).startAnimation(animation);

	}

	public abstract void sendData(ContentValues index);

	@Override
	public int getLayout() {
		return R.layout.dichvu_dialog;
	}

	@Override
	public void onClick(View v) {
	}

}