package vnp.com.mimusic.view;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.BangXepHangItemView
public class BangXepHangItemView extends LinearLayout {

	public BangXepHangItemView(Context context) {
		super(context);
		init();
	}

	public BangXepHangItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bangxephang_item, this);
	}

	// id: của định danh của BXH
	// nickname(String): nickname của dealer
	// msisdn: số điện thoại của dealer
	// avatar: ảnh đại diện của dealer
	// quantity: số lượng đăng ký
	// commission: hoa hồng được nhận (doanh thu)
	public void setData(JSONObject item) {
		TextView bxhItemSTT = (TextView) findViewById(R.id.bangxephang_item_tv_stt);
		ImageView bxhItemImage = (ImageView) findViewById(R.id.bangxephang_item_img_icon);
		TextView bxhItemName = (TextView) findViewById(R.id.bangxephang_item_tv_name);
		TextView bxhItemValue = (TextView) findViewById(R.id.bangxephang_item);

		bxhItemSTT.setText("" + (Integer.parseInt(Conts.getString(item, "position")) + 1));
		bxhItemName.setText(Conts.getString(item, "nickname"));
		if ("1".equals(Conts.getString(item, "type"))) {
			bxhItemValue.setText(Conts.getString(item, "commission"));
		} else {
			bxhItemValue.setText(Conts.getString(item, "quantity"));
		}

		ImageLoaderUtils.getInstance(getContext()).DisplayImage(Conts.getString(item, "avatar"), bxhItemImage, BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar));
	}

	public void setDataColor(int position) {
		findViewById(R.id.bangxephang_item_tv_stt).setBackgroundColor(getResources().getColor(Conts.getDataColor(position)));
		TextView bxhItemSTT = (TextView) findViewById(R.id.bangxephang_item_tv_stt);
		bxhItemSTT.setTextColor(Color.WHITE);
		if (position >= 6) {
			bxhItemSTT.setTextColor(getResources().getColor(R.color.mx));
		}
	}

	public void setDataBundle(Bundle arguments) {
		setDataColor(Integer.parseInt(arguments.getString("position")));
		TextView bxhItemSTT = (TextView) findViewById(R.id.bangxephang_item_tv_stt);
		TextView bxhItemName = (TextView) findViewById(R.id.bangxephang_item_tv_name);
		bxhItemSTT.setText("" + (Integer.parseInt(arguments.getString("position")) + 1));
		bxhItemName.setText(arguments.getString("nickname"));
		Conts.setTextView(findViewById(R.id.chitietsoluong1dichvu), arguments.getString("quantity"));
		Conts.setTextView(findViewById(R.id.chitietdoanhthu1dichvu), arguments.getString("commission"));
		ImageView bxhItemImage = (ImageView) findViewById(R.id.bangxephang_item_img_icon);
		ImageLoaderUtils.getInstance(getContext()).DisplayImage(arguments.getString("avatar"), bxhItemImage, BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar));

		
		
		((TextView)findViewById(R.id.chitietdoanhthu1dichvu)).setText(String.format(getContext().getString(R.string.format_tien),arguments.getString("commission")));
	}
}
