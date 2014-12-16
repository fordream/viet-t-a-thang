package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import android.content.ContentValues;
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
		ChiTietDichVuNoFeatureView chitietdichvu_chitietdichvunofeatureview = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.chitietdichvu_chitietdichvunofeatureview);

		chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.chitietdichvu_no_feature_dangky).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put("name", "dich vu 1");
				values.put("content", "content");
				new DangKyDialog(v.getContext(), values).show();
			}
		});
		chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.chitietdichvu_no_feature_moi).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi();
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {

	}

}