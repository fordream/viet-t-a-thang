package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChiTietDichVuFragment extends Fragment implements View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietdichvu, null);
		view.findViewById(R.id.chitietdichvu_main).setOnClickListener(null);

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
		String id = getArguments().getString(DichVu.ID);
		String selection = DichVu.ID + "='" + id + "'";
		final Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
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
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoiFragment();
				}
			});
			cursor.close();
		}
		Bundle bundles = new Bundle();
		bundles.putString("service_code", id);
		Conts.execute(RequestMethod.GET, "utilitiServiceDetail", getActivity(), bundles, new IContsCallBack() {
			
			@Override
			public void onSuscess(JSONObject response) {
				
			}
			
			@Override
			public void onError(String message) {
				
			}
			
			@Override
			public void onError() {
				
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {

	}

}