package vnp.com.mimusic.adapter;

import vnp.com.mimusic.view.QuyDinhBanHangItemView;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

import com.viettel.vtt.vdealer.R;

public class QuyDinhBanHangAdapter extends CursorAdapter {

	public QuyDinhBanHangAdapter(Context context, Cursor c) {
		super(context, c, true);
	}

	@Override
	public void bindView(View convertView, Context context, Cursor cursor) {
		if (convertView == null) {
			convertView = new QuyDinhBanHangItemView(context);
		}
		convertView.findViewById(R.id.quydinhbanhang_item_main).setBackgroundColor(context.getResources().getColor(cursor.getPosition() % 2 == 0 ? android.R.color.white : R.color.f3f3f3));
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		return new QuyDinhBanHangItemView(context);
	}

}
