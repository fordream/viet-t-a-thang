package com.aretha.slidemenudemo.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.HuongDanBanHangAdapter;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.MusicListView;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HuongDanBanHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private MusicListView dichvu_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.huongdanbanhang, null);
		dichvu_list = (MusicListView) view.findViewById(R.id.quydinhbanhang_list);
		dichvu_list.setOnItemClickListener(this);
		HeaderView header = (HeaderView) view.findViewById(R.id.quydinhbanhang_header);
		header.setTextHeader(R.string.huongdanbanhang);
		header.showButton(true, false);
		header.setButtonLeftImage(true, R.drawable.btn_back);
		header.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, null, null, null);
		dichvu_list.setAdapter(new HuongDanBanHangAdapter(getActivity(), cursor));
		
//		Bundle bundle = new Bundle();
//		bundle.putString("type", 4 + "");
//		getmImusicService().execute(RequestMethod.GET, API.API_R010, bundle, new IContsCallBack() {
//
//			@Override
//			public void onSuscess(JSONObject response) {
//				String guide_text = getString(R.string.nodata);
//				try {
//					guide_text = response.getString("guide_text");
//				} catch (JSONException e) {
//				}
//				dichvu_list.setTextNoData(true, guide_text);
//			}
//
//			@Override
//			public void onStart() {
//
//			}
//
//			@Override
//			public void onError(String message) {
//				dichvu_list.setTextNoData(true, message);
//			}
//
//			@Override
//			public void onError() {
//				onError("onError");
//			}
//		});

		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}