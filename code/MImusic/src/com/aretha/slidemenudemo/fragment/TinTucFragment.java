package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.ExeCallBack;
import vnp.com.api.ExeCallBackOption;
import vnp.com.api.ResClientCallBack;
import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.TintucAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.LogUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class TinTucFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tintuc, null);
		final ListView bangxephang_list = (ListView) view.findViewById(R.id.tintuc_list);
		bangxephang_list.setOnItemClickListener(this);

		bangxephang_list.setAdapter(new TintucAdaper(getActivity(), new JSONArray()));

		getmImusicService().execute(RequestMethod.POST, API.API_R027, new Bundle(), new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				try {
					JSONArray jsonArray = response.getJSONArray("data");
					((TintucAdaper) bangxephang_list.getAdapter()).setJSOnArray(jsonArray);
					((TintucAdaper) bangxephang_list.getAdapter()).notifyDataSetChanged();
				} catch (Exception exception) {

				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String message) {
				Conts.toast(getActivity(), message);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "check network");
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