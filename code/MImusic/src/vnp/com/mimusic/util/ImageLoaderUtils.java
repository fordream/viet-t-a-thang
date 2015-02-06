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

	public void showAvatarContact(final ImageView menu_right_item_img_icon, final String avatar, final String contact_id, int resAvatar) {

		menu_right_item_img_icon.setImageResource(resAvatar);
		if (Conts.isBlank(avatar)) {
			if (Conts.isBlank(contact_id)) {
			} else {
				imageLoader.DisplayImage(contact_id, menu_right_item_img_icon);
			}
		} else {
			imageLoader.DisplayImage(avatar, menu_right_item_img_icon);
		}
	}

	public void showLogoDichvu(final ImageView menu_right_item_img_icon, final String avatar) {
		menu_right_item_img_icon.setImageResource(R.drawable.no_image);
		imageLoader.DisplayImage(avatar, menu_right_item_img_icon);

	}

	public void showLogoTinTuc(ImageView tintuc_item_img_icon, String images) {
		tintuc_item_img_icon.setImageResource(R.drawable.no_image);
		imageLoader.DisplayImage(images, tintuc_item_img_icon);

	}
	// public void displayImage(String url, ImageView imageView, int res) {
	//
	// if (res != 0) {
	// imageView.setImageResource(res);
	// }
	//
	// if (!Conts.isBlank(url)) {
	// imageLoader.DisplayImage(url, imageView);
	// }
	// }

}