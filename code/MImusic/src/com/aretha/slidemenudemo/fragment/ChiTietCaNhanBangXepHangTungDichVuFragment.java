package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChiTietCaNhanBangXepHangTungDichVuFragment extends BaseFragment implements View.OnClickListener {
	// private TextView soGDTrongThang, soGD, soHHTrongThang, soHH,
	// bangxephangItemTvStt;
	// private LoadingView loadingView;
	private vnp.com.mimusic.view.ChitietCaNhanBangXepHangTungDichVuView chitiet;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietcanhanbangxephangtungdichvu, null);
		chitiet = Conts.getView(view, R.id.chititet);
		chitiet.setType(getArguments().getString("xType"));
		chitiet.initData(getArguments());

		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.chitietcanhanbangxephangtungdichvu_header);
		chitiettintuc_headerview.setTextHeader(R.string.chitietcanhanbxh);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {
	}

}