package vnp.com.mimusic.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Set;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.ExeCallBack;
import vnp.com.api.ExeCallBackOption;
import vnp.com.api.MImusicService;
import vnp.com.api.ResClientCallBack;
import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
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
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

public class Conts {
	 public final static String SERVER = "http://125.235.40.85/api.php/";
//	public final static String SERVER = "https://125.235.40.85:443/api.php/";
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
	public static final boolean ISLISTCHUASUDUNG = true;
	public static final String CHONSODIENTHOAIFRAGMENT = "CHONSODIENTHOAIFRAGMENT";

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

		if (no_image != 0) {
			bitmap = BitmapFactory.decodeResource(img.getContext().getResources(), no_image);
		}
		ImageLoaderUtils.getInstance(img.getContext()).DisplayImage(path, img, bitmap);

		// Bitmap bitmap = null;
		// if (path != null && path.contains("file://")) {
		// path = path.substring(path.indexOf("file://") + 7, path.length());
		// BitmapFactory.Options options = new BitmapFactory.Options();
		// options.inPreferredConfig = Bitmap.Config.ARGB_8888;
		// bitmap = BitmapFactory.decodeFile(path, options);
		// }
		//
		// if (path != null && path.contains("content://")) {
		// try {
		// bitmap =
		// MediaStore.Images.Media.getBitmap(img.getContext().getContentResolver(),
		// Uri.parse(path));
		// } catch (Exception e) {
		// }
		// }
		//
		// if (bitmap == null && no_image != 0) {
		// bitmap =
		// BitmapFactory.decodeResource(img.getContext().getResources(),
		// no_image);
		// }
		//
		// if (bitmap != null) {
		// img.setImageBitmap(bitmap);
		// }
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

	public static void executeNoProgressBar(final RequestMethod requestMethod, final String api, final MImusicService activity, Bundle bundles, final IContsCallBack contsCallBack) {
		if (contsCallBack != null)
			contsCallBack.onStart();
		ResClientCallBack resClientCallBack = new ResClientCallBack(activity) {

			@Override
			public String getApiName() {
				return api;
			}

			@Override
			public void onCallBack(Object object) {
				super.onCallBack(object);
				String message = "";
				String errorCode = "";
				RestClient restClient = (RestClient) object;
				String response = restClient.getResponse();
				try {

					if (Conts.isBlank(response)) {
						response = activity.getString(R.string.default_error);
					}
					JSONObject jsonObject = new JSONObject(response);
					errorCode = jsonObject.getString("errorCode");
					message = jsonObject.getString("message");
					if ("0".equals(errorCode)) {
						if (contsCallBack != null)
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

						try {
							JSONArray errorMessage = jsonObject.getJSONArray("customers_fail");
							String mNewData = "";
							for (int i = 0; i < errorMessage.length(); i++) {
								String newData = errorMessage.get(i).toString();
								mNewData += "" + newData;
							}

							if (!Conts.isBlank(mNewData)) {
								mNewData = mNewData.replace("{", "").replace("}", "").replace("\"\"", " , ").replace("\"", "");
								message += "\n" + String.format(activity.getString(R.string.danhsachsodienthoaikhongthemoi), mNewData);
							}
						} catch (Exception exception) {

						}
						if (contsCallBack != null)
							contsCallBack.onError(message);
					}
				} catch (Exception exception) {
					if (contsCallBack != null)
						contsCallBack.onError();
				}

				checkToken(restClient.getResponseCode(), errorCode);

			}

			private void checkToken(int responseCode, String errorCode) {

				if (!API.API_R013.equals(getApiName())) {
					if (responseCode == 440 || responseCode == -1 || "440".equals(errorCode) || "-1".equals(errorCode)) {
						activity.refreshToken(null);
					}
				}
			}

			@Override
			public RequestMethod getMedthod() {
				return requestMethod;
			}
		};

		if (!API.API_R013.equals(resClientCallBack.getApiName())) {
			bundles.putString("token", Conts.getToken(activity));
		}

		Set<String> keys = bundles.keySet();
		for (String key : keys) {
			resClientCallBack.addParam(key, bundles.getString(key));
			// LogUtils.e("para", key + " : " + bundles.getString(key));
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
		try {
			Toast.makeText(activity, message + "", Toast.LENGTH_SHORT).show();
		} catch (Exception exception) {

		}
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

	public static boolean isVietTelNUmber(String moidichvuchonhieunguoi_numberText, FragmentActivity activity) {

		while (moidichvuchonhieunguoi_numberText.startsWith("0")) {
			moidichvuchonhieunguoi_numberText = moidichvuchonhieunguoi_numberText.substring(1, moidichvuchonhieunguoi_numberText.length());
		}

		int length = moidichvuchonhieunguoi_numberText.trim().length();
		if (moidichvuchonhieunguoi_numberText.startsWith("84")) {
			return length == 12 || length == 11;
		}

		return length == 9 || length == 10;
	}

	public static void showDialogThongbao(Context context, String message) {
		try {
			ContentValues contentValues = new ContentValues();
			contentValues.put("btn_right", context.getString(R.string.dong));
			contentValues.put("btn_left_close", true);
			contentValues.put("name", context.getString(R.string.thongbao));
			contentValues.put("content", message);
			DangKyDialog dangKyDialog = new DangKyDialog(context, contentValues);
			dangKyDialog.show();
		} catch (Exception exception) {
		}
	}

	public static void showDialogDongYCallBack(Context context, String message, final DialogCallBack dialogCallBack) {
		ContentValues contentValues = new ContentValues();
		contentValues.put("btn_right", context.getString(R.string.dongy));
		contentValues.put("btn_left_close", true);
		contentValues.put("name", context.getString(R.string.thongbao));
		contentValues.put("content", message);
		contentValues.put("type", "x");

		DangKyDialog dangKyDialog = new DangKyDialog(context, contentValues) {
			@Override
			public void mOpen() {
				super.mOpen();
				dismiss();
				dialogCallBack.callback(null);
			}
		};
		dangKyDialog.show();
	}

	public interface DialogCallBack {
		public void callback(Object object);
	}

	public static String getString(JSONObject jsonObject, String string) {
		try {
			return jsonObject.getString(string);
		} catch (JSONException e) {
			return null;
		}
	}

	public static boolean haveContact(String phone, Context context) {
		Cursor cursor = context.getContentResolver().query(User.CONTENT_URI, null, String.format("%s='%s'", User.USER, phone), null, null);

		if (cursor != null && cursor.getCount() > 0) {
			cursor.close();
			return true;
		}

		if (cursor != null) {
			cursor.close();
		}

		return false;
	}

	public static String getUser(Context context) {
		String user = "";
		Cursor cursor = context.getContentResolver().query(User.CONTENT_URI, null, User.STATUS + "='1'", null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			user = cursor.getString(cursor.getColumnIndex(User.USER));
		}

		if (cursor != null) {
			cursor.close();
		}
		return user;
	}

	public static String getPassword(Context context) {
		String user = "";
		Cursor cursor = context.getContentResolver().query(User.CONTENT_URI, null, User.STATUS + "='1'", null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			user = cursor.getString(cursor.getColumnIndex(User.PASSWORD));
		}

		if (cursor != null) {
			cursor.close();
		}
		return user;

	}

	public static String getRefreshToken(Context context) {
		String token = null;
		String selection = User.STATUS + "='1'";
		Cursor cursor = context.getContentResolver().query(User.CONTENT_URI, null, selection, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			token = cursor.getString(cursor.getColumnIndex(User.KEYREFRESH));
		}

		if (cursor != null) {
			cursor.close();
		}
		return token;
	}

	public static void addViewScale(View view) {
		Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.abc_scale_in);
		animation.setDuration(50);
		view.startAnimation(animation);
	}

	public static void removeViewScale(View view, AnimationListener animationListener) {
		Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.abc_scale_out);
		animation.setAnimationListener(animationListener);
		animation.setDuration(50);
		view.startAnimation(animation);

	}

