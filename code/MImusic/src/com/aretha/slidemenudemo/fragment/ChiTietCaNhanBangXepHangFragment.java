package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.ChiTietCaNhanBangXepHangAdaper;
import vnp.com.mimusic.view.BangXepHangItemView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChiTietCaNhanBangXepHangFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietcanhanbangxephang, null);
		ListView bangxephang_list = (ListView) view.findViewById(R.id.chitietcanhanbangxephang_list);

		View header = new BangXepHangItemView(getActivity());
		header.setOnClickListener(null);
		bangxephang_list.addHeaderView(header);
		bangxephang_list.setOnItemClickListener(this);
		bangxephang_list.setAdapter(new ChiTietCaNhanBangXepHangAdaper(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }) );
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanDichVu(parent, view, position, id);
	}
}