package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.BangXepHangHeaderView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.BangXepHangHeaderView.BangXepHangHeaderInterface;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BangXepHangFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private LoadingView bxhLoadingView;
	private View view;
	private String noDataText = "";
	private boolean successRequest = false;
	private String type = "";

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private vnp.com.mimusic.view.MusicListView bangxephang_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.bangxephang, null);
		bangxephang_list = (vnp.com.mimusic.view.MusicListView) view.findViewById(R.id.bangxephang_list);
		BangXepHangHeaderView bangxephang_bangxephangheader = (BangXepHangHeaderView) view.findViewById(R.id.bangxephang_bangxephangheader);
		bangxephang_bangxephangheader.setBangXepHangHeaderInterface(new BangXepHangHeaderInterface() {

			@Override
			public void callSoLuong(boolean isSoluong) {
				// TODO
				callData(isSoluong);
			}
		});
		callData(true);
		return view;
	}

	boolean displaySoLuong = false;

	private void callData(boolean b) {
		displaySoLuong = b;
		bangxephang_list.setText(false, "");
		if (b) {
			noDataText = getResources().getString(R.string.bangxephang_so_luong_no_data);
			type = "2";// theo so luong
		} else {
			noDataText = getResources().getString(R.string.bangxephang_doanh_thu_no_data);
			type = "1";// theo doanh thu
		}
		BangXepHangAdaper adaper = new BangXepHangAdaper(getActivity(), new JSONArray());
		bangxephang_list.setAdapter(adaper);
		// get data for list
		bxhLoadingView = (LoadingView) view.findViewById(R.id.bxhLoadingView);
		Bundle bxhParamBundle = new Bundle();
		bxhParamBundle.putString("type", type);
		if (!successRequest) {
			execute(RequestMethod.GET, API.API_R024, bxhParamBundle, new IContsCallBack() {
				@Override
				public void onSuscess(JSONObject response) {
					successRequest = true;
					try {
						JSONArray jsonArray = response.getJSONArray("data");
						if (jsonArray.length() == 0) {
							jsonArray = sampleData();
						}
						((BangXepHangAdaper) bangxephang_list.getAdapter()).setType(type);
						((BangXepHangAdaper) bangxephang_list.getAdapter()).setJSOnArray(jsonArray);
						((BangXepHangAdaper) bangxephang_list.getAdapter()).notifyDataSetChanged();

						if (jsonArray.length() == 0 && displaySoLuong) {
							bangxephang_list.setTextNoData(true, noDataText);
						} else {
							bangxephang_list.setOnItemClickListener(BangXepHangFragment.this);
						}
					} catch (Exception exception) {

					}
					Conts.showView(bxhLoadingView, false);
				}

				@Override
				public void onStart() {
					Conts.showView(bxhLoadingView, true);
				}

				@Override
				public void onError(String message) {
					Conts.showView(bxhLoadingView, false);
					Conts.toast(getActivity(), message);
					try {
						BangXepHangAdaper adapter = new BangXepHangAdaper(getActivity(), new JSONArray());
						bangxephang_list.setAdapter(null);
						adapter.setType(type);
						JSONArray jsonArray = sampleData();
						adapter.setJSOnArray(jsonArray);
						bangxephang_list.setAdapter(adapter);
						adapter.notifyDataSetChanged();

						if (jsonArray.length() == 0 && displaySoLuong) {
							bangxephang_list.setTextNoData(true, noDataText);
						} else {
							bangxephang_list.setOnItemClickListener(BangXepHangFragment.this);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					Toast.makeText(getActivity(), "API không hoạt động", Toast.LENGTH_LONG).show();
				}

				@Override
				public void onError() {
					onError("check network");
				}
			});
		}
	}

	private JSONArray sampleData() {
		// ///////sample data//////////////
		JSONArray jsonArray = new JSONArray();
		if (!displaySoLuong) {
			try {
				for (int i = 0; i < 10; i++) {
					JSONObject jo = new JSONObject();
					// id: của định danh của BXH
					// nickname(String): nickname của dealer
					// msisdn: số điện thoại của dealer
					// avatar: ảnh đại diện của dealer
					// quantity: số lượng đăng ký
					// commission: hoa hồng được nhận (doanh thu)
					jo.put("id", i);
					jo.put("nickname", "Anh");
					jo.put("msisdn", "0985123456");
					jo.put("avatar", R.drawable.bangxephang_avatar_example);
					jo.put("quantity", "100");
					jo.put("commission", "100000");
					jsonArray.put(jo);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return jsonArray;
		// ///////////////////////////////
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}