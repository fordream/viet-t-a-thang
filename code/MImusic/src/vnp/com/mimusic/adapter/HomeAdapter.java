package vnp.com.mimusic.adapter;

import java.util.List;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.view.DichVuItemView;
import vnp.com.mimusic.view.HomeItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;

public abstract class HomeAdapter extends CursorAdapter {
	public HomeAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new HomeItemView(context);
	}

	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new HomeItemView(context);
		}

		((HomeItemView) convertView).setDataCusor(cursor, cursor.getPosition());
		
		final ContentValues values = new ContentValues();
		values.put("name", cursor.getString(cursor.getColumnIndex(DichVu.service_name)));
		values.put(DichVu.service_code, cursor.getString(cursor.getColumnIndex(DichVu.service_code)));
		values.put("content", cursor.getString(cursor.getColumnIndex(DichVu.service_content)));
		values.put(DichVu.ID, cursor.getString(cursor.getColumnIndex(DichVu.ID)));
		values.put("type", "dangky");

		convertView.findViewById(R.id.home_item_right_control_1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DangKyDialog dangKyDialog = new DangKyDialog(v.getContext(), values) {
					@Override
					public void updateUiDangKy() {
						super.updateUiDangKy();
						updateUI();
					}
				};
				dangKyDialog.show();
			}
		});

		convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				moiDVChoNhieuNguoi(values);
			}
		});
	}

	// @Override
	// public View getView(final int position, View convertView, ViewGroup
	// parent) {
	// if (convertView == null) {
	// convertView = new HomeItemView(parent.getContext());
	// }
	//
	// ((HomeItemView) convertView).setDataCusor(getItem(position), position);
	//
	// convertView.findViewById(R.id.home_item_right_control_1).setOnClickListener(new
	// OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// DangKyDialog dangKyDialog = new DangKyDialog(v.getContext(),
	// contentValues) {
	// @Override
	// public void updateUiDangKy() {
	// super.updateUiDangKy();
	// updateUI();
	// }
	// };
	// dangKyDialog.show();
	// }
	// });
	//
	// convertView.findViewById(R.id.home_item_right_control_2).setOnClickListener(new
	// OnClickListener() {
	//
	// @Override
	// public void onClick(View v) {
	// moiDVChoNhieuNguoi(contentValues);
	// }
	// });
	//
	// return convertView;
	// }

	public abstract void updateUI();

	public abstract void moiDVChoNhieuNguoi(ContentValues contentValues);

}
