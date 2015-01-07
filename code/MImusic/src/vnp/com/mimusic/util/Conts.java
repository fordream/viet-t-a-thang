package vnp.com.mimusic.util;

import java.util.Set;

import org.json.JSONObject;

import vnp.com.api.ExeCallBack;
import vnp.com.api.ExeCallBackOption;
import vnp.com.api.ResClientCallBack;
import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

public class Conts {

	public final static String SERVER = "http://125.235.40.85/api.php/";
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
		String name = cursor.getString(cursor.getColumnIndex(User.fullname));

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

	public static void showImage(String path, ImageView img, int no_image) {
		Bitmap bitmap = null;
		LogUtils.e("PATH1", path + "");
		if (path != null && path.contains("file://")) {
			path = path.substring(path.indexOf("file://") + 7, path.length());
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;
			bitmap = BitmapFactory.decodeFile(path, options);

		}

		if (path != null && path.contains("content://")) {
			try {
				bitmap = MediaStore.Images.Media.getBitmap(img.getContext().getContentResolver(), Uri.parse(path));
			} catch (Exception e) {
			}
		}

		if (bitmap == null && no_image != 0) {
			bitmap = BitmapFactory.decodeResource(img.getContext().getResources(), no_image);
		}

		if (bitmap != null) {
			img.setImageBitmap(bitmap);
		}
	}

	public static String getToken(Context activity) {
		String token = null;
		String selection = User.STATUS + "='1'";
		Cursor cursor = activity.getContentResolver().query(User.CONTENT_URI, null, selection, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			token = cursor.getString(cursor.getColumnIndex(User.TOKEN));
		}

		if (cursor != null) {
			cursor.close();
		}
		return token;
	}

	public static void executeNoProgressBar(final RequestMethod requestMethod, final String api, final Context activity, Bundle bundles, final IContsCallBack contsCallBack) {
		contsCallBack.onStart();
		ResClientCallBack resClientCallBack = new ResClientCallBack() {

			@Override
			public String getApiName() {
				return api;
			}

			@Override
			public void onCallBack(Object object) {
				super.onCallBack(object);

				RestClient restClient = (RestClient) object;
				try {
					LogUtils.e("restClient.getResponse()", restClient.getResponse());
					JSONObject jsonObject = new JSONObject(restClient.getResponse());
					String errorCode = jsonObject.getString("errorCode");
					String message = jsonObject.getString("message");
					if ("0".equals(errorCode)) {
						contsCallBack.onSuscess(jsonObject);
					} else {
						try {
							JSONObject errorMessage = jsonObject.getJSONObject("errorMessage");
							java.util.Iterator<String> iterator = errorMessage.keys();
							while (iterator.hasNext()) {
								String key = iterator.next();
								message += "\n" + errorMessage.getString(key);
							}
						} catch (Exception exception) {

						}
						contsCallBack.onError(message);
					}
				} catch (Exception exception) {
					contsCallBack.onError();
				}

			}

			@Override
			public RequestMethod getMedthod() {
				return requestMethod;
			}
		};

		resClientCallBack.addParam("token", Conts.getToken(activity));

		Set<String> keys = bundles.keySet();
		for (String key : keys) {
			resClientCallBack.addParam(key, bundles.getString(key));
		}
		ExeCallBack exeCallBack = new ExeCallBack();
		exeCallBack.setExeCallBackOption(new ExeCallBackOption(activity, false, R.string.loading, null));
		exeCallBack.executeAsynCallBack(resClientCallBack);
	}

	public interface IContsCallBack {
		public void onStart();

		public void onError();

		public void onError(String message);

		public void onSuscess(JSONObject response);
	}

	public static void toast(Context activity, String message) {
		Toast.makeText(activity, message + "", Toast.LENGTH_SHORT).show();
	}

	public static void hiddenKeyBoard(Activity context) {
		try {
			InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
			IBinder binder = context.getCurrentFocus().getWindowToken();
			imm.hideSoftInputFromWindow(binder, 0);
		} catch (Exception e) {
		}
	}

	public static void disableView(View view[]) {
		for (View v : view) {
			v.setEnabled(false);
		}
	}

	public static void enableView(View view[]) {
		for (View v : view) {
			v.setEnabled(true);
		}
	}

	public static void showView(View progressBar1, boolean b) {
		progressBar1.setVisibility(b ? View.VISIBLE : View.GONE);
	}

	public static boolean is3GConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// For 3G check
		return manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
	}

	public static boolean isWifiConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
	}
}