	public static File getFileFromPath(Context context, String path) {

		if (path != null && path.contains("file://")) {
			path = path.substring(path.indexOf("file://") + 7, path.length());
			return new File(path);
		}

		if (path != null && path.contains("content://")) {
			try {
				Bitmap bm = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(Uri.parse(path)));

				File file = new File(Environment.getExternalStorageDirectory() + "/temp.png");
				FileOutputStream fOut = new FileOutputStream(file);
				bm.compress(Bitmap.CompressFormat.PNG, 85, fOut);
				fOut.flush();
				fOut.close();

				return new File(Environment.getExternalStorageDirectory() + "/temp.png");
			} catch (Exception e) {

				LogUtils.e("CHECK", e);
			}
		}

		return new File(path);
	}

	public static boolean contains(String full, String con) {
		return full.toLowerCase().trim().contains(con.toLowerCase().trim());
	}

	public static boolean havenewWork(Context loginActivty) {
		return is3GConnected(loginActivty) || isWifiConnected(loginActivty);
	}

	public interface IShowDateDialog {
		public void onSend(String year, String month, String day);
	}

	public static void showDateDialog(Context activity, int title, String date, final IShowDateDialog showDateDialog) {

		// date day/month/year
		final Dialog dialog = new Dialog(activity, android.R.style.Theme_Holo_Dialog);
		dialog.setContentView(R.layout.date_time_layout);
		final DatePicker datePicker = (DatePicker) dialog.findViewById(R.id.datePicker1);
		dialog.findViewById(R.id.btn1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialog.findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
				showDateDialog.onSend(datePicker.getYear() + "", (datePicker.getMonth() + 1 < 10 ? "0" : "") + (datePicker.getMonth() + 1),
						(datePicker.getDayOfMonth() < 10 ? "0" : "") + datePicker.getDayOfMonth());
			}
		});
		dialog.setTitle(title);
		try {
			StringTokenizer stringTokenizer = new StringTokenizer(date, "/");
			int day = Integer.parseInt(stringTokenizer.nextToken());
			int month = Integer.parseInt(stringTokenizer.nextToken());
			int year = Integer.parseInt(stringTokenizer.nextToken());

			datePicker.updateDate(year, month - 1, day);
		} catch (Exception exception) {

		}
		dialog.show();
	}
}