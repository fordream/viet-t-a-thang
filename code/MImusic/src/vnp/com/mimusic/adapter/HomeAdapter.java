package vnp.com.mimusic.adapter;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HomeItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.viettel.vtt.vdealer.R;

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
		values.put("name",

		String.format(context.getString(R.string.title_dangky), cursor.getString(cursor.getColumnIndex(DichVuStore.service_name)))

		);
		values.put(DichVuStore.service_code, cursor.getString(cursor.getColumnIndex(DichVuStore.service_code)));

		String content = String.format(context.getString(R.string.xacnhandangky_form), Conts.getStringCursor(cursor, DichVuStore.service_name),
				Conts.getStringCursor(cursor, DichVuStore.service_price));
		// values.put("content",
		// cursor.getString(cursor.getColumnIndex(DichVuStore.service_content)));
		values.put("content", content);
		values.put(DichVuStore.ID, cursor.getString(cursor.getColumnIndex(DichVuStore.ID)));
		values.put("type", "dangky");
		final boolean isDangKy = "0".equals(cursor.getString(cursor.getColumnIndex(DichVuStore.service_status)));

		convertView.findViewById(R.id.home_item_right_control_1).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (!isDangKy) {

					DangKyDialog dangKyDialog = new DangKyDialog(v.getContext(), values) {
						@Override
						public void updateUiDangKy() {
							super.updateUiDangKy();
							updateUI();
						}
					};
					dangKyDialog.show();
				}
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
