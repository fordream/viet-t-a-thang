package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.JsonTintucAdaper;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.vnp.core.scroll.VasTintucScrollListView;

public class TinTucFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {

	private JsonTintucAdaper tintucAdaper;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LoadingView loadingView1;
	vnp.com.mimusic.view.MusicListView bangxephang_list;

	@Override
	public void onCLickButtonLeft() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuLeft();
	}

	@Override
	public void onCLickButtonRight() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuRight();
	}

	private HeaderView listHeader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tintuc, null);
		loadHeader(view, R.id.header, R.string.tintuc, true, true);
		listHeader = new HeaderView(getActivity());
		listHeader.showHeader(true);
		loadingView1 = (LoadingView) view.findViewById(R.id.loadingView1);
		Conts.showView(loadingView1, false);
		bangxephang_list = (vnp.com.mimusic.view.MusicListView) view.findViewById(R.id.tintuc_list);
		bangxephang_list.addHeaderView(listHeader);
		bangxephang_list.setOnItemClickListener(this);
		// tintucAdaper = new TintucAdaper(getActivity(),
		// TinTuc.queryFromId(getActivity(), null));
		tintucAdaper = new JsonTintucAdaper(getActivity());
		tintucAdaper.getData();
		
		bangxephang_list.setAdapter(tintucAdaper);

		executeHttps(RequestMethod.GET, API.API_R027, new Bundle(), new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				Conts.showView(loadingView1, false);
				// Cursor cursor = TinTuc.queryFromId(getActivity(), null);
				// tintucAdaper.changeCursor(cursor);
				tintucAdaper.getData();
				if (tintucAdaper.getCount() == 0) {
					bangxephang_list.setText(true, R.string.nodata);
				} else {
					bangxephang_list.setText(false, R.string.nodata);
				}
				// else {
				// if (cursor.getCount() == 0) {
				// bangxephang_list.setTextNoData(true, R.string.nodata);
				// } else {
				// bangxephang_list.setAdapter(new TintucAdaper(getActivity(),
				// cursor));
				// }
				// }
			}

			@Override
			public void onStart() {

				if (tintucAdaper.getCount() == 0) {
					Conts.showView(loadingView1, true);
				}
			}

			@Override
			public void onError(String message) {
				// bangxephang_list.setTextNoData(true, message);
				// Conts.showView(loadingView1, false);
				onSuscess(null);
			}

		});

		ListView listViews[] = new ListView[] { bangxephang_list };
		new VasTintucScrollListView(getHeaderView(), listHeader, listViews, getActivity());
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