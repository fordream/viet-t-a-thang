package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MoiDvChoNhieuNguoiAdaper;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MoiDvChoNhieuNguoiFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moidichvuchonhieunguoi, null);
		ListView moi_list = (ListView) view.findViewById(R.id.moidichvuchonhieunguoi_list);

		ChiTietDichVuNoFeatureView header = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.moidichvuchonhieunguoi_chiteitdichvunofeatureview);
		header.setOnClickListener(null);
		header.findViewById(R.id.chitietdichvu_no_feature_dangky).setVisibility(View.INVISIBLE);
		header.findViewById(R.id.chitietdichvu_no_feature_moi).setVisibility(View.INVISIBLE);

		moi_list.setOnItemClickListener(this);

		moi_list.setAdapter(new MoiDvChoNhieuNguoiAdaper(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));

		view.findViewById(R.id.moidichvuchonhieunguoi_chonloimoi).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// new MauMoiDialog(getActivity()).show();

			}
		});

		view.findViewById(R.id.moidichvuchonhieunguoi_moi).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// (((RootMenuActivity)
		// getActivity())).gotoChiTietCaNhanBangXepHang(parent, view, position,
		// id);
	}
}