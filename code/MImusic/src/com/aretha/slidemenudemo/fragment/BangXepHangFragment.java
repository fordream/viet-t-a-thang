package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXepHangItemView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class BangXepHangFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bangxephang, null);
		ListView bangxephang_list = (ListView) view.findViewById(R.id.bangxephang_list);

		bangxephang_list.addHeaderView(new BangXepHangHeaderView(getActivity()));
		bangxephang_list.setOnItemClickListener(this);
		bangxephang_list.setAdapter(new BangXepHangAdaper(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanBangXepHang(parent, view, position, id);
	}
}