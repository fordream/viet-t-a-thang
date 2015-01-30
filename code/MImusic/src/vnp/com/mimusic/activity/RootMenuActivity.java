/**
 * 
 */
package vnp.com.mimusic.activity;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.DialogCallBack;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.AdapterView;

import com.aretha.slidemenudemo.fragment.BangXepHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanBangXepHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanBangXepHangTungDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietListSuBanHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietTintucFragment;
import com.aretha.slidemenudemo.fragment.ChonDichvuFragment;
import com.aretha.slidemenudemo.fragment.ChonSoDienThoaiFragment;
import com.aretha.slidemenudemo.fragment.DichVuBanChayFragment;
import com.aretha.slidemenudemo.fragment.DichVuFragment;
import com.aretha.slidemenudemo.fragment.DichVuHotFragment;
import com.aretha.slidemenudemo.fragment.GuiDvChoNhieuNguoiFragment;
import com.aretha.slidemenudemo.fragment.HomeFragment;
import com.aretha.slidemenudemo.fragment.HuongDanBanHangFragment;
import com.aretha.slidemenudemo.fragment.HuongDanBanHangOfDichVuFragment;
import com.aretha.slidemenudemo.fragment.InforFragment;
import com.aretha.slidemenudemo.fragment.LichSuBanHangFragment;
import com.aretha.slidemenudemo.fragment.MauMoiFragment;
import com.aretha.slidemenudemo.fragment.MoiDvChoNhieuNguoiFragment;
import com.aretha.slidemenudemo.fragment.MoiNhieuDichVuFragment;
import com.aretha.slidemenudemo.fragment.MoiThanhVienFragment;
import com.aretha.slidemenudemo.fragment.QuyDinhBanHangFragment;
import com.aretha.slidemenudemo.fragment.SearchFragment;
import com.aretha.slidemenudemo.fragment.ThongTinCaNhanFragment;
import com.aretha.slidemenudemo.fragment.TinTucFragment;
import com.vnp.core.crash.CrashExceptionHandler;

/**
 * class base for all activity
 * 
 * @author tvuong1pc
 * 
 */
public class RootMenuActivity extends FragmentActivity {
	public static final int DIALOG_LOGIN_FAIL = 1000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		CrashExceptionHandler.onCreate(this);
		setContentView(R.layout.root_main);
		String type = getIntent().getStringExtra("type");

