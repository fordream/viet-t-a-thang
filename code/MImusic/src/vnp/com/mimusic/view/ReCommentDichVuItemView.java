package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

//vnp.com.mimusic.view.ReCommentDichVuItemView
public class ReCommentDichVuItemView extends LinearLayout {

	public ReCommentDichVuItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ReCommentDichVuItemView(Context context) {
		super(context);
		init();
	}

	public void setData(Cursor cursor) {
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.icon);

		Conts.getView(this, R.id.left).setVisibility(View.GONE);
		home_item_img_icon.setBackgroundResource(R.drawable.new_home_dv_bg_1);
		home_item_img_icon.setImageResource(R.drawable.tranfer);
		Conts.setTextView(findViewById(R.id.name), "");

		if (cursor != null) {
			String service_icon = cursor.getString(cursor.getColumnIndex(DichVuStore.service_icon)) + "";

			Conts.showLogoDichvu(home_item_img_icon, service_icon);
			try {
				Conts.setTextViewCursor(findViewById(R.id.name), cursor, DichVuStore.service_name);
			} catch (Exception exception) {
			}
			int poistion = cursor.getPosition();

			Conts.getView(this, R.id.left).setVisibility(poistion == 0 ? View.VISIBLE : View.GONE);
		}

	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_gallery_item, this);
	}

	public void setData(JSONObject item, int position) {
		ImageView icon = (ImageView) findViewById(R.id.icon);

		Conts.getView(this, R.id.left).setVisibility(View.GONE);
		// icon.setBackgroundResource(R.drawable.new_home_dv_bg_1);
		icon.setImageResource(R.drawable.tranfer);
		Conts.setTextView(findViewById(R.id.name), "");

		String service_icon = Conts.getString(item, DichVuStore.service_icon);
		Conts.showLogoDichvu(icon, service_icon);
		try {
			Conts.setTextView(findViewById(R.id.name), item, DichVuStore.service_name);
		} catch (Exception exception) {

		}
		Conts.getView(this, R.id.left).setVisibility(position == 0 ? View.VISIBLE : View.GONE);
	}
}
