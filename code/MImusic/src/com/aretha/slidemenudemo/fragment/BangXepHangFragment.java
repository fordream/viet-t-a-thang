package com.aretha.slidemenudemo.fragment;

import vnp.com.db.BangXepHang;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import vnp.com.mimusic.view.BangXephangListView;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.vnp.core.scroll.VasBangXepHangScrollListView;

public class BangXepHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private View view;
	private vnp.com.mimusic.view.BangXephangListView bangxephangSoluong, bangxephangDoanhthu;
	private HeaderView headerbangxephangsoluong, headerbangxepdoanhthu;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCLickButtonLeft() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuLeft();
	}

	@Override
	public void onCLickButtonRight() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuRight();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bangxephang, null);

		loadHeader(view, R.id.header, R.string.bangxephang, true, true);

		bangxephangSoluong = (BangXephangListView) view.findViewById(R.id.bangxephang1);
		bangxephangDoanhthu = (BangXephangListView) view.findViewById(R.id.bangxephang2);

		headerbangxephangsoluong = new HeaderView(getActivity());
		headerbangxephangsoluong.showHeadderSearch();
		bangxephangSoluong.addHeader(headerbangxephangsoluong);

		headerbangxepdoanhthu = new HeaderView(getActivity());
		headerbangxepdoanhthu.showHeadderSearch();
		bangxephangDoanhthu.addHeader(headerbangxepdoanhthu);

		bangxephangSoluong.setType(BangXepHang.typeSOLUONG);
		bangxephangDoanhthu.setType(BangXepHang.typeDOANHTHU);
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

		new VasBangXepHangScrollListView(getHeaderView(), headerbangxephangsoluong, new ListView[] { bangxephangSoluong.list(), bangxephangDoanhthu.list() }, getActivity());
		// new VasBangXepHangScrollListView(getHeaderView(),
		// headerbangxepdoanhthu, bangxephangDoanhthu.list());

		return view;
	}

	private void callData(boolean isSoluong) {
		if (isSoluong) {
			bangxephangSoluong.setVisibility(View.VISIBLE);
			bangxephangDoanhthu.setVisibility(View.GONE);
			bangxephangSoluong.execute();

			// bangxephangSoluong.updateHeader(getHeaderView().getVisibility()
			// == view.VISIBLE);
		} else {
			bangxephangDoanhthu.setVisibility(View.VISIBLE);
			bangxephangSoluong.setVisibility(View.GONE);
			bangxephangDoanhthu.execute();
			// bangxephangDoanhthu.updateHeader(getHeaderView().getVisibility()
			// == view.VISIBLE);
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		String xType = bangxephangSoluong.getType();
		if (bangxephangSoluong.getVisibility() == View.GONE) {
			xType = bangxephangDoanhthu.getType();
		}
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id, xType);
	}
}