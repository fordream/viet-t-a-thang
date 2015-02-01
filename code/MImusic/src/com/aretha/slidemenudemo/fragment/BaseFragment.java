package com.aretha.slidemenudemo.fragment;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

public class BaseFragment extends Fragment {

	public void execute(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		((VApplication) getActivity().getApplication()).execute(requestMethod, api, bundle, contsCallBack);
	}

	public void executeHttps(final RequestMethod requestMethod, final String api, final Bundle bundle, final IContsCallBack contsCallBack) {
		((VApplication) getActivity().getApplication()).executeHttps(requestMethod, api, bundle, contsCallBack);
	}

	public void executeUpdateAvatar(String path2, IContsCallBack iContsCallBack) {
		((VApplication) getActivity().getApplication()).executeUpdateAvatar(path2, iContsCallBack);
	}

	public void executeUpdateHttpsAvatar(String path2, IContsCallBack iContsCallBack) {
		((VApplication) getActivity().getApplication()).executeUpdateHttpsAvatar(path2, iContsCallBack);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public BaseFragment() {
	}

	private HeaderView header;

	public HeaderView getHeaderView() {
		return header;
	}

	public void createHeader(String title, boolean leftShow, boolean rightShow) {
		View.OnClickListener homeOnClick = new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (v.getId() == R.id.header_btn_left) {
					onCLickButtonLeft();
				} else if (v.getId() == R.id.header_btn_right) {
					onCLickButtonRight();
				}
			}
		};
		if (header == null) {
			header = new HeaderView(getActivity());
			header.setTextHeader(title);
			header.showButton(leftShow, rightShow);

			header.findViewById(R.id.header_btn_left).setOnClickListener(homeOnClick);
			header.findViewById(R.id.header_btn_right).setOnClickListener(homeOnClick);
		}

	}

	public void onCLickButtonLeft() {

	}

	public void onCLickButtonRight() {

	}
}
