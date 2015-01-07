package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.DichVuAdapter;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.view.LoadingView;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class DichVuFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private DichVuAdapter adapter;

	@Override
	public void onResume() {
		super.onResume();
		callSHowData();
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	private ListView dichvu_list;
	private LoadingView loadingView1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dichvu, null);
		loadingView1 = (LoadingView) view.findViewById(R.id.loadingView1);
		dichvu_list = (ListView) view.findViewById(R.id.dichvu_list);
		dichvu_list.setOnItemClickListener(this);
		Bundle bundle = new Bundle();

		getmImusicService().execute(RequestMethod.GET, API.API_R004, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				Conts.showView(loadingView1, true);
			}

			@Override
			public void onSuscess(JSONObject response) {
				Conts.showView(loadingView1, false);
//				try {
//					JSONArray jsonArray = response.getJSONArray("data");
//					for (int i = 0; i < jsonArray.length(); i++) {
//						JSONObject jsonObject = jsonArray.getJSONObject(i);
//						ContentValues contentValues = new ContentValues();
//						contentValues.put(DichVu.ID, jsonObject.getString(DichVu.ID));
//						contentValues.put(DichVu.service_name, jsonObject.getString(DichVu.service_name));
//						contentValues.put(DichVu.service_code, jsonObject.getString(DichVu.service_code));
//						contentValues.put(DichVu.service_icon, jsonObject.getString(DichVu.service_icon));
//						contentValues.put(DichVu.service_content, jsonObject.getString(DichVu.service_content));
//						contentValues.put(DichVu.service_price, jsonObject.getString(DichVu.service_price));
//						contentValues.put(DichVu.service_status, jsonObject.getString(DichVu.service_status));
//
//						String selection = String.format("%s='%s'", DichVu.ID, jsonObject.getString(DichVu.ID));
//						Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);
//
//						if (cursor != null && cursor.getCount() >= 1) {
//							cursor.close();
//							getActivity().getContentResolver().update(DichVu.CONTENT_URI, contentValues, selection, null);
//						} else {
//							getActivity().getContentResolver().insert(DichVu.CONTENT_URI, contentValues);
//						}
//					}
//				} catch (JSONException e) {
//				}
				callSHowData();
			}

			@Override
			public void onError(String message) {
				Conts.showView(loadingView1, false);
				Conts.toast(getActivity(), message);
			}

			@Override
			public void onError() {
				Conts.showView(loadingView1, false);
				Conts.toast(getActivity(), "onError");
			}
		});

		final EditText dichvu_edittext_search = (EditText) view.findViewById(R.id.dichvu_edittext_search);
		dichvu_edittext_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (adapter != null) {
					adapter.setTextSearch(dichvu_edittext_search.getText().toString().trim());
					adapter.notifyDataSetChanged();
				}
			}
		});
		return view;
	}

	private void callSHowData() {
		Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, null, null, null);
		if (cursor != null) {
			dichvu_list.setAdapter(adapter = new DichVuAdapter(getActivity(), cursor) {
				@Override
				public void moiDVChoNhieuNguoi(String id) {
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(id);
				}

				@Override
				public void dangKy(ContentValues values) {
					new DangKyDialog(getActivity(), values) {
						public void updateUiDangKy() {
							callSHowData();
						};
					}.show();
				}
			});
		}
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Conts.hiddenKeyBoard(getActivity());
		(((RootMenuActivity) getActivity())).gotoChiTietDichVu(parent, view, position, id);
	}
}