		if (Conts.HOME.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new HomeFragment());
		} else if (Conts.BANGXEPHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new BangXepHangFragment());
		} else if (Conts.DICHVU.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new DichVuFragment());
		} else if (Conts.HUONGDANBANHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new HuongDanBanHangFragment());
		} else if (Conts.LICHSUBANHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new LichSuBanHangFragment());
		} else if (Conts.ORTHER.equals(type)) {
			//
		} else if (Conts.QUYDINHBANHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new QuyDinhBanHangFragment());
		} else if (Conts.TIMKIEM.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new SearchFragment());
		} else if (Conts.THONGTINCANHAN.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ThongTinCaNhanFragment());
		} else if (Conts.TINTUC.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new TinTucFragment());
		} else if (Conts.NHIEUDICHVU.equals(type)) {
			String _id = getIntent().getStringExtra(User._ID);
			Bundle bundle = new Bundle();
			bundle.putString(User._ID, _id);
			bundle.putString("msisdn", getIntent().getStringExtra("msisdn"));
			bundle.putString("name", getIntent().getStringExtra("name"));
			MoiNhieuDichVuFragment moiNhieuDichVuFragment = new MoiNhieuDichVuFragment();
			moiNhieuDichVuFragment.setArguments(bundle);
			changeFragemt(R.id.root_main_fragment, moiNhieuDichVuFragment);
		} else if (Conts.CHITIETTINTUC.equals(type)) {
			ChiTietTintucFragment chiTietTintucFragment = new ChiTietTintucFragment();
			Bundle args = new Bundle();
			// args.putString("id", getIntent().getStringExtra("id") + "");
			args.putString("news_id", getIntent().getStringExtra("news_id") + "");
			chiTietTintucFragment.setArguments(args);
			changeFragemt(R.id.root_main_fragment, chiTietTintucFragment);
		} else if (Conts.MOIDICHVUCHONHIEUNGUOI.equals(type)) {
			MoiDvChoNhieuNguoiFragment chiTietTintucFragment = new MoiDvChoNhieuNguoiFragment();
			Bundle args = new Bundle();
			args.putString("id", getIntent().getStringExtra("id") + "");
			chiTietTintucFragment.setArguments(args);
			changeFragemt(R.id.root_main_fragment, chiTietTintucFragment);
		} else if (Conts.CHITIETDICHVU.equals(type)) {
			ChiTietDichVuFragment chiTietTintucFragment = new ChiTietDichVuFragment();
			Bundle args = new Bundle();
			args.putString("id", getIntent().getStringExtra(DichVu.ID) + "");
			if (getIntent().hasExtra(DichVu.service_code))
				args.putString(DichVu.service_code, getIntent().getStringExtra(DichVu.service_code) + "");
			chiTietTintucFragment.setArguments(args);
			changeFragemt(R.id.root_main_fragment, chiTietTintucFragment);
		} else if (Conts.CHITIETCANHANBANGXEPHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanBangXepHangFragment());
		} else if (Conts.CHITIETCANHANBANGXEPHANGTUNGDICHVU.equals(type)) {
			ChiTietCaNhanBangXepHangTungDichVuFragment chiTietCaNhanBangXepHangTungDichVuFragment = new ChiTietCaNhanBangXepHangTungDichVuFragment();
			chiTietCaNhanBangXepHangTungDichVuFragment.setArguments(getIntent().getExtras());
			changeFragemt(R.id.root_main_fragment, chiTietCaNhanBangXepHangTungDichVuFragment);
		} else if (Conts.CHITTIETLICHSUBANHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChiTietListSuBanHangFragment());
		} else if (Conts.CHONSODIENTHOAIFRAGMENT.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChonSoDienThoaiFragment());
		} else if (Conts.CHONDICHVU.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChonDichvuFragment());
		} else if (Conts.DICHVUHOT.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new DichVuHotFragment());
		} else if (Conts.DICHVUBANCHAY.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new DichVuBanChayFragment());
		} else if (Conts.MOITHANHVIEN.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new MoiThanhVienFragment());
		}
	}

	/**
	 * convert view from resource
	 * 
	 * @param res
	 * @return
	 */
	public <T extends View> T getView(int res) {
		@SuppressWarnings("unchecked")
		T view = (T) findViewById(res);
		return view;
	}

	protected Context getContext() {
		return this;
	}

	protected Activity getActivity() {
		return this;
	}

	@Override
	public void onBackPressed() {
		Conts.hiddenKeyBoard(getActivity());
		sendBroadcast(new Intent("updateprofile"));
		FragmentManager fragmentManager = getSupportFragmentManager();
		int count = fragmentManager.getBackStackEntryCount();

		if (count > 1) {
			fragmentManager.popBackStack();
		} else if (count == 1) {
			List<Fragment> fragments = fragmentManager.getFragments();

//			if (fragments.get(fragments.size() - 1) instanceof BangXepHangFragment) {
//				if (((BangXepHangFragment) fragments.get(fragments.size() - 1)).onBackPressed()) {
//					return;
//				}
//			}
			try {
				((vnp.com.mimusic.main.NewMusicSlideMenuActivity) getParent()).finish(true);
			} catch (Exception exception) {
				finish();
				overridePendingTransition(R.anim.abc_nothing, R.anim.abc_scale_out);
			}
		} else {
			super.onBackPressed();
		}
	}

	public void changeFragemt(int res, Fragment fragment) {
		changeFragemt(res, fragment, false);
	}

	public void changeFragemt(int res, Fragment fragment, boolean haveAnimation) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

		if (haveAnimation) {
			transaction.setCustomAnimations(R.anim.abc_scale_in, R.anim.abc_scale_in, R.anim.abc_scale_out, R.anim.abc_scale_out);
		}
		transaction.add(res, fragment, "" + System.currentTimeMillis());
		transaction.addToBackStack(null);

		transaction.commit();
	}

	public void gotoChiTietCaNhanBangXepHang(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETCANHANBANGXEPHANG);
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoChiTietCaNhanDichVu() {
		changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanDichVuFragment(), true);
	}

	public void gotoChiTietCaNhanTungDichVu(AdapterView<?> parent, View view, int position, long id) {
		changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanBangXepHangTungDichVuFragment(), true);
	}

	public void gotoChiTietCaNhanTungDichVu1(AdapterView<?> parent, View view, int position, long id, String xType) {
		JSONObject jo = (JSONObject) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETCANHANBANGXEPHANGTUNGDICHVU);
		intent.putExtra("xType", xType);
		intent.putExtra("position", Conts.getString(jo, "position"));
		intent.putExtra("mtype", Conts.getString(jo, "type"));
		intent.putExtra("avatar", Conts.getString(jo, "avatar"));
		intent.putExtra("ranking_id", Conts.getString(jo, "id"));
		intent.putExtra("nickname", Conts.getString(jo, "nickname"));
		intent.putExtra("quantity", Conts.getString(jo, "quantity"));
		intent.putExtra("commission", Conts.getString(jo, "commission"));
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoChiTietDichVu(AdapterView<?> parent, View view, int position, long id) {

		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETDICHVU);
		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		intent.putExtra("id", cursor.getString(cursor.getColumnIndex(DichVu.ID)));
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoChiTietDichVuFromHome(AdapterView<?> parent, View view, int position, long id) {

		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETDICHVU);
		NewHomeItem cursor = (NewHomeItem) parent.getItemAtPosition(position);
		intent.putExtra("id", cursor.id);
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoMoiDvChoNhieuNguoi(String id) {
		Conts.hiddenKeyBoard(this);
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.MOIDICHVUCHONHIEUNGUOI);
		intent.putExtra("id", id);
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoMoiDvChoNhieuNguoiFragment(String id) {
		Conts.hiddenKeyBoard(this);

		MoiDvChoNhieuNguoiFragment choNhieuNguoiFragment = new MoiDvChoNhieuNguoiFragment();
		Bundle args = new Bundle();
		args.putString("id", id);
		choNhieuNguoiFragment.setArguments(args);
		changeFragemt(R.id.root_main_fragment, choNhieuNguoiFragment, true);

	}

	public void gotoChiTietDichVuFragment(AdapterView<?> parent, View view, int position, long id) {
		Cursor cursor = (Cursor) parent.getItemAtPosition(position);
		ChiTietDichVuFragment chiTietDichVuFragment = new ChiTietDichVuFragment();
		Bundle args = new Bundle();
		args.putString("id", cursor.getString(cursor.getColumnIndex(DichVu.ID)));
		chiTietDichVuFragment.setArguments(args);
		changeFragemt(R.id.root_main_fragment, chiTietDichVuFragment, true);
	}

	public void gotoSendDvChoNhieuNguoi(boolean isEmail) {
		Bundle bundle = new Bundle();
		bundle.putBoolean("isemail", isEmail);
		GuiDvChoNhieuNguoiFragment choNhieuNguoiFragment = new GuiDvChoNhieuNguoiFragment();
		choNhieuNguoiFragment.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, choNhieuNguoiFragment, true);
	}

	public void gotoChiTietTinTuc(AdapterView<?> parent, View view, int position, long id) {
		JSONObject data = (JSONObject) parent.getItemAtPosition(position);
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETTINTUC);
		intent.putExtra("id", Conts.getString(data, "id"));
		intent.putExtra("news_id", Conts.getString(data, "id"));
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	/**
	 * type false : moi dich vu cho nhieu nguoi
	 * 
	 * @param id
	 * @param customers
	 */
	public void gotoLoiMoi(String id, String customers) {

		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
		MauMoiFragment mauMoiFragment = new MauMoiFragment();

		Bundle args = new Bundle();
		args.putBoolean("type", false);
		args.putString("id", id);
		args.putString("customers", customers);

		args.putString("sdt", "");
		args.putString("service_code", "");
		args.putString("service_codes", "");
		mauMoiFragment.setArguments(args);
		transaction.setCustomAnimations(R.anim.abc_alpha_in, R.anim.abc_alpha_in, R.anim.abc_alpha_out, R.anim.abc_alpha_out);
		transaction.add(R.id.root_main_fragment, mauMoiFragment, "" + System.currentTimeMillis());
		transaction.addToBackStack(null);

		transaction.commit();
	}

	/**
	 * type true : moi nhieu dich vu cho 1 nguoi
	 * 
	 * @param sdt
	 * @param service_code
	 * @param service_codes
	 */
	public void gotoLoiMoi(String sdt, String service_code, String service_codes) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
		MauMoiFragment mauMoiFragment = new MauMoiFragment();
		Bundle args = new Bundle();
		args.putBoolean("type", true);
		args.putString("id", "");
		args.putString("customers", "");
		args.putString("sdt", sdt);
		args.putString("service_code", service_code);
		args.putString("service_codes", service_codes);
		mauMoiFragment.setArguments(args);
		transaction.setCustomAnimations(R.anim.abc_alpha_in, R.anim.abc_alpha_in, R.anim.abc_alpha_out, R.anim.abc_alpha_out);
		transaction.add(R.id.root_main_fragment, mauMoiFragment, "" + System.currentTimeMillis());
		transaction.addToBackStack(null);

		transaction.commit();
	}

	public void gotoHuongDanBanHang() {
		Bundle bundle = new Bundle();
		HuongDanBanHangFragment chitiettintuc = new HuongDanBanHangFragment();
		bundle.putBoolean("addfragment", true);
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);
	}

	public void gotoHuongDanBanHangOfDichVh(String service_guide) {
		Bundle bundle = new Bundle();
		HuongDanBanHangOfDichVuFragment chitiettintuc = new HuongDanBanHangOfDichVuFragment();
		bundle.putBoolean("addfragment", true);
		bundle.putString("service_guide", service_guide);
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);
	}

	public void gotoChinhsuaThongTinCaNhan() {
		Bundle bundle = new Bundle();
		InforFragment chitiettintuc = new InforFragment();
		bundle.putBoolean("addfragment", true);
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);
	}

	public void gotoChiTietLichSuBanHang(Bundle bundle) {
		ChiTietListSuBanHangFragment chitiettintuc = new ChiTietListSuBanHangFragment();
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);
	}

	private void overridePendingTransitionStartActivity() {
		getParent().overridePendingTransition(R.anim.abc_scale_in, R.anim.abc_nothing);
	}

	public void closeActivity() {
		finish();
		overridePendingTransition(R.anim.abc_scale_in, R.anim.abc_nothing);
	}

	public void execute(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		((VApplication) getApplication()).execute(requestMethod, api, bundle, contsCallBack);
	}

	public void moi(boolean type, Bundle bundle) {
		onBackPressed();
		String api = !type ? API.API_R015 : API.API_R016;

		// Conts.showDialogThongbao(this, api + " : " + bundle.toString());
		execute(RequestMethod.POST, api, bundle, new IContsCallBack() {
			ProgressDialog dialog;

			@Override
			public void onSuscess(JSONObject response) {
				String message = "";
				try {
					message = response.getString("message");
				} catch (JSONException e1) {
				}

				if (Conts.isBlank(message)) {
					message = getActivity().getString(R.string.success_moi);
				} else {
					message = String.format("%s\n%s", message, getActivity().getString(R.string.success_moi));
				}
				Conts.showDialogDongYCallBack(getActivity(), message, new DialogCallBack() {
					@Override
					public void callback(Object object) {
						// ((RootMenuActivity) getActivity()).closeActivity();
						closeActivity();
					}
				});
				if (dialog != null)
					dialog.dismiss();
			}

			@Override
			public void onStart() {
				if (dialog == null)
					dialog = ProgressDialog.show(getActivity(), null, getActivity().getString(R.string.loading));
			}

			@Override
			public void onError(String message) {
				Conts.showDialogThongbao(getActivity(), message);
				if (dialog != null)
					dialog.dismiss();
			}

			@Override
			public void onError() {
				onError("onError");
			}
		});
	}

	public void moiContactUser(String user, String name) {
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.NHIEUDICHVU);
		intent.putExtra("msisdn", user);
		intent.putExtra("name", name);
		intent.putExtra(User._ID, "");
		startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void moiContactUserFragment(String _id) {

		Bundle bundle = new Bundle();
		bundle.putString(User._ID, _id);
		bundle.putString("msisdn", "");
		bundle.putString("name", "");
		MoiNhieuDichVuFragment moiNhieuDichVuFragment = new MoiNhieuDichVuFragment();
		moiNhieuDichVuFragment.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, moiNhieuDichVuFragment);
	}

	public void gotoChiTietDichVuFromHome(String id) {
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETDICHVU);
		intent.putExtra("id", id);
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void homeXemall(String name) {
		// TODO

		if (!Conts.isBlank(name)) {
			Intent intent = new Intent(this, RootMenuActivity.class);
			if (name.equals(getString(R.string.dichvuhot))) {
				intent.putExtra("type", Conts.DICHVUHOT);
			} else if (name.equals(getString(R.string.moithanhvien))) {
				intent.putExtra("type", Conts.MOITHANHVIEN);
			} else if (name.equals(getString(R.string.dichvubanchay))) {
				intent.putExtra("type", Conts.DICHVUBANCHAY);
			}
			getParent().startActivity(intent);
			overridePendingTransitionStartActivity();
		}

	}
}