package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MauMoiAdaper;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MauMoiFragment extends Fragment implements android.view.View.OnClickListener {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.maumoi_dialog, null);
		ListView maumoi_list = (ListView) view.findViewById(R.id.maumoi_list);
		HeaderView maumoi_headerview = (HeaderView) view.findViewById(R.id.maumoi_headerview);
		maumoi_headerview.setTextHeader(R.string.mautinmoitemplete);
		maumoi_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		maumoi_headerview.setButtonRightImage(false, R.drawable.btn_back);
		maumoi_list.addHeaderView(new BangXepHangHeaderView(getActivity()));
		maumoi_list.setAdapter(new MauMoiAdaper(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));

		maumoi_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

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