package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.NewHomeAdapter;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.MusicListView;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private vnp.com.mimusic.view.MusicListView list;
	private View home_header;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUI();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);
		list = (MusicListView) view.findViewById(R.id.list);

		list.setOnItemClickListener(this);
		home_header = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_header, null);
		Conts.showView(home_header.findViewById(R.id.home_header_main), false);

		home_header.findViewById(R.id.dichvubanchay).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				homeXemall(getString(R.string.dichvubanchay));
			}
		});
		list.addHeaderView(home_header);

		Bundle bundle = new Bundle();
		execute(RequestMethod.GET, API.API_R026, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
			}

			@Override
			public void onSuscess(JSONObject response) {
				updateUI();
			}

			@Override
			public void onError(String message) {
			}

			@Override
			public void onError() {
				onError("");
			}
		});
		// callSHowData();

		return view;
	}

	private void updateUI() {
		list.setAdapter(new NewHomeAdapter(getActivity()) {

			@Override
			public void moiDVChoNhieuNguoi(String id) {
				(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(id);
			}

			@Override
			public void dangKy(ContentValues values) {
				new DangKyDialog(getActivity(), values) {
					public void updateUiDangKy() {
						updateUI();
					};
				}.show();
			}

			@Override
			public void xemall(String name) {
				homeXemall(name);

			}

			@Override
			public void moiContactUser(String user, String name) {
				(((RootMenuActivity) getActivity())).moiContactUser(user,name);

			}

		});
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		NewHomeItem homeItem = (NewHomeItem) parent.getItemAtPosition(position);
		if (homeItem.type == 2) {
			// call to dich vu
			(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHome(parent, view, position, id);
		}
	}

	private void homeXemall(String name) {
		// TODO
		if (!Conts.isBlank(name)) {
			if (name.equals(getString(R.string.dichvuhot))) {

			} else if (name.equals(getString(R.string.moithanhvien))) {

			} else if (name.equals(getString(R.string.dichvubanchay))) {

			}
		}
	}
}