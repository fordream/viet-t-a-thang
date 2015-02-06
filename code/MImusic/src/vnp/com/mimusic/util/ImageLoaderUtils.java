package vnp.com.mimusic.util;

import vnp.com.mimusic.R;
import android.content.Context;
import android.widget.ImageView;

public class ImageLoaderUtils {
	private ImageLoader imageLoader;
	public static ImageLoaderUtils instance;

	public static ImageLoaderUtils getInstance(Context context) {
		return (instance == null ? (instance = new ImageLoaderUtils()) : instance).init(context);
	}

	private ImageLoaderUtils init(Context context) {
		if (imageLoader == null) {
			imageLoader = new ImageLoader(context);
		}
		imageLoader.updateContext(context);
		return this;
	}

	private ImageLoaderUtils() {

	}

	public void displayImageInfor(String url, ImageView imageView) {

		imageView.setImageResource(R.drawable.new_no_avatar);

		if (!Conts.isBlank(url)) {
			imageLoader.DisplayImage(url, imageView);
		}
	}

	public void displayImageCover(String url, ImageView imageView) {
		if (!Conts.isBlank(url)) {
			imageLoader.DisplayImage(url, imageView);
		}
	}

	public void displayImage(String url, ImageView imageView, int res) {

		if (res != 0) {
			imageView.setImageResource(res);
		}

		if (!Conts.isBlank(url)) {
			imageLoader.DisplayImage(url, imageView);
		}
	}

}