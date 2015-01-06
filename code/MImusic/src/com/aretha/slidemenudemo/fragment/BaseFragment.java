package com.aretha.slidemenudemo.fragment;

import vnp.com.api.MImusicService;
import vnp.com.mimusic.VApplication;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	private MImusicService mImusicService;

	public MImusicService getmImusicService() {
		if (mImusicService == null)
			mImusicService = ((VApplication) getActivity().getApplication()).getmImusicService();
		return mImusicService;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public BaseFragment() {
	}

}
