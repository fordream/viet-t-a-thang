package com.aretha.slidemenudemo.fragment;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.os.Bundle;
import android.support.v4.app.Fragment;

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

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public BaseFragment() {
	}

}
