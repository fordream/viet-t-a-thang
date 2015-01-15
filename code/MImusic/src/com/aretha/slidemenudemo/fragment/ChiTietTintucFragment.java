package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.TinTucKhacItemView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChiTietTintucFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LinearLayout chitiet_tintuc_tintuckhac_list;
	private TextView chitiettintuc_item_tv_name, chitiettintuc_item_tv_date, home_item_right_control_2_tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitiet_tintuc, null);
		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.chitiettintuc_headerview);
		chitiettintuc_headerview.setTextHeader(R.string.chitiettintuc);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.btn_back);
		chitiet_tintuc_tintuckhac_list = (LinearLayout) view.findViewById(R.id.chitiet_tintuc_tintuckhac_list);

		// for (int i = 0; i < 10; i++) {
		// chitiet_tintuc_tintuckhac_list.addView(new
		// TinTucKhacItemView(getActivity()));
		// }

		chitiettintuc_item_tv_name = (TextView) view.findViewById(R.id.chitiettintuc_item_tv_name);
		chitiettintuc_item_tv_date = (TextView) view.findViewById(R.id.chitiettintuc_item_tv_date);
		home_item_right_control_2_tv = (TextView) view.findViewById(R.id.home_item_right_control_2_tv);

		chitiettintuc_item_tv_name.setText("");
		chitiettintuc_item_tv_date.setText("");
		home_item_right_control_2_tv.setText("");

		callApi(getArguments());
		return view;
	}

	private void callApi(Bundle arguments) {

		execute(RequestMethod.POST, API.API_R028, arguments, new IContsCallBack() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError() {
				onError("fail");
			}

			@Override
			public void onError(String message) {
				chitiettintuc_item_tv_name.setText(message);
				chitiettintuc_item_tv_date.setText("");
				home_item_right_control_2_tv.setText("");
			}

			@Override
			public void onSuscess(JSONObject response) {
				chitiettintuc_item_tv_name.setText(Conts.getString(response, "title"));
				chitiettintuc_item_tv_date.setText(Conts.getString(response, "public_time"));
				home_item_right_control_2_tv.setText(Conts.getString(response, "content"));
			}
		});

		execute(RequestMethod.POST, API.API_R029, arguments, new IContsCallBack() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError() {
			}

			@Override
			public void onError(String message) {
			}

			@Override
			public void onSuscess(JSONObject response) {
				chitiet_tintuc_tintuckhac_list.removeAllViews();
				try {
					JSONArray jsonArray = response.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						final JSONObject object = jsonArray.getJSONObject(i);
						TinTucKhacItemView tinTucKhacItemView = new TinTucKhacItemView(getActivity());
						chitiet_tintuc_tintuckhac_list.addView(tinTucKhacItemView);
						tinTucKhacItemView.setData(object);

						tinTucKhacItemView.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								Bundle bundle = new Bundle();
								bundle.putString("news_id", Conts.getString(object, "news_id"));
								callApi(bundle);
							}
						});
					}

				} catch (JSONException e) {
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}