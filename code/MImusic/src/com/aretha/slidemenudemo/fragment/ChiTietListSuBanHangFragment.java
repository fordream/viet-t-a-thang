package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.ChiTietListSuBanHangAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.view.FooterBangXepHangNoDataView;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ChiTietListSuBanHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private vnp.com.mimusic.view.MusicListView home_list;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LoadingView loadingView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietlichsubanhang, null);
		loadingView = (LoadingView) view.findViewById(R.id.loadingView1);
		HeaderView header = (HeaderView) view.findViewById(R.id.activity_login_header);
		header.setTextHeader(R.string.chitietbanhang);
		header.showButton(true, false);
		header.setButtonLeftImage(true, R.drawable.btn_back);
		header.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		home_list = (vnp.com.mimusic.view.MusicListView) view.findViewById(R.id.home_list);
		execute(RequestMethod.GET, API.API_R008, getArguments(), new IContsCallBack() {
			@Override
			public void onSuscess(JSONObject response) {
				
				home_list.setText(false, R.string.nodata);
				try {
					JSONArray data = response.getJSONArray("data");
					if (data.length() == 0) {
						home_list.setTextNoData(true, R.string.nodata);
					} else {
						home_list.setAdapter(new ChiTietListSuBanHangAdaper(getActivity(), new JSONObject[] {}, data));
					}
				} catch (JSONException e) {
				}

				Conts.showView(loadingView, false);
			}

			@Override
			public void onStart() {
				Conts.showView(loadingView, true);
			}

			@Override
			public void onError(String message) {
				Conts.showView(loadingView, false);
				home_list.setTextNoData(true, message);
			}

			@Override
			public void onError() {
				onError("onError");
			}
		});

		return view;
	}

	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}