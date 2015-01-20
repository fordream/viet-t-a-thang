package com.aretha.slidemenudemo.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.BangXepHangItemView;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChiTietCaNhanBangXepHangTungDichVuFragment extends BaseFragment implements View.OnClickListener {
	private TextView soGDTrongThang, soGD, soHHTrongThang, soHH, bangxephangItemTvStt;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietcanhanbangxephangtungdichvu, null);
		BangXepHangItemView bXHItemView = (BangXepHangItemView) view.findViewById(R.id.bangxephangitemview);
		LinearLayout blockLayout = (LinearLayout) bXHItemView.findViewById(R.id.bangxephang_block);
		blockLayout.setVisibility(View.GONE);
		LinearLayout soLuongLayout = (LinearLayout) bXHItemView.findViewById(R.id.bxhSoLuongLayout);
		soLuongLayout.setVisibility(View.VISIBLE);
		LinearLayout doanhThuLayout = (LinearLayout) bXHItemView.findViewById(R.id.bxhDoanhThuLayout);
		doanhThuLayout.setVisibility(View.VISIBLE);

		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.chitietcanhanbangxephangtungdichvu_header);
		chitiettintuc_headerview.setTextHeader(R.string.chitietcanhanbangxephang);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		// get elements to set value when callAPI successfully
		bangxephangItemTvStt = (TextView) bXHItemView.findViewById(R.id.bangxephang_item_tv_stt);
		bangxephangItemTvStt.setText(getArguments().getString("position"));

		bXHItemView.setDataBundle(getArguments());

		soGDTrongThang = (TextView) view.findViewById(R.id.soGDTrongThang);
		soGD = (TextView) view.findViewById(R.id.soGD);
		soHHTrongThang = (TextView) view.findViewById(R.id.soHHTrongThang);
		soHH = (TextView) view.findViewById(R.id.soHH);

		soGDTrongThang.setText("");
		soGD.setText("");
		soHHTrongThang.setText("");
		soHH.setText("");

		callApi(getArguments());

		return view;
	}

	private void callApi(Bundle arguments) {
		Bundle bundle = new Bundle();
		bundle.putString("ranking_id", arguments.getString("ranking_id"));
		bundle.putString("type", arguments.getString("mtype"));

		executeHttps(RequestMethod.GET, API.API_R025, bundle, new IContsCallBack() {
			@Override
			public void onStart() {

			}

			@Override
			public void onError() {

				onError("erorr");
			}

			@Override
			public void onError(String message) {
				Conts.showDialogThongbao(getActivity(), message);
			}

			@Override
			public void onSuscess(JSONObject response) {
				//Conts.showDialogThongbao(getActivity(), response.toString());
				try {
					String STT = "";// truyền từ danh sách vào
					String imagePath = response.getString("avatar");
					String name = response.getString("nickname");
					String soLuong = response.getString("quantity");
					String doanhThu = response.getString("commission");
					String soGDThanhCongTrongThang = response.getString("commission");
					String soGDThanhCong = response.getString("commission");
					String soTienHoaHongTrongThang = response.getString("commission");
					String soTienHoaHong = response.getString("commission");
					// set value for element of form
				} catch (JSONException e) {
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

}