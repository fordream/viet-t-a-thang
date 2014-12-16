package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.ChiTietCaNhanDichVuAdaper;
import vnp.com.mimusic.view.BangXepHangItemView;
import vnp.com.mimusic.view.ChiTietCaNhanBangXepHangItemView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChiTietCaNhanDichVuFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietcanhandichvu, null);
		ListView bangxephang_list = (ListView) view.findViewById(R.id.chitietcanhandichvu_list);

		bangxephang_list.addHeaderView(new BangXepHangItemView(getActivity()));
		bangxephang_list.addHeaderView(new ChiTietCaNhanBangXepHangItemView(getActivity()));
		bangxephang_list.setAdapter(new ChiTietCaNhanDichVuAdaper(getActivity(), new String[] { "a" }));
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// (((RootMenuActivity) getActivity())).gotoChiTietCaNhanDichVu(parent,
		// view, position, id);
	}
}