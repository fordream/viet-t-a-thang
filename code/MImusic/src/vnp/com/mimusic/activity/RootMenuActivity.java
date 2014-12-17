/**
 * 
 */
package vnp.com.mimusic.activity;

import vnp.com.mimusic.R;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.aretha.slidemenudemo.fragment.BangXepHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanBangXepHangFragment;
import com.aretha.slidemenudemo.fragment.ChiTietCaNhanDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietDichVuFragment;
import com.aretha.slidemenudemo.fragment.ChiTietTintucFragment;
import com.aretha.slidemenudemo.fragment.DichVuFragment;
import com.aretha.slidemenudemo.fragment.GuiDvChoNhieuNguoiFragment;
import com.aretha.slidemenudemo.fragment.HomeFragment;
import com.aretha.slidemenudemo.fragment.HuongDanBanHangFragment;
import com.aretha.slidemenudemo.fragment.InforFragment;
import com.aretha.slidemenudemo.fragment.LichSuBanHangFragment;
import com.aretha.slidemenudemo.fragment.MoiDvChoNhieuNguoiFragment;
import com.aretha.slidemenudemo.fragment.QuyDinhBanHangFragment;
import com.aretha.slidemenudemo.fragment.SearchFragment;
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
			// changeFragemt(R.id.root_main_fragment, new HomeFragment());
		} else if (Conts.QUYDINHBANHANG.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new QuyDinhBanHangFragment());
		} else if (Conts.TIMKIEM.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new SearchFragment());
		} else if (Conts.INFOR.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new InforFragment());
		} else if (Conts.TINTUC.equals(type)) {
			changeFragemt(R.id.root_main_fragment, new TinTucFragment());
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
		Log.e("onBackPressed", "Root<Menu");
		FragmentManager fragmentManager = getSupportFragmentManager();
		int count = fragmentManager.getBackStackEntryCount();

		// Toast.makeText(getActivity(), count + "", Toast.LENGTH_SHORT).show();

		if (count > 1) {
			fragmentManager.popBackStack();
		} else if (count == 1) {
			// finish();
			// overridePendingTransition(R.anim.abc_nothing,
			// R.anim.abc_slide_right_out);

			((BaseMusicSlideMenuActivity) getParent()).finish(true);

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
			transaction.setCustomAnimations(R.anim.abc_slide_right_in, R.anim.abc_slide_right_in, R.anim.abc_slide_right_out, R.anim.abc_slide_right_out);
			// transaction.setCustomAnimations(R.anim.enter, R.anim.exit,
			// R.anim.pop_enter, R.anim.pop_exit);
		}
		transaction.add(res, fragment, "" + System.currentTimeMillis());
		transaction.addToBackStack(null);

		transaction.commit();
	}

	public void gotoChiTietCaNhanBangXepHang(AdapterView<?> parent, View view, int position, long id) {
		changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanBangXepHangFragment(), true);
	}

	public void gotoChiTietCaNhanDichVu(AdapterView<?> parent, View view, int position, long id) {
		changeFragemt(R.id.root_main_fragment, new ChiTietCaNhanDichVuFragment(), true);
	}

	public void gotoChiTietDichVu(AdapterView<?> parent, View view, int position, long id) {
		changeFragemt(R.id.root_main_fragment, new ChiTietDichVuFragment(), true);
	}

	public void gotoMoiDvChoNhieuNguoi() {
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
		Bundle bundle = new Bundle();
		ChiTietTintucFragment chitiettintuc = new ChiTietTintucFragment();
		chitiettintuc.setArguments(bundle);
		changeFragemt(R.id.root_main_fragment, chitiettintuc, true);
	}
}