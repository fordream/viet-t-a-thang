package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.datastore.TintucStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
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

	private LoadingView loadingView;
	private LinearLayout chitiet_tintuc_tintuckhac_list;
	private TextView chitiettintuc_item_tv_name, chitiettintuc_item_tv_date, home_item_right_control_2_tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitiet_tintuc, null);

		loadingView = (LoadingView) view.findViewById(R.id.loadingView1);
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

		chitiettintuc_item_tv_name = (TextView) view.findViewById(R.id.chitiettintuc_item_tv_name);
		chitiettintuc_item_tv_date = (TextView) view.findViewById(R.id.chitiettintuc_item_tv_date);
		home_item_right_control_2_tv = (TextView) view.findViewById(R.id.home_item_right_control_2_tv);

		chitiettintuc_item_tv_name.setText("");
		chitiettintuc_item_tv_date.setText("");
		home_item_right_control_2_tv.setText("");

		callApi(getArguments());
		return view;
	}

	private boolean updateTintucUI(String id) {
		boolean needLoad = false;
		// Cursor cursor = TinTuc.queryFromId(getActivity(), id);
		if (getActivity() != null) {
			JSONObject cursor = new TintucStore(getActivity()).getJsonById(id);
			// if (cursor != null) {
			// if (cursor.moveToNext()) {
			chitiettintuc_item_tv_name.setText(Conts.getString(cursor, TintucStore.title));
			chitiettintuc_item_tv_date.setText(Conts.getString(cursor, TintucStore.public_time));
			home_item_right_control_2_tv.setText(Conts.getString(cursor, TintucStore.header));
			// needLoad = true;
			// }
			// /cursor.close();
			// }
			if (!Conts.isBlank(Conts.getString(cursor, TintucStore.title))) {
				needLoad = true;
			}
		}
		return needLoad;
	}

	private void callApi(Bundle arguments) {
		final String id = arguments.getString("news_id");
		boolean needLoad = !updateTintucUI(id);

		if (needLoad)
			executeHttps(RequestMethod.GET, API.API_R028, arguments, new IContsCallBack() {
				@Override
				public void onSuscess(JSONObject response) {
					Conts.showView(loadingView, false);
					updateTintucUI(id);
				}

				@Override
				public void onStart() {
					Conts.showView(loadingView, true);
				}

				@Override
				public void onError(String message) {
					Conts.showView(loadingView, false);
					Conts.showDialogThongbao(getActivity(), message);
				}

			});

		executeHttps(RequestMethod.GET, API.API_R029, arguments, new IContsCallBack() {
			@Override
			public void onStart() {
				chitiet_tintuc_tintuckhac_list.removeAllViews();
			}

			@Override
			public void onError(String message) {
			}

			@Override
			public void onSuscess(JSONObject response) {
				try {
					chitiet_tintuc_tintuckhac_list.removeAllViews();
					JSONArray jsonArray = response.getJSONArray("data");
					for (int i = 0; i < jsonArray.length(); i++) {
						final JSONObject object = jsonArray.getJSONObject(i);
						TinTucKhacItemView tinTucKhacItemView = new TinTucKhacItemView(getActivity());
						chitiet_tintuc_tintuckhac_list.addView(tinTucKhacItemView);
						tinTucKhacItemView.setData(object);
						tinTucKhacItemView.setData(i);
						tinTucKhacItemView.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								Bundle bundle = new Bundle();
								bundle.putString("news_id", Conts.getString(object, "id"));
								callApi(bundle);
							}
						});
					}
				} catch (Exception e) {
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