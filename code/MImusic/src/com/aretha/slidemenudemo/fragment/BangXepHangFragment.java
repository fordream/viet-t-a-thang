package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class BangXepHangFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private ListView bangxephang_list;
	private View header_nodata;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bangxephang, null);
		bangxephang_list = (ListView) view.findViewById(R.id.bangxephang_list);
		header_nodata = inflater.inflate(R.layout.bangxephang_nodata, null);
		BangXepHangHeaderView bangxephang_bangxephangheader = (BangXepHangHeaderView) view.findViewById(R.id.bangxephang_bangxephangheader);
		bangxephang_bangxephangheader.setBangXepHangHeaderInterface(new BangXepHangHeaderInterface() {

			@Override
			public void callSoLuong(boolean isSoluong) {
				// TODO
				callData(isSoluong);
			}
		});
		callData(true);
		return view;
	}

	private void callData(boolean b) {
		try {
			bangxephang_list.removeHeaderView(header_nodata);
		} catch (Exception exception) {

		}
		BangXepHangAdaper adaper = new BangXepHangAdaper(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" });
		if (!b) {
			bangxephang_list.setAdapter(null);
			bangxephang_list.addHeaderView(header_nodata);
			adaper = new BangXepHangAdaper(getActivity(), new String[] {});
		}
		bangxephang_list.setOnItemClickListener(this);
		bangxephang_list.setAdapter(adaper);
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanBangXepHang(parent, view, position, id);
	}
}