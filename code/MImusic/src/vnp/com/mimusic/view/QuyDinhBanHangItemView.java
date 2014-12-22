package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

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

}