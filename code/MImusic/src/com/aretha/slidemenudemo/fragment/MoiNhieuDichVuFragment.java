package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiDanhBaAdapter;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

public class MoiNhieuDichVuFragment extends Fragment implements android.view.View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moinhieudichvu_dialog, null);
		HeaderView maumoi_headerview = (HeaderView) view.findViewById(R.id.moinhieudichvu_dialog_headerview);
		maumoi_headerview.setTextHeader(R.string.moidanhba);
		maumoi_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		maumoi_headerview.setButtonRightImage(false, R.drawable.btn_back);
		maumoi_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		ListView maumoi_list = (ListView) view.findViewById(R.id.moinhieudichvu_dialog_list);
		maumoi_list.setAdapter(new MoiDanhBaAdapter(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));

		view.findViewById(R.id.moinhieudichvu_dialog_chonloimoi).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoLoiMoi();
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}
}