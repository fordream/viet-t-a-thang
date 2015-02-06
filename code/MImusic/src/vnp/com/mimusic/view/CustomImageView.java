package vnp.com.mimusic.view;

import vnp.com.mimusic.util.Conts;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

//vnp.com.mimusic.view.CustomImageView
public class CustomImageView extends ImageView {

	public static float radius = 90f;

	public CustomImageView(Context context) {
		super(context);
	}

	public CustomImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
//		try {
//			super.setImageBitmap(Conts.getRoundedCornerBitmap(bm));
//		} catch (Exception exception) {
//		}
	}
}
