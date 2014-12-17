package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.TinTucKhacItemView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

public class ChiTietTintucFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LinearLayout chitiet_tintuc_tintuckhac_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitiet_tintuc, null);
		chitiet_tintuc_tintuckhac_list = (LinearLayout) view.findViewById(R.id.chitiet_tintuc_tintuckhac_list);

		for (int i = 0; i < 10; i++) {
			chitiet_tintuc_tintuckhac_list.addView(new TinTucKhacItemView(getActivity()));
		}
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}