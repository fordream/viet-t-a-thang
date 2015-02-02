package vnp.com.mimusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageLoaderUtils {
	private ImageLoader imageLoader;
	public static ImageLoaderUtils instance;

	public static ImageLoaderUtils getInstance(Context context) {
		return (instance == null ? (instance = new ImageLoaderUtils()) : instance).init(context);
	}

	private ImageLoaderUtils init(Context context) {
		if (imageLoader == null)
			imageLoader = new ImageLoader(context);
		imageLoader.updateContext(context);
		return this;
	}

	private ImageLoaderUtils() {

	}

	//
	// public void DisplayImage(String url, ImageView imageView) {
	// imageView.setImageBitmap(null);
	// if (!Conts.isBlank(url)) {
	// // url = url.replace("htpps", "htpp");
	// }
	// if (!Conts.isBlank(url)) {
	// // url = url.replace("htpps", "htpp");
	// imageLoader.DisplayImage(url, imageView);
	// }
	// }

	public void DisplayImageXX(String url, ImageView imageView, Bitmap bitmap) {
		if (bitmap != null)
			imageView.setImageBitmap(bitmap);
		if (!Conts.isBlank(url)) {
			imageLoader.DisplayImage(url, imageView);
		}

	}

	public void DisplayImage(String url, ImageView imageView, int bitmap) {
		if (bitmap != 0)
			imageView.setImageResource(bitmap);
		if (!Conts.isBlank(url)) {
			imageLoader.DisplayImage(url, imageView);
		}

	}
}