/**
 * 
 */
package vnp.com.mimusic.activity;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.aretha.slidemenudemo.fragment.BangXepHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanBangXepHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanBangXepHangTungDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietListSuBanHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietTintucFragment;
import com.aretha.slidemenudemo.fragment.DichVuFragment;
import com.aretha.slidemenudemo.fragment.GuiDvChoNhieuNguoiFragment;
import com.aretha.slidemenudemo.fragment.HomeFragment;
import com.aretha.slidemenudemo.fragment.HuongDanBanHangFragment;
import com.aretha.slidemenudemo.fragment.InforFragment;
import com.aretha.slidemenudemo.fragment.LichSuBanHangFragment;
import com.aretha.slidemenudemo.fragment.MauMoiFragment;
import com.aretha.slidemenudemo.fragment.MoiDvChoNhieuNguoiFragment;
import com.aretha.slidemenudemo.fragment.MoiNhieuDichVuFragment;
import com.aretha.slidemenudemo.fragment.QuyDinhBanHangFragment;
import com.aretha.slidemenudemo.fragment.SearchFragment;
import com.aretha.slidemenudemo.fragment.ThongTinCaNhanFragment;
import com.aretha.slidemenudemo.fragment.TinTucFragment;

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
			MoiNhieuDichVuFragment moiNhieuDichVuFragment = new MoiNhieuDichVuFragment();
			moiNhieuDichVuFragment.setArguments(bundle);
			changeFragemt(R.id.root_main_fragment, moiNhieuDichVuFragment);
		} else if (Conts.CHITIETTINTUC.equals(type)) {
			ChiTietTintucFragment chiTietTintucFragment = new ChiTietTintucFragment();
			Bundle args = new Bundle();
			args.putString("id", getIntent().getStringExtra("id") + "");
			chiTietTintucFragment.setArguments(args);
			changeFragemt(R.id.root_main_fragment, chiTietTintucFragment);
		} else if (Conts.MOIDICHVUCHONHIEUNGUOI.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new MoiDvChoNhieuNguoiFragment());
		} else if (Conts.CHITIETDICHVU.equals(type)) {

			ChiTietDichVuFragment chiTietTintucFragment = new ChiTietDichVuFragment();
			Bundle args = new Bundle();
			args.putString("id", getIntent().getStringExtra(DichVu.ID) + "");
			chiTietTintucFragment.setArguments(args);
			changeFragemt(R.id.root_main_fragment, chiTietTintucFragment);
		} else if (Conts.CHITIETCANHANBANGXEPHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanBangXepHangFragment());
		} else if (Conts.CHITIETCANHANBANGXEPHANGTUNGDICHVU.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanBangXepHangTungDichVuFragment());
		} else if (Conts.CHITTIETLICHSUBANHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new ChiTietListSuBanHangFragment());
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
		sendBroadcast(new Intent("updateprofile"));
		FragmentManager fragmentManager = getSupportFragmentManager();
		int count = fragmentManager.getBackStackEntryCount();

		if (count > 1) {
			fragmentManager.popBackStack();
		} else if (count == 1) {
			try {
				((BaseMusicSlideMenuActivity) getParent()).finish(true);
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

	public void gotoChiTietCaNhanTungDichVu1(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.CHITIETCANHANBANGXEPHANGTUNGDICHVU);
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

	public void gotoMoiDvChoNhieuNguoi() {

		Intent intent = new Intent(this, RootMenuActivity.class);
		intent.putExtra("type", Conts.MOIDICHVUCHONHIEUNGUOI);
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoMoiDvChoNhieuNguoiFragment() {
		changeFragemt(R.id.root_main_fragment, new MoiDvChoNhieuNguoiFragment(), true);

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
		try {
			intent.putExtra("id", data.getString("id"));
		} catch (JSONException e) {
		}
		getParent().startActivity(intent);
		overridePendingTransitionStartActivity();
	}

	public void gotoLoiMoi() {

		FragmentManager fragmentManager = getSupportFragmentManager();
		android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
		MauMoiFragment mauMoiFragment = new MauMoiFragment();
		transaction.setCustomAnimations(R.anim.abc_slide_in_top, R.anim.abc_slide_in_top, R.anim.abc_slide_out_top, R.anim.abc_slide_out_top);
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

	public void gotoChinhsuaThongTinCaNhan() {
		Bundle bundle = new Bundle();
		InforFragment chitiettintuc = new InforFragment();
		bundle.putBoolean("addfragment", true);
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);
	}

	public void gotoChiTietLichSuBanHang() {

		Bundle bundle = new Bundle();
		ChiTietListSuBanHangFragment chitiettintuc = new ChiTietListSuBanHangFragment();
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);

	}

	private void overridePendingTransitionStartActivity() {
		getParent().overridePendingTransition(R.anim.abc_scale_in, R.anim.abc_nothing);
	}
}