package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import vnp.com.mimusic.view.BangXephangListView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class BangXepHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private View view;
	private vnp.com.mimusic.view.BangXephangListView bangxephangSoluong, bangxephangDoanhthu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bangxephang, null);

		bangxephangSoluong = (BangXephangListView) view.findViewById(R.id.bangxephang1);
		bangxephangDoanhthu = (BangXephangListView) view.findViewById(R.id.bangxephang2);
		bangxephangSoluong.setType("2");
		bangxephangDoanhthu.setType("1");

		bangxephangSoluong.setOnItemClick(this);
		bangxephangDoanhthu.setOnItemClick(this);
		BangXepHangHeaderView bangxephang_bangxephangheader = (BangXepHangHeaderView) view.findViewById(R.id.bangxephang_bangxephangheader);
		bangxephang_bangxephangheader.setBangXepHangHeaderInterface(new BangXepHangHeaderInterface() {
			@Override
			public void callSoLuong(boolean isSoluong) {
				callData(isSoluong);
			}
		});
		callData(true);
		return view;
	}

	private void callData(boolean isSoluong) {

		bangxephangSoluong.setVisibility(isSoluong ? View.VISIBLE : View.GONE);
		bangxephangDoanhthu.setVisibility(!isSoluong ? View.VISIBLE : View.GONE);
		if (isSoluong) {
			bangxephangSoluong.execute();
		} else {
			bangxephangDoanhthu.execute();
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String xType = "1";
		if(bangxephangSoluong.getVisibility() == View.VISIBLE){
			xType = "2";
		}
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id,xType);
	}

	// public boolean onBackPressed() {
	// if (bangxephangSoluong.getVisibility() == View.VISIBLE) {
	// return bangxephangSoluong.onBackPressed();
	// }
	//
	// if (bangxephangDoanhthu.getVisibility() == View.VISIBLE) {
	// return bangxephangDoanhthu.onBackPressed();
	// }
	// return false;
	// }
}