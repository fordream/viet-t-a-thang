package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.ChiTietCaNhanBangXepHangAdaper;
import vnp.com.mimusic.view.BangXepHangItemView;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChiTietCaNhanBangXepHangTungDichVuFragment extends Fragment implements View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietcanhanbangxephangtungdichvu, null);

		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.chitietcanhanbangxephangtungdichvu_header);
		chitiettintuc_headerview.setTextHeader(R.string.chitietcanhanbangxephang);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		view.findViewById(R.id.chitietcanhanbangxephang_tracuu).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoChiTietCaNhanDichVu();
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}

}