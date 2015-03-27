package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.datastore.HuongDanBanHangStore;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.MusicListView;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.viettel.vtt.vdealer.R;

public class HuongDanBanHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private HuongDanBanHangStore huongDanBanHangStore;
	private WebView web_huongdanbanhang;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		huongDanBanHangStore = new HuongDanBanHangStore(getActivity());
	}

	private MusicListView dichvu_list;
	private View loadingView;
	private String textHuongDanBanHang = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.huongdanbanhang, null);

		web_huongdanbanhang = (WebView) view.findViewById(R.id.web_huongdanbanhang);
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

		textHuongDanBanHang = huongDanBanHangStore.getHdbh();
		if (!Conts.isBlank(textHuongDanBanHang)) {
			dichvu_list.setTextNoDataX2(true, Html.fromHtml(textHuongDanBanHang));
			showTextHungDanBanHang(textHuongDanBanHang);
		}
		Bundle bundle = new Bundle();
		bundle.putString("type", 4 + "");
		execute(RequestMethod.GET, API.API_R010, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				if (getActivity() != null) {
					textHuongDanBanHang = huongDanBanHangStore.getHdbh();
					if (!Conts.isBlank(textHuongDanBanHang)) {
						dichvu_list.setTextNoDataX2(true, Html.fromHtml(textHuongDanBanHang));
						showTextHungDanBanHang(textHuongDanBanHang);
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
				textHuongDanBanHang = huongDanBanHangStore.getHdbh();
				if (!Conts.isBlank(textHuongDanBanHang)) {
					dichvu_list.setTextNoDataX2(true, Html.fromHtml(textHuongDanBanHang));
					showTextHungDanBanHang(textHuongDanBanHang);
				} else {
					dichvu_list.setTextNoData(true, message);
				}
				Conts.showView(loadingView, false);
			}

		});

		return view;
	}

	private void showTextHungDanBanHang(String textHuongDanBanHang) {
		if (!Conts.isBlank(textHuongDanBanHang)) {
			String data = String.format("<font color='#353f45' size='-0.5' style='line-height: 1.5;'>%s</font>",textHuongDanBanHang);
			web_huongdanbanhang.loadDataWithBaseURL(null, data, "text/html", "UTF-8", null);
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}