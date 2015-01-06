package vnp.com.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;

public class MImusicService extends Service {
	public static final class API {
		/**
		 * API_R001: Đăng nhập qua 3G
		 */
		public static final String API_R001 = "authenticate";
		/**
		 * API_R002: Đăng nhập qua wifi
		 */
		public static final String API_R002 = "signin";
		/**
		 * API_R003: Danh sách nội dung dịch vụ
		 */
		public static final String API_R003 = "contentService";
		/**
		 * API_R004: Danh sách dịch vụ tiện ích -
		 */
		public static final String API_R004 = "utilitiServices";
		/**
		 * API_R005: Chi tiết dịch vụ
		 */
		public static final String API_R005 = "utilitiServiceDetail";
		/**
		 * API_R006: Thông tin cá nhân
		 */
		public static final String API_R006 = "userInfor";
		/**
		 * API_R007: Cập nhật thông tin -
		 */
		public static final String API_R007 = "editUserInfor";
		/**
		 * API_R008: Lịch sử giao dịch
		 */
		public static final String API_R008 = "historySale";
		/**
		 * API_R009: Báo cáo chi tiết
		 */
		public static final String API_R009 = "report";
		/**
		 * API_R010: Hướng dẫn bán hàng -
		 */
		public static final String API_R010 = "guideline";
		/**
		 * API_R011: Đồng bộ danh bạ lên
		 */
		public static final String API_R011 = "pushContact";
		/**
		 * API_R012: Đồng bộ danh bạ xuống
		 */
		public static final String API_R012 = "syncContact";
		/**
		 * API_R013: Lấy lại token -
		 */
		public static final String API_R013 = "retoken";
		/**
		 * API_R014: Tặng nội dung
		 */
		public static final String API_R014 = "presentContent";
		/**
		 * API_R015: Mời theo dịch vụ
		 */
		public static final String API_R015 = "inviteService";
		/**
		 * API_R016: Mời theo theo thuê bao
		 */
		public static final String API_R016 = "inviteSubcriber";
		/**
		 * API_R017: Đăng ký dịch vụ
		 */
		public static final String API_R017 = "registryService";
		/**
		 * API_R018: Tải nội dung dịch vụ
		 */
		public static final String API_R018 = "buyContent";
		/**
		 * API_R019: Kiểm tra điều kiện thuê bao
		 */
		public static final String API_R019 = "checkSubscriber";
		/**
		 * API_R020: Kiểm tra điều kiện với nhiều thuê bao
		 */
		public static final String API_R020 = "checkMultiSubscriber";
		/**
		 * API_R021: Mời sử dụng app
		 */
		public static final String API_R021 = "inviteApp";
		/**
		 * API_R022: Danh sách mẫu tin nhắn
		 */
		public static final String API_R022 = "getSMSTemplate";
		/**
		 * API_R023: Thay đổi Avatar
		 */
		public static final String API_R023 = "changeAvatar";
		/**
		 * API_R024: Danh sách BXH
		 */
		public static final String API_R024 = "ranking";
		/**
		 * API_R025: Chi tiết BXH
		 */
		public static final String API_R025 = "ranking";
		/**
		 * API_R026: Dịch vụ gợi ý
		 */
		public static final String API_R026 = "recommend";
		/**
		 * API_R027: Danh sách tin tức
		 */
		public static final String API_R027 = "news";
		/**
		 * API_R028: Chi tiết tin tức
		 */
		public static final String API_R028 = "newsDetail";
		/**
		 * API_R029: Tin tức liên quan
		 */
		public static final String API_R029 = "relatedNews";

	}

	private MImusicBin mImusicBin;
	public static final String ACTION = "vnp.com.api.MImusicService";
	public static final String KEY = "KEY";
	public static final String VALUE = "VALUE";
	public static final String METHOD = "METHOD";

	public MImusicService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mImusicBin = new MImusicBin(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mImusicBin;
	}

	private List<String> listCallBack = new ArrayList<String>();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if ("add".equals(intent.getStringExtra(KEY))) {
			if (!listCallBack.contains(intent.getStringExtra(VALUE))) {
				listCallBack.add(intent.getStringExtra(VALUE));
			}
		} else if ("remove".equals(intent.getStringExtra(KEY))) {
			if (listCallBack.contains(intent.getStringExtra(VALUE))) {
				listCallBack.remove(intent.getStringExtra(VALUE));
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * 
	 * @param is3G
	 * @param u
	 * @param p
	 * @param contsCallBack
	 */
	public void login(boolean is3G, final String u, final String p, final IContsCallBack contsCallBack) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(User.STATUS, "0");
		getContentResolver().update(User.CONTENT_URI, contentValues, null, null);
		Bundle bundle = new Bundle();
		bundle.putString("u", u);
		bundle.putString("p", p);
		Conts.executeNoProgressBar(is3G ? RequestMethod.GET : RequestMethod.POST, is3G ? API.API_R001 : API.API_R002, this, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				contsCallBack.onStart();
			}

			@Override
			public void onSuscess(JSONObject jsonObject) {
				try {
					String token = jsonObject.getString("token");
					String keyRefresh = jsonObject.getString("keyRefresh");
					String phone_number = jsonObject.getString("phone");
					ContentValues values = new ContentValues();
					values.put(User.USER, phone_number);
					values.put(User.PASSWORD, p);
					values.put(User.TOKEN, token);
					values.put(User.KEYREFRESH, keyRefresh);
					values.put(User.STATUS, "1");
					String selection = String.format("%s='%s'", User.USER, phone_number);
					Cursor cursor = getContentResolver().query(User.CONTENT_URI, null, selection, null, null);

					boolean isUpdate = cursor != null && cursor.getCount() >= 1;
					cursor.close();

					if (isUpdate) {
						getContentResolver().update(User.CONTENT_URI, values, selection, null);
					} else {
						getContentResolver().insert(User.CONTENT_URI, values);
					}
				} catch (Exception e) {
				}
				contsCallBack.onSuscess(jsonObject);
			}

			@Override
			public void onError(String message) {
				contsCallBack.onError(message);
			}

			@Override
			public void onError() {
				contsCallBack.onError();
			}
		});
	}

	public void getInfor(final IContsCallBack contsCallBack) {

	}

}