package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.ExeCallBack;
import vnp.com.api.ExeCallBackOption;
import vnp.com.api.ResClientCallBack;
import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.LoginActivty;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.adapter.TintucAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.view.BangXepHangItemView;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class TinTucFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
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
		ResClientCallBack resClientCallBack = new ResClientCallBack() {

			@Override
			public String getApiName() {
				return "news";
			}

			@Override
			public void onCallBack(Object object) {
				super.onCallBack(object);

				RestClient restClient = (RestClient) object;
				try {
					LogUtils.e("restClient.getResponse()", restClient.getResponse());
					JSONObject jsonObject = new JSONObject(restClient.getResponse());
					String errorCode = jsonObject.getString("errorCode");
					String message = jsonObject.getString("message");
					if ("0".equals(errorCode)) {
						JSONArray jsonArray = jsonObject.getJSONArray("data");
						((TintucAdaper) bangxephang_list.getAdapter()).setJSOnArray(jsonArray);
						((TintucAdaper) bangxephang_list.getAdapter()).notifyDataSetChanged();
					} else {
						Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
					}
				} catch (Exception exception) {
					Toast.makeText(getActivity(), "login fail", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public RequestMethod getMedthod() {
				return RequestMethod.POST;
			}
		};

		resClientCallBack.addParam("token", Conts.getToken(getActivity()));
		ExeCallBack exeCallBack = new ExeCallBack();
		exeCallBack.setExeCallBackOption(new ExeCallBackOption(getActivity(), true, R.string.loading, null));
		exeCallBack.executeAsynCallBack(resClientCallBack);
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