package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
				// Huong dan ban hang
				// TODO
				Toast.makeText(v.getContext(), "huong dan ban hang", Toast.LENGTH_SHORT).show();
			}
		});

		ChiTietDichVuNoFeatureView chitietdichvu_chitietdichvunofeatureview = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.chitietdichvu_chitietdichvunofeatureview);

		chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.chitietdichvu_no_feature_dangky).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ContentValues values = new ContentValues();
				values.put("name", "Dịch vụ Imuzik");
				values.put("content", "Website nhặc trực tuyến của tập đoản viễn thông quân đội viettel, các bài hát hot nhất, mới nhất , kết nối với các nhạc sĩ tên tuổi nhất...");
				new DangKyDialog(v.getContext(), values).show();
			}
		});
		chitietdichvu_chitietdichvunofeatureview.findViewById(R.id.chitietdichvu_no_feature_moi).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoiFragment();
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {

	}

}