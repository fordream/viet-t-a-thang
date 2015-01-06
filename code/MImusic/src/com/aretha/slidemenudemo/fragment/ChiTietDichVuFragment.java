package com.aretha.slidemenudemo.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
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

	private LoadingView loadingView;

	private void update(String service_content) {
		TextView textView = (TextView) getView().findViewById(R.id.mchitiet);
		textView.setText(service_content);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietdichvu, null);
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
				(((RootMenuActivity) getActivity())).gotoHuongDanBanHang();
			}
		});

		ChiTietDichVuNoFeatureView chitietdichvu_chitietdichvunofeatureview = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.chitietdichvu_chitietdichvunofeatureview);

		final String id = getArguments().getString(DichVu.ID);
		String service_code = "";
		String selection = DichVu.ID + "='" + id + "'";
		final Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			service_code = cursor.getString(cursor.getColumnIndex(DichVu.service_code));
			chitietdichvu_chitietdichvunofeatureview.setData(cursor);
			final ContentValues values = new ContentValues();
			values.put(DichVu.ID, id);
			values.put("name", cursor.getString(cursor.getColumnIndex(DichVu.service_name)));
			values.put("content", cursor.getString(cursor.getColumnIndex(DichVu.service_content)));

			chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.chitietdichvu_no_feature_dangky).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					new DangKyDialog(v.getContext(), values).show();
				}
			});

			chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.chitietdichvu_no_feature_moi).setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoiFragment(id);
				}
			});
			cursor.close();
		}

		Bundle bundles = new Bundle();
		bundles.putString("service_code", service_code);
		getmImusicService().execute(RequestMethod.GET, API.API_R005, bundles, new IContsCallBack() {

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
				Conts.toast(getActivity(), message);
				Conts.showView(loadingView, false);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "onError");
				Conts.showView(loadingView, false);
			}

			@Override
			public void onStart() {
				Conts.showView(loadingView, true);
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {

	}

}