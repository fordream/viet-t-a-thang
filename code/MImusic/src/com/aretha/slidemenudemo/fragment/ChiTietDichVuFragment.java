package com.aretha.slidemenudemo.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ChiTietDichVuFragment extends BaseFragment implements View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private String service_guide = "";
	private LoadingView loadingView;
	private TextView mchitiet;

	private void update(String service_content) {
		mchitiet.setText(service_content);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietdichvu, null);
		mchitiet = Conts.getView(view, R.id.mchitiet);
		view.findViewById(R.id.chitietdichvu_main).setOnClickListener(null);
		loadingView = (LoadingView) view.findViewById(R.id.loadingView1);
		Conts.showView(loadingView, false);
		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.chitietdichvu_headerview);
		chitiettintuc_headerview.setTextHeader(R.string.chitietdichvu);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonRightImage(true, R.drawable.chititetdichvu_right);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		chitiettintuc_headerview.findViewById(R.id.header_btn_right).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Conts.isBlank(service_guide)) {
					service_guide = getActivity().getString(R.string.nodata);
				}

				(((RootMenuActivity) getActivity())).gotoHuongDanBanHangOfDichVh(service_guide);
			}
		});

		final ChiTietDichVuNoFeatureView chitietdichvu_chitietdichvunofeatureview = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.chitietdichvu_chitietdichvunofeatureview);

		final String service_code = getArguments().getString(DichVuStore.service_code);

		JSONObject cursor = dichVuStore.getDvByServiceCode(service_code);
		chitietdichvu_chitietdichvunofeatureview.setData(cursor);
		final ContentValues values = new ContentValues();
		values.put(DichVuStore.ID, Conts.getString(cursor, DichVuStore.ID));
		values.put("type", "dangky");
		values.put(DichVuStore.service_code, Conts.getString(cursor, DichVuStore.service_code));
		values.put("name", String.format(getString(R.string.title_dangky), Conts.getString(cursor, DichVuStore.service_name)));

		String content = String.format(getString(R.string.xacnhandangky_form), Conts.getString(cursor, DichVuStore.service_name), Conts.getString(cursor, DichVuStore.service_price));
		values.put("content", content);
		final String name = Conts.getString(cursor, DichVuStore.service_name);

		boolean isDangKy = dichVuStore.isRegister(service_code);

		if (!isDangKy) {
			chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.home_item_right_control_1).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					new DangKyDialog(v.getContext(), values) {
						@Override
						public void updateUiDangKy() {
							super.updateUiDangKy();
							Conts.showDialogThongbao(getContext(), String.format(getContext().getString(R.string.bandangkythanhcongdichvu), name));

							chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.home_item_right_control_1).setOnClickListener(null);
							chitietdichvu_chitietdichvunofeatureview.updateDangDung();
							chitietdichvu_chitietdichvunofeatureview.hiddenChitietdichvu_no_feature_dangky();
						}
					}.show();
				}
			});
		} else {
			chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.home_item_right_control_1).setOnClickListener(null);
		}
		chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.home_item_right_control_2).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoiFragment(service_code);
			}
		});

		String service_content = Conts.getString(cursor, DichVuStore.service_content);
		service_guide = Conts.getString(cursor, DichVuStore.service_guide);
		update(service_content);

		if (Conts.isBlank(service_content)) {
			Bundle bundles = new Bundle();
			bundles.putString("service_code", service_code);
			execute(RequestMethod.GET, API.API_R005, bundles, new IContsCallBack() {

				@Override
				public void onSuscess(JSONObject response) {
					try {
						final String service_content = response.getString("service_content");
						update(service_content);
					} catch (JSONException e) {
					}
					Conts.showView(loadingView, false);
				}

				@Override
				public void onError(String message) {
					// Conts.toast(getActivity(), message);
					Conts.showView(loadingView, false);
				}

				@Override
				public void onError() {
					// Conts.toast(getActivity(), "onError");
					Conts.showView(loadingView, false);
				}

				@Override
				public void onStart() {
					Conts.showView(loadingView, false);
				}
			});
		}
		return view;
	}

	@Override
	public void onClick(View v) {

	}

}