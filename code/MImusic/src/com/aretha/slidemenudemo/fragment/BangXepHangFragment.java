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

	private ListView bangxephang_list;
	private View header_nodata;
	private String[] items;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bangxephang, null);
		bangxephang_list = (ListView) view.findViewById(R.id.bangxephang_list);
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
		// remove header no data when check or not check so luong radio
		try {
			bangxephang_list.removeHeaderView(header_nodata);
		} catch (Exception exception) {
		}
		// get data for list
		if (b) {
			items = new String[0];
		} else {
			items = new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" };
		}
		// header no data
		// TODO can than crash em
		if (header_nodata == null) {
			LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			header_nodata = inflater.inflate(R.layout.bangxephang_nodata, null);
		}

		// set custom font for message
		TextView noDataMessage = (TextView) header_nodata.findViewById(R.id.no_data_message);
		if (b) {
			noDataMessage.setText(getResources().getString(R.string.bangxephang_so_luong_no_data));
		} else {
			noDataMessage.setText(getResources().getString(R.string.bangxephang_doanh_thu_no_data));
		}
		Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
		noDataMessage.setTypeface(typeface);
		BangXepHangAdaper adaper = new BangXepHangAdaper(getActivity(), items);
		// set data for listview
		bangxephang_list.setAdapter(null);
		if (items.length > 0) {
			bangxephang_list.setOnItemClickListener(this);
			bangxephang_list.setAdapter(adaper);
		} else {

			bangxephang_list.addHeaderView(header_nodata);
			bangxephang_list.setAdapter(adaper);
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// (((RootMenuActivity)
		// getActivity())).gotoChiTietCaNhanBangXepHang(parent, view, position,
		// id);
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}