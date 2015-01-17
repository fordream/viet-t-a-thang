package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.BangXephangListView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
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
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}