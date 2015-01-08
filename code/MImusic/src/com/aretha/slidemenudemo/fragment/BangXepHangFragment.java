package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class BangXepHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private LoadingView bxhLoadingView;
	private View view;
	private String text = "";
	private boolean successRequest = false;
	private String type;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private vnp.com.mimusic.view.MusicListView bangxephang_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bangxephang, null);
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
		bangxephang_list.setText(false, "");
		if (b) {
			text = getResources().getString(R.string.bangxephang_so_luong_no_data);
			type = "2";// theo so luong
		} else {
			text = getResources().getString(R.string.bangxephang_doanh_thu_no_data);
			type = "1";// theo doanh thu
		}
		BangXepHangAdaper adaper = new BangXepHangAdaper(getActivity(), new JSONArray());
		bangxephang_list.setAdapter(adaper);
		bangxephang_list.setTextNoData(true, text);
		// get data for list
		bxhLoadingView = (LoadingView) view.findViewById(R.id.bxhLoadingView);
		Bundle bxhParamBundle = new Bundle();
		bxhParamBundle.putString("type", type);
		if (!successRequest) {
			getmImusicService().execute(RequestMethod.GET, API.API_R024, bxhParamBundle, new IContsCallBack() {
				@Override
				public void onSuscess(JSONObject response) {
					successRequest = true;
					try {
						JSONArray jsonArray = response.getJSONArray("data");
						((BangXepHangAdaper) bangxephang_list.getAdapter()).setType(type);
						((BangXepHangAdaper) bangxephang_list.getAdapter()).setJSOnArray(jsonArray);
						((BangXepHangAdaper) bangxephang_list.getAdapter()).notifyDataSetChanged();

						if (jsonArray.length() == 0) {
							bangxephang_list.setTextNoData(true, text);
						} else {
							bangxephang_list.setOnItemClickListener(BangXepHangFragment.this);
						}
					} catch (Exception exception) {

					}
					Conts.showView(bxhLoadingView, false);
				}

				@Override
				public void onStart() {
					Conts.showView(bxhLoadingView, true);
				}

				@Override
				public void onError(String message) {
					Conts.showView(bxhLoadingView, false);
					Conts.toast(getActivity(), message);
				}

				@Override
				public void onError() {
					onError("check network");
				}
			});
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