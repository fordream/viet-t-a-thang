package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MauMoiAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MauMoiFragment extends BaseFragment implements android.view.View.OnClickListener {
	private View loading;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.maumoi_dialog, null);
		loading = view.findViewById(R.id.loadingView1);
		view.setOnClickListener(null);
		final ListView maumoi_list = (ListView) view.findViewById(R.id.maumoi_list);
		// maumoi_list.setAdapter(new MauMoiAdaper(getActivity(), new String[] {
		// "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a",
		// "a", "a" }));

		view.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		view.findViewById(R.id.recomment_icon_bottom).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		final String id = getArguments().getString(DichVu.ID);
		String service_code = "";
		String selection = DichVu.ID + "='" + id + "'";
		final Cursor mcursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

		if (mcursor != null && mcursor.getCount() >= 1) {
			mcursor.moveToNext();
			service_code = mcursor.getString(mcursor.getColumnIndex(DichVu.service_code));
			mcursor.close();
		}

		Bundle bundle = new Bundle();
		bundle.putString("service_code", service_code);
		getmImusicService().execute(RequestMethod.GET, API.API_R022, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("data");
					MauMoiAdaper adaper = new MauMoiAdaper(getActivity(), new JSONObject[] {},array);
					maumoi_list.setAdapter(adaper);

				} catch (JSONException e) {
				}
				Conts.showView(loading, false);
			}

			@Override
			public void onStart() {
				Conts.showView(loading, true);
			}

			@Override
			public void onError(String message) {
				Conts.toast(getActivity(), message);
				Conts.showView(loading, false);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "onError");
				Conts.showView(loading, false);
			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}
}