package vnp.com.mimusic.util;

import vnp.com.db.User;
import vnp.com.mimusic.base.VTAnimationListener;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class Conts {
	public final static String HOME = "home";
	public final static String BANGXEPHANG = "bangxephang";
	public final static String LICHSUBANHANG = "lichsubanhang";
	public final static String QUYDINHBANHANG = "quydinhbanhang";
	public final static String HUONGDANBANHANG = "huongdanbanhang";
	public final static String DICHVU = "dichvu";
	public final static String TIMKIEM = "timkiem";
	public final static String ORTHER = "other";
	public static final String THONGTINCANHAN = "THONGTINCANHAN";
	public static final String TINTUC = "tintuc";

	public static final String NHIEUDICHVU = "NHIEUDICHVU";
	public static final String CHITIETTINTUC = "CHITIETTINTUC";
	public static final String MOIDICHVUCHONHIEUNGUOI = "MOIDICHVUCHONHIEUNGUOI";
	public static final String CHITIETDICHVU = "CHITIETDICHVU";
	public static final String CHITIETCANHANBANGXEPHANG = "CHITIETCANHANBANGXEPHANG";
	public static final String CHITIETCANHANBANGXEPHANGTUNGDICHVU = "CHITIETCANHANBANGXEPHANGTUNGDICHVU";
	public static final String CHITTIETLICHSUBANHANG = "CHITTIETLICHSUBANHANG";

	public static String getName(Cursor cursor) {
		String name = cursor.getString(cursor.getColumnIndex(User.NAME));

		if (name == null || name != null && name.trim().equals("")) {
			name = cursor.getString(cursor.getColumnIndex(User.NAME_CONTACT));
		}

		if (name != null && !name.trim().equals("")) {
			return name;
		}

		return cursor.getString(cursor.getColumnIndex(User.USER));
	}

	public static boolean isBlank(String cover) {
		return cover == null || cover != null && cover.trim().equals("");
	}

	public static String getPath(Context context, Uri uri) {

		if (uri == null) {
			return null;
		}

		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);

		if (cursor != null) {
			int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			String path = cursor.getString(column_index);
			return path == null ? uri.getPath() : path;
		}
		return uri.getPath();
	}

	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
		bitmap = cropSquare(bitmap);
		bitmap = getRoundedShape(bitmap);
		return bitmap;
	}

	public static Bitmap cropSquare(Bitmap bitmap) {
		int value = Math.min(bitmap.getHeight(), bitmap.getWidth());
		return Bitmap.createBitmap(bitmap, (bitmap.getWidth() - value) / 2, (bitmap.getHeight() - value) / 2, value, value);
	}

	public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
		int targetWidth = scaleBitmapImage.getWidth();
		int targetHeight = scaleBitmapImage.getHeight();
		Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);

		Canvas canvas = new Canvas(targetBitmap);

		Path path = new Path();

		path.addCircle(((float) targetWidth - 1) / 2, ((float) targetHeight - 1) / 2, (Math.min(((float) targetWidth), ((float) targetHeight)) / 2), Path.Direction.CCW);

		canvas.clipPath(path);

		Bitmap sourceBitmap = scaleBitmapImage;

		canvas.drawBitmap(sourceBitmap, new Rect(0, 0, sourceBitmap.getWidth(), sourceBitmap.getHeight()), new Rect(0, 0, targetWidth, targetHeight), null);

		return targetBitmap;
	}

	// public static void setOnClickListener(final View view, final
	// View.OnClickListener onClickListener) {
	//
	// view.setOnClickListener(new View.OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0.6f, 1f, 0.6f,
	// v.getWidth() / 2, v.getHeight() / 2);
	// scaleAnimation.setDuration(100);
	// scaleAnimation.setAnimationListener(new VTAnimationListener() {
	// @Override
	// public void onAnimationEnd(Animation animation) {
	// super.onAnimationEnd(animation);
	// onClickListener.onClick(view);
	// }
	// });
	//
	// view.startAnimation(scaleAnimation);
	// }
	// });
	// }
}