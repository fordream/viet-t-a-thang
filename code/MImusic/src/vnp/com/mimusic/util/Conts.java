package vnp.com.mimusic.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.Normalizer;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

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
import vnp.com.db.datastore.AccountStore;
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
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Conts {
	public static final class StringConnvert {
		public static final String convertVNToAlpha(String str) {
			if (isBlank(str)) {
				return str;
			}
			try {
				String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
				Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
				// D D d d
				return pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replaceAll("đ", "d").toUpperCase();
			} catch (Exception exception) {

				return str;
			}
		}

		private static boolean isBlank(String str) {
			return str == null || (str != null && str.trim().equals(""));
		}
	}

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
			cursor.close();
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

	public static void executeNoProgressBar(final RequestMethod requestMethod, final String api, final MImusicService activity, final Bundle bundles, final IContsCallBack contsCallBack) {
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

					// if (API.API_R015.equals(api)) {
					// Set<String> keys = bundles.keySet();
					// }

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
						}

						if ("440".equals(errorCode) || "-1".equals(errorCode)) {
							message = activity.getString(R.string.error_network);
						}

						if (contsCallBack != null) {
							if (Conts.isBlank(message)) {
								message = activity.getString(R.string.error_network);
							}
							contsCallBack.onError(message);
						}
					}
				} catch (Exception exception) {
					if (contsCallBack != null) {
						contsCallBack.onError(activity.getString(R.string.error_network));
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

		if (!API.API_R013.equals(resClientCallBack.getApiName()) && !API.API_R001.equals(resClientCallBack.getApiName())) {
			bundles.putString("token", new AccountStore(activity).getToken());
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

		// public void onError();

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

	public static void hiddenKeyBoard(EditText edt) {
		InputMethodManager imm = (InputMethodManager) edt.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
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

			contentValues.put("typeThongBao", true);

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

	public static boolean xDontains(String con, boolean isSdt, String[] full) {
		if (Conts.isBlank(con) || full == null) {
			return true;
		}

		if (isSdt) {
			if (con.startsWith("0") && con.length() >= 1) {
				con = con.substring(1, con.length());
			}
		}

		for (int i = 0; i < full.length; i++) {
			String str = full[i];

			if (str.toLowerCase().trim().contains(con.toLowerCase().trim())) {
				return true;
			}
		}

		return false;
	}

	private static char[] SPECIAL_CHARACTERS = { ' ', '!', '"', '#', '$', '%', '*', '+', ',', ':', '<', '=', '>', '?', '@', '[', '\\', ']', '^', '`', '|', '~', 'À', 'Á', 'Â', 'Ã', 'È', 'É', 'Ê', 'Ì',
			'Í', 'Ò', 'Ó', 'Ô', 'Õ', 'Ù', 'Ú', 'Ý', 'à', 'á', 'â', 'ã', 'è', 'é', 'ê', 'ì', 'í', 'ò', 'ó', 'ô', 'õ', 'ù', 'ú', 'ý', 'Ă', 'ă', 'Đ', 'đ', 'Ĩ', 'ĩ', 'Ũ', 'ũ', 'Ơ', 'ơ', 'Ư', 'ư', 'Ạ',
			'ạ', 'Ả', 'ả', 'Ấ', 'ấ', 'Ầ', 'ầ', 'Ẩ', 'ẩ', 'Ẫ', 'ẫ', 'Ậ', 'ậ', 'Ắ', 'ắ', 'Ằ', 'ằ', 'Ẳ', 'ẳ', 'Ẵ', 'ẵ', 'Ặ', 'ặ', 'Ẹ', 'ẹ', 'Ẻ', 'ẻ', 'Ẽ', 'ẽ', 'Ế', 'ế', 'Ề', 'ề', 'Ể', 'ể', 'Ễ', 'ễ',
			'Ệ', 'ệ', 'Ỉ', 'ỉ', 'Ị', 'ị', 'Ọ', 'ọ', 'Ỏ', 'ỏ', 'Ố', 'ố', 'Ồ', 'ồ', 'Ổ', 'ổ', 'Ỗ', 'ỗ', 'Ộ', 'ộ', 'Ớ', 'ớ', 'Ờ', 'ờ', 'Ở', 'ở', 'Ỡ', 'ỡ', 'Ợ', 'ợ', 'Ụ', 'ụ', 'Ủ', 'ủ', 'Ứ', 'ứ', 'Ừ',
			'ừ', 'Ử', 'ử', 'Ữ', 'ữ', 'Ự', 'ự', };

	private static char[] REPLACEMENTS = { '-', '\0', '\0', '\0', '\0', '\0', '\0', '_', '\0', '_', '\0', '\0', '\0', '\0', '\0', '\0', '_', '\0', '\0', '\0', '\0', '\0', 'A', 'A', 'A', 'A', 'E',
			'E', 'E', 'I', 'I', 'O', 'O', 'O', 'O', 'U', 'U', 'Y', 'a', 'a', 'a', 'a', 'e', 'e', 'e', 'i', 'i', 'o', 'o', 'o', 'o', 'u', 'u', 'y', 'A', 'a', 'D', 'd', 'I', 'i', 'U', 'u', 'O', 'o',
			'U', 'u', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'A', 'a', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E', 'e', 'E',
			'e', 'E', 'e', 'E', 'e', 'I', 'i', 'I', 'i', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'O', 'o', 'U', 'u', 'U', 'u',
			'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', 'U', 'u', };

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
				bitmap = Conts.decodeFile(new File(url));
			} else {
				bitmap = Conts.decodeFile(new File(url));
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the
																	// bitmap
																	// object
			byte[] b = baos.toByteArray();
			return Base64.encodeToString(b, Base64.DEFAULT);
		} catch (Exception e) {
			return "";
		}
	}

	// decodes image and scales it to reduce memory consumption
	public static Bitmap decodeFile(File f) {
		try {
			// decode image size
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			FileInputStream stream1 = new FileInputStream(f);
			BitmapFactory.decodeStream(stream1, null, o);
			stream1.close();

			// Find the correct scale value. It should be the power of 2.
			final int REQUIRED_SIZE = 480;
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < REQUIRED_SIZE | height_tmp / 2 < REQUIRED_SIZE)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}

			// decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			FileInputStream stream2 = new FileInputStream(f);
			Bitmap bitmap = BitmapFactory.decodeStream(stream2, null, o2);
			stream2.close();

			return bitmap;
		} catch (Exception e) {
		}
		return null;
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

	public static Bitmap getBitmapFromContactId(Context context, String contactId, File yourFile) {
		Bitmap bitmap = null;
		try {
			Uri contactUri = ContentUris.withAppendedId(Contacts.CONTENT_URI, Long.parseLong(contactId));
			Uri photoUri = Uri.withAppendedPath(contactUri, Contacts.Photo.CONTENT_DIRECTORY);
			Cursor cursor = context.getContentResolver().query(photoUri, new String[] { Contacts.Photo.PHOTO }, null, null, null);
			// File yourFile = new
			// File(Environment.getExternalStorageDirectory(),
			// System.currentTimeMillis() + "");
			if (cursor.moveToFirst()) {
				byte[] data = cursor.getBlob(0);
				if (data != null) {
					BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(yourFile));
					bos.write(data);
					bos.flush();
					bos.close();
				}
			}

			bitmap = decodeFile(yourFile);
			if (cursor != null) {
				cursor.close();
			}
		} catch (Exception exception) {

		}
		return bitmap;
	}

	// public static void showAvatar(ImageView menu_right_item_img_icon, String
	// avatar, String contact_id) {
	// if (Conts.isBlank(avatar)) {
	// if (Conts.isBlank(contact_id)) {
	// menu_right_item_img_icon.setImageResource(R.drawable.no_avatar);
	// } else {
	// Bitmap bitmap =
	// Conts.getBitmapFromContactId(menu_right_item_img_icon.getContext(),
	// contact_id, f);
	// if (bitmap == null) {
	// menu_right_item_img_icon.setImageResource(R.drawable.no_avatar);
	// } else {
	// menu_right_item_img_icon.setImageBitmap(bitmap);
	// }
	// }
	// } else {
	// ImageLoaderUtils.getInstance(menu_right_item_img_icon.getContext()).DisplayImage(avatar,
	// menu_right_item_img_icon, R.drawable.no_avatar);
	// }
	// }

	public static void setTextResource(View view, int res) {
		if (view != null && view instanceof TextView) {
			((TextView) view).setText(res);
		}
	}

	public static <T extends View> T getView(View view, int id) {
		T v = (T) view.findViewById(id);
		return v;
	}

	public static void showKeyBoard(EditText keEditText) {
		keEditText.requestFocus();
		InputMethodManager imm = (InputMethodManager) keEditText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		// imm.hideSoftInputFromWindow(keEditText.getWindowToken(), 0);
		imm.showSoftInput(keEditText, InputMethodManager.SHOW_IMPLICIT);

	}

	public static void showAlpha(View view, boolean isHidden) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 1f);

		if (isHidden) {
			alphaAnimation = new AlphaAnimation(0.6f, 0.6f);
		}
		alphaAnimation.setFillAfter(true);
		view.setAnimation(alphaAnimation);
	}

	public static int getDateToInt(String str) {
		try {
			StringTokenizer stringTokenizer = new StringTokenizer(str, "/");
			String day = stringTokenizer.nextToken();
			String month = stringTokenizer.nextToken();
			String year = stringTokenizer.nextToken();
			return Integer.parseInt(year) * 10000 + Integer.parseInt(month) * 100 + Integer.parseInt(day) * 1;
		} catch (Exception exception) {

		}

		return 0;
	}

	/**
	 * avatar
	 * 
	 * @return
	 */
	public static int[] resavatar() {
		int resavatar[] = new int[] { R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3, R.drawable.avatar_4, R.drawable.avatar_5 };
		return resavatar;
	}

	public static void showInforAvatar(String path, ImageView img) {
		ImageLoaderUtils.getInstance(img.getContext()).displayImageInfor(path, img);
	}

	public static void displayImageCover(String path, ImageView img) {
		ImageLoaderUtils.getInstance(img.getContext()).displayImageCover(path, img);
	}

	public static void showAvatarContact(final ImageView menu_right_item_img_icon, final String avatar, final String contact_id, int resAvatar) {
		ImageLoaderUtils.getInstance(menu_right_item_img_icon.getContext()).showAvatarContact(menu_right_item_img_icon, avatar, contact_id, resAvatar);
	}

	public static void showLogoDichvu(final ImageView icon, final String avatar) {

		// LogUtils.e("showLogoDichvu", avatar);
		ImageLoaderUtils.getInstance(icon.getContext()).showLogoDichvu(icon, avatar);
	}

	public static void showLogoTinTuc(ImageView tintuc_item_img_icon, String images) {
		ImageLoaderUtils.getInstance(tintuc_item_img_icon.getContext()).showLogoTinTuc(tintuc_item_img_icon, images);
	}

	public static File cacheDir(Context activity) {
		String state = Environment.getExternalStorageState();
		File cacheDir = null;
		String path = "Android/data/" + activity.getPackageName() + "/LazyList";

		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), path);
		} else {
			cacheDir = activity.getCacheDir();
		}
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}

		return cacheDir;

	}

}