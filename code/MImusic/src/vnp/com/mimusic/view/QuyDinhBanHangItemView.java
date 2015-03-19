package vnp.com.mimusic.view;

import vnp.com.db.datastore.DichVuStore;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

//vnp.com.mimusic.view.DichVuItemView
public class QuyDinhBanHangItemView extends LinearLayout {
	public QuyDinhBanHangItemView(Context context) {
		super(context);
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.quydinhbanhang_item, this);

	}

	public QuyDinhBanHangItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.quydinhbanhang_item, this);
	}

	public void setData(Cursor cursor) {
		TextView home_item_tv_name = (TextView) findViewById(R.id.home_item_tv_name);
		TextView home_item_tv_name_dv = (TextView) findViewById(R.id.home_item_tv_name_dv);
		TextView home_item_tv_content = (TextView) findViewById(R.id.home_item_tv_content);

		home_item_tv_name.setText("");
		home_item_tv_content.setText("");
		home_item_tv_name.setText(String.format(getContext().getString(R.string.formatdieukhoan), (cursor.getPosition() + 1)));
		home_item_tv_name_dv.setText(cursor.getString(cursor.getColumnIndex(DichVuStore.service_name)));
		home_item_tv_content.setText(cursor.getString(cursor.getColumnIndex(DichVuStore.service_content)));
	}

}