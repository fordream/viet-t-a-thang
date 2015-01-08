package vnp.com.api;

public class API {
	/**
	 * API_R001: Đăng nhập qua 3G
	 */
	public static final String API_R001 = "authenticate"; // OK
	/**
	 * API_R002: Đăng nhập qua wifi
	 */
	public static final String API_R002 = "signin";// OK
	/**
	 * API_R003: Danh sách nội dung dịch vụ
	 */
	public static final String API_R003 = "contentService"; // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	/**
	 * API_R004: Danh sách dịch vụ tiện ích -
	 */
	public static final String API_R004 = "utilitiServices"; // OK
	/**
	 * API_R005: Chi tiết dịch vụ
	 */
	public static final String API_R005 = "utilitiServiceDetail";// OKIE
	/**
	 * API_R006: Thông tin cá nhân
	 */
	public static final String API_R006 = "userInfor"; // OKIE
	/**
	 * API_R007: Cập nhật thông tin -
	 */
	public static final String API_R007 = "editUserInfor"; // OKIE
	/**
	 * API_R008: Lịch sử giao dịch
	 */
	public static final String API_R008 = "historySale"; // OKIE
	/**
	 * API_R009: Báo cáo chi tiết
	 */
	public static final String API_R009 = "report"; // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	/**
	 * API_R010: Hướng dẫn bán hàng -
	 */
	public static final String API_R010 = "guideline"; // 404
														// //xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	/**
	 * API_R011: Đồng bộ danh bạ lên
	 */
	public static final String API_R011 = "pushContact";// OKIE
	/**
	 * API_R012: Đồng bộ danh bạ xuống
	 */
	public static final String API_R012 = "syncContact";// OKIE
	/**
	 * API_R013: Lấy lại token -
	 */
	public static final String API_R013 = "retoken";// OKIE
	/**
	 * API_R014: Tặng nội dung
	 */
	public static final String API_R014 = "presentContent"; // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	/**
	 * API_R015: Mời theo dịch vụ
	 */
	public static final String API_R015 = "inviteService"; // OKIE
	/**
	 * API_R016: Mời theo theo thuê bao
	 */
	public static final String API_R016 = "inviteSubcriber"; // OKIE
	/**
	 * API_R017: Đăng ký dịch vụ
	 */
	public static final String API_R017 = "registryService"; // OKIE
	/**
	 * API_R018: Tải nội dung dịch vụ
	 */
	public static final String API_R018 = "buyContent"; // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
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
	public static final String API_R021 = "inviteApp"; // xxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
	/**
	 * API_R022: Danh sách mẫu tin nhắn
	 */
	public static final String API_R022 = "getSMSTemplate"; // OKIE
	/**
	 * API_R023: Thay đổi Avatar
	 */
	public static final String API_R023 = "changeAvatar";// 405
	/**
	 * API_R024: Danh sách BXH
	 */
	public static final String API_R024 = "ranking"; // 405
	/**
	 * API_R025: Chi tiết BXH
	 */
	public static final String API_R025 = "ranking";// 405
	/**
	 * API_R026: Dịch vụ gợi ý
	 */
	public static final String API_R026 = "recommend";// 405
	/**
	 * API_R027: Danh sách tin tức
	 */
	public static final String API_R027 = "news";// 405
	/**
	 * API_R028: Chi tiết tin tức
	 */
	public static final String API_R028 = "newsDetail";// 405
	/**
	 * API_R029: Tin tức liên quan
	 */
	public static final String API_R029 = "relatedNews";// 405
}
