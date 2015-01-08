package vnp.com.mimusic.view;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.mimusic.R;
import android.content.Context;
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

	//id: của định danh của BXH
    //nickname(String): nickname của dealer
    //msisdn: số điện thoại của dealer
    //avatar: ảnh đại diện của dealer
    //quantity: số lượng đăng ký
    //commission: hoa hồng được nhận (doanh thu)
	public void setData(JSONObject item) {
		TextView bxhItemSTT = (TextView) findViewById(R.id.bangxephang_item_tv_stt);
		ImageView bxhItemImage = (ImageView) findViewById(R.id.bangxephang_item_img_icon);
		TextView bxhItemName = (TextView) findViewById(R.id.bangxephang_item_tv_name);
		TextView bxhItemValue = (TextView) findViewById(R.id.bangxephang_item);
		
		try {
			bxhItemSTT.setText(item.getString("position"));
			bxhItemName.setText(item.getString("nickname"));
			if (!item.isNull("type")) {
				if (item.get("type").equals("1")) {
					bxhItemValue.setText(item.getString("commission"));
				} else if (item.get("type").equals("2")) {
					bxhItemValue.setText(item.getString("quantity"));
				}
			}
		} catch (JSONException e) {
		}

	}

}
