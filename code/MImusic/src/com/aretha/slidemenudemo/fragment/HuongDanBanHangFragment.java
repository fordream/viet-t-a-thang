package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.HuongDanBanHang;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.MusicListView;
import android.os.Bundle;
import android.text.Html;
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
	private View loadingView;
	private String textHuongDanBanHang = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.huongdanbanhang, null);
		loadingView = view.findViewById(R.id.loadingView1);
		Conts.showView(loadingView, false);
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

		textHuongDanBanHang = HuongDanBanHang.getTextHuongDanBanHang(getActivity());
		if (!Conts.isBlank(textHuongDanBanHang)) {
			dichvu_list.setTextNoDataX(true, Html.fromHtml(textHuongDanBanHang));
		}
		Bundle bundle = new Bundle();
		bundle.putString("type", 4 + "");
		execute(RequestMethod.GET, API.API_R010, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				if (getActivity() != null) {
					textHuongDanBanHang = HuongDanBanHang.getTextHuongDanBanHang(getActivity());
					if (!Conts.isBlank(textHuongDanBanHang)) {
						dichvu_list.setTextNoDataX(true, Html.fromHtml(textHuongDanBanHang));
					}
					Conts.showView(loadingView, false);
				}
			}

			@Override
			public void onStart() {
				if (Conts.isBlank(textHuongDanBanHang)) {
					Conts.showView(loadingView, true);
				}
			}

			@Override
			public void onError(String message) {
				textHuongDanBanHang = HuongDanBanHang.getTextHuongDanBanHang(getActivity());
				if (!Conts.isBlank(textHuongDanBanHang)) {
					dichvu_list.setTextNoDataX(true, Html.fromHtml(textHuongDanBanHang));
				} else {
					dichvu_list.setTextNoData(true, message);
				}
				Conts.showView(loadingView, false);
			}

			@Override
			public void onError() {
				onError("onError");
			}
		});

		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}