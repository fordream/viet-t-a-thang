package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.adapter.GuiDivhVuChoNhieuNguoiAdaper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class GuiDvChoNhieuNguoiFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.guidichvuchonhieunguoi, null);
		view.setOnClickListener(null);
		boolean isEmail = getArguments().getBoolean("isemail");
		ListView guidichvuchonhieunguoi_list = (ListView) view.findViewById(R.id.guidichvuchonhieunguoi_list);
		guidichvuchonhieunguoi_list.setOnItemClickListener(this);
		guidichvuchonhieunguoi_list.setAdapter(new GuiDivhVuChoNhieuNguoiAdaper(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));
		
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}