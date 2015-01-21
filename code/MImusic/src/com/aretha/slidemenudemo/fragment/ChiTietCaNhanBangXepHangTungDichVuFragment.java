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
import vnp.com.mimusic.view.LoadingView;
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
	private LoadingView loadingView;
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.chitietcanhanbangxephangtungdichvu, null);
		loadingView = (LoadingView)view.findViewById(R.id.loadingView1);
		
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
		
		soGD.setText(getArguments().getString("quantity"));
		soHH.setText(String.format(getString(R.string.format_tien),getArguments().getString("commission")));
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
				Conts.showView(loadingView, true);
			}

			@Override
			public void onError() {
				onError("erorr");
			}

			@Override
			public void onError(String message) {
				Conts.showDialogThongbao(getActivity(), message);
				Conts.showView(loadingView, false);
			}

			@Override
			public void onSuscess(JSONObject response) {
				Conts.showView(loadingView, false);
				// Conts.showDialogThongbao(getActivity(), response.toString());
//				// so giao dich thanh cong
//				String exchange_number = Conts.getString(response, "exchange_number");
//				// so giao dich thanh cong trong thang
//				String exchange_number_month = Conts.getString(response, "exchange_number_month");
//				// poundage tien hoa hong
//				String poundage = Conts.getString(response, "poundage");
//				// poundage_month tien hoa hong trong thang
//				String poundage_month = Conts.getString(response, "poundage_month");

				// quantity_in_duration
				// commission_in_duration

				// soGD.setText(exchange_number);
				// soHH.setText(poundage);

				String quantity_in_duration = Conts.getString(response, "quantity_in_duration");
				
				String commission_in_duration = Conts.getString(response, "commission_in_duration");
				
				soGDTrongThang.setText(quantity_in_duration);
				soHHTrongThang.setText(String.format(getString(R.string.format_tien),commission_in_duration+""));
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

}