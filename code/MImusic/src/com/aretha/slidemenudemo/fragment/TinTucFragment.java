package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.TintucAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.LoadingView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class TinTucFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LoadingView loadingView1;
	vnp.com.mimusic.view.MusicListView bangxephang_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tintuc, null);
		loadingView1 = (LoadingView) view.findViewById(R.id.loadingView1);
		bangxephang_list = (vnp.com.mimusic.view.MusicListView) view.findViewById(R.id.tintuc_list);
		bangxephang_list.setOnItemClickListener(this);

		bangxephang_list.setAdapter(new TintucAdaper(getActivity(), new JSONArray()));

		execute(RequestMethod.GET, API.API_R027, new Bundle(), new IContsCallBack() {
			@Override
			public void onSuscess(JSONObject response) {
				bangxephang_list.setText(false, "");
				try {
					JSONArray jsonArray = response.getJSONArray("data");
					((TintucAdaper) bangxephang_list.getAdapter()).setJSOnArray(jsonArray);
					((TintucAdaper) bangxephang_list.getAdapter()).notifyDataSetChanged();

					if (jsonArray.length() == 0) {
						bangxephang_list.setTextNoData(true, R.string.nodata);
					}
				} catch (Exception exception) {

				}
				Conts.showView(loadingView1, false);
			}

			@Override
			public void onStart() {
				Conts.showView(loadingView1, true);
			}

			@Override
			public void onError(String message) {
				Conts.showView(loadingView1, false);
				// Conts.toast(getActivity(), message);
				bangxephang_list.setText(true, message);
			}

			@Override
			public void onError() {
				onError("check network");
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietTinTuc(parent, view, position, id);
	}
}