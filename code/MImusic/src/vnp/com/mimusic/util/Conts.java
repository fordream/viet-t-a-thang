package vnp.com.mimusic.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
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
import vnp.com.db.DataStore;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentUris;
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
import android.provider.ContactsContract.Contacts;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Conts {
	public final static String SERVER = "https://125.235.40.85/api.php/";
	// :443
	public final static String SERVERS = "https://125.235.40.85/api.php/";
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
	public static final String CHONDICHVU = "CHONDICHVU";
	public static final String DICHVUHOT = "DICHVUHOT";
	public static final String MOITHANHVIEN = "MOITHANHVIEN";
	public static final String DICHVUBANCHAY = "DICHVUBANCHAY";

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
			token = getStringCursor(cursor, User.TOKEN);
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
					if ("0".equals(errorCode) || "0000".equals(errorCode)) {
						if (contsCallBack != null)
							contsCallBack.onSuscess(jsonObject);
					} else {
						try {
							if (jsonObject.has("errorMessage")) {
								JSONObject errorMessage = jsonObject.getJSONObject("errorMessage");
								java.util.Iterator<String> iterator = errorMessage.keys();
								while (iterator.hasNext()) {
									String key = iterator.next();
									message += "\n" + errorMessage.getString(key);
								}
							}
						} catch (Exception exception) {
							LogUtils.e("response", exception);
						}

						try {
							if (jsonObject.has("customers_fail")) {
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
							}
						} catch (Exception exception) {
							LogUtils.e("response", exception);
						}
						if (contsCallBack != null) {
							contsCallBack.onError(message);
						}
					}
				} catch (Exception exception) {
					LogUtils.e("response", exception);
					if (contsCallBack != null) {
						contsCallBack.onError();
					}
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

	public interface IContsCallBackData {
		public void onCallBack(Object response);
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
		return manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
	}

	public static boolean isWifiConnected(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		return manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
	}

	public static boolean isVietTelNUmber(String moidichvuchonhieunguoi_numberText, Context context) {

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
			String str = jsonObject.getString(string);

			if ("null".equals(str)) {
				str = "";
			}
			return str;
		} catch (JSONException e) {
			return "";
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

				// LogUtils.e("CHECK", e);
			}
		}

		return new File(path);
	}

	public static boolean contains(String full, String con) {
		try {
			return full.toLowerCase().trim().contains(con.toLowerCase().trim());
		} catch (Exception exception) {
			return true;
		}
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

	public static boolean isTablet(Context context) {
		// return "1".equals(context.getString(R.string.checkdevice));
		return false;
	}

	public static void setTextView(View findViewById, JSONObject jsonObject, String key) {
		try {
			((TextView) findViewById).setText(getString(jsonObject, key));
		} catch (Exception exception) {

		}
	}

	public static String getStringCursor(Cursor cursor, String key) {
		try {
			return cursor.getString(cursor.getColumnIndex(key));
		} catch (Exception exception) {
			return "";
		}
	}

	public static void setTextViewCursor(View findViewById, Cursor cursor, String key) {
		try {
			((TextView) findViewById).setText(cursor.getString(cursor.getColumnIndex(key)));
		} catch (Exception exception) {

		}
	}

	public static void getPhoto(String photo_id) {

	}

	public static int getDataColor(int position) {
		int res[] = new int[] { R.color.bangxephang0, R.color.bangxephang1, R.color.bangxephang2, R.color.bangxephang3, R.color.bangxephang4, R.color.bangxephang5 };

		if (position < res.length) {
			return res[position];
		} else if (position % 2 == 0) {
			return R.color.bangxephang6;
		}

		return R.color.bangxephang7;
	}

	public static void setTextView(View findViewById, String key) {
		try {
			((TextView) findViewById).setText(key);
		} catch (Exception exception) {

		}
	}

	public static void saveHttpsToken(Context context, String token) {
		DataStore.getInstance().init(context);
		DataStore.getInstance().save("httpsToken", token);
	}

	public static String getHttpsToken(Context context) {
		DataStore.getInstance().init(context);
		return DataStore.getInstance().get("httpsToken", "");
	}

	public static String encodeToString(Context context, String url) {
		try {
			Bitmap bitmap = null;
			if (url != null && url.startsWith("content://")) {
				try {
					bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), Uri.parse(url));
				} catch (Exception e) {

				}
			} else if (url != null && url.startsWith("file://")) {
				url = url.substring(url.indexOf("file://") + 7, url.length());
				bitmap = ImageLoader.decodeFile(new File(url));
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the
																	// bitmap
																	// object
			byte[] b = baos.toByteArray();
			return Base64.encodeToString(b, Base64.DEFAULT);
		} catch (Exception e) {
			return "";
			// TODO: handle exception
		}
	}

	public static void getSDT(View textView) {
		if (textView instanceof TextView) {
			String sdt = ((TextView) textView).getText().toString();

			if (!Conts.isBlank(sdt) && sdt.startsWith("84")) {
				((TextView) textView).setText("0" + sdt.substring(2, sdt.length()));
			}
		}
	}

	public static String getSDT(String string) {
		if (!Conts.isBlank(string) && string.startsWith("84")) {
			return "0" + string.substring(2, string.length());
		}
		return string;
	}

	public static Bitmap getBitmapFromContactId(Context context, String contactId) {
		Bitmap bitmap = null;
		try {
			Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(contactId));
			Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
			Cursor cursor = context.getContentResolver().query(photoUri, new String[] { Contacts.Photo.PHOTO }, null, null, null);
			File yourFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + "");
			if (cursor.moveToFirst()) {
				byte[] data = cursor.getBlob(0);
				if (data != null) {
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(yourFile));
					bos.write(data);
					bos.flush();
					bos.close();
				}
			}

			bitmap = ImageLoader.decodeFile(yourFile);
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception exception) {

		}
		return bitmap;
	}

	public static void showAvatar(ImageView menu_right_item_img_icon, String avatar, String contact_id) {
		if (Conts.isBlank(avatar)) {
			if (Conts.isBlank(contact_id)) {
				menu_right_item_img_icon.setImageResource(R.drawable.no_avatar);
			} else {
				Bitmap bitmap = Conts.getBitmapFromContactId(menu_right_item_img_icon.getContext(), contact_id);
				if (bitmap == null) {
					menu_right_item_img_icon.setImageResource(R.drawable.no_avatar);
				} else {
					menu_right_item_img_icon.setImageBitmap(bitmap);
				}
			}
		} else {
			ImageLoaderUtils.getInstance(menu_right_item_img_icon.getContext()).DisplayImage(avatar, menu_right_item_img_icon,
					BitmapFactory.decodeResource(menu_right_item_img_icon.getContext().getResources(), R.drawable.no_avatar));
		}
	}

	public static void showAvatarNoImage(ImageView menu_right_item_img_icon, String avatar, String contact_id) {
		if (Conts.isBlank(avatar)) {
			if (Conts.isBlank(contact_id)) {
				menu_right_item_img_icon.setImageResource(R.drawable.no_image);
			} else {
				Bitmap bitmap = Conts.getBitmapFromContactId(menu_right_item_img_icon.getContext(), contact_id);
				if (bitmap == null) {
					menu_right_item_img_icon.setImageResource(R.drawable.no_image);
				} else {
					menu_right_item_img_icon.setImageBitmap(bitmap);
				}
			}
		} else {
			ImageLoaderUtils.getInstance(menu_right_item_img_icon.getContext()).DisplayImage(avatar, menu_right_item_img_icon,
					BitmapFactory.decodeResource(menu_right_item_img_icon.getContext().getResources(), R.drawable.no_image));
		}
	}

	public static void setTextResource(View view, int res) {
		if (view != null && view instanceof TextView) {
			((TextView) view).setText(res);
		}
	}

	public static <T extends View> T getView(View view, int id) {
		T v = (T) view.findViewById(id);
		return v;
	}

}