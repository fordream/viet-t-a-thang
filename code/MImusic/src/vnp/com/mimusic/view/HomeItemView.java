package vnp.com.mimusic.view;

import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import android.content.ContentValues;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HomeItemView extends LinearLayout {
	public HomeItemView(Context context) {
		super(context);
		init();
	}

	public HomeItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_item, this);
	}

	public void setData(final ContentValues contentValues) {
		ImageView home_item_img_icon = (ImageView) findViewById(R.id.home_item_img_icon);
		ImageView home_item_right_control_1 = (ImageView) findViewById(R.id.home_item_right_control_1);
		ImageView home_item_right_control_2 = (ImageView) findViewById(R.id.home_item_right_control_2);

		TextView home_item_tv_name = (TextView) findViewById(R.id.home_item_tv_name);
		TextView home_item_tv_link = (TextView) findViewById(R.id.home_item_tv_link);
		TextView home_item_tv_content = (TextView) findViewById(R.id.home_item_tv_content);

		home_item_img_icon.setBackgroundResource(R.drawable.icon_imusiz);

		home_item_tv_name.setText(contentValues.getAsString("name"));
		home_item_tv_link.setText(contentValues.getAsString("link"));
		home_item_tv_content.setText(contentValues.getAsString("content"));

		home_item_right_control_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				DangKyDialog dangKyDialog = new DangKyDialog(v.getContext(), contentValues);
				dangKyDialog.show();
			}
		});

		home_item_right_control_2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
	}

}