package com.aretha.slidemenudemo.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.QuyDinhBanHangAdapter;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HuongDanBanHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.huongdanbanhang, null);

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
		ListView dichvu_list = (ListView) view.findViewById(R.id.quydinhbanhang_list);
		dichvu_list.setOnItemClickListener(this);

		Bundle bundle = new Bundle();
		bundle.putString("type", 4 + "");
		getmImusicService().execute(RequestMethod.GET, API.API_R010, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				try {
					String guide_text = response.getString("guide_text");
				} catch (JSONException e) {
				}
			}

			@Override
			public void onStart() {

			}

			@Override
			public void onError(String message) {
				Conts.toast(getActivity(), message);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "onError");
			}
		});
		// Cursor cursor =
		// getActivity().getContentResolver().query(DichVu.CONTENT_URI, null,
		// null, null, null);
		// if (cursor != null) {
		// dichvu_list.setAdapter(new QuyDinhBanHangAdapter(getActivity(),
		// cursor));
		// }

		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}