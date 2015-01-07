package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
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

public class BangXepHangFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private vnp.com.mimusic.view.MusicListView bangxephang_list;
	// private View header_nodata;
	private String[] items;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bangxephang, null);
		bangxephang_list = (vnp.com.mimusic.view.MusicListView) view.findViewById(R.id.bangxephang_list);
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
		// get data for list
		if (b) {
			items = new String[0];
		} else {
			items = new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" };
		}

		bangxephang_list.setText(false, "");
		String text = "";
		if (b) {
			text = getResources().getString(R.string.bangxephang_so_luong_no_data);
		} else {
			text = getResources().getString(R.string.bangxephang_doanh_thu_no_data);
		}

		BangXepHangAdaper adaper = new BangXepHangAdaper(getActivity(), items);
		bangxephang_list.setAdapter(adaper);
		if (items.length > 0) {
			bangxephang_list.setOnItemClickListener(this);
		} else {
			bangxephang_list.setTextNoData(true, text);
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}