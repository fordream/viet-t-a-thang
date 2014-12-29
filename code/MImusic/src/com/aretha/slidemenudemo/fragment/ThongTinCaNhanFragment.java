package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ThongTinCaNhanFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.thongtincanhan, null);
		HeaderView headerView = (HeaderView) view.findViewById(R.id.activity_login_header);
		headerView.setTextHeader(R.string.thongtincanhan);
		headerView.setButtonLeftImage(true, R.drawable.btn_back);
		headerView.setButtonRightImage(true, R.drawable.thongtincanhan_right);

		headerView.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		headerView.findViewById(R.id.header_btn_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoChinhsuaThongTinCaNhan();
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}