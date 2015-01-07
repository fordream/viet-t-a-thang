package com.aretha.slidemenudemo.fragment;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DateDialog;
import vnp.com.mimusic.base.diablog.DichVuDialog;
import vnp.com.mimusic.base.diablog.TrangThaiDialog;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LichSuBanHangFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.lichsubanhang, null);
		HeaderView header = (HeaderView) view.findViewById(R.id.lichsubanhang_header);
		header.setTextHeader(R.string.lichsubanhang);
		header.showButton(true, false);
		header.setButtonLeftImage(true, R.drawable.btn_back);
		header.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();

			}
		});

		view.findViewById(R.id.btn_lichsubanhang).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoChiTietLichSuBanHang();
			}
		});

		view.findViewById(R.id.lsbh_open_danhba).setOnClickListener(l);
		lsbt_tu = (TextView) view.findViewById(R.id.lsbt_tu);
		lsbt_tu.setOnClickListener(l);

		lsbt_den = (TextView) view.findViewById(R.id.lsbt_den);
		lsbt_den.setOnClickListener(l);

		lsbt_dichvu = (TextView) view.findViewById(R.id.lsbt_dichvu);
		lsbt_dichvu.setOnClickListener(l);

		lsbt_trangthai = (TextView) view.findViewById(R.id.lsbt_trangthai);
		lsbt_trangthai.setOnClickListener(l);

		updateTrangThai();
		lsbt_dichvu.setText(getActivity().getString(R.string.tatca));

		return view;
	}

	private TextView lsbt_trangthai, lsbt_tu, lsbt_den, lsbt_dichvu;

	private View.OnClickListener l = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.lsbt_tu) {
				showTuDialog();
			} else if (v.getId() == R.id.lsbt_den) {
				showDenDialog();
			} else if (v.getId() == R.id.lsbh_open_danhba) {
				showDanhBa();
			} else if (v.getId() == R.id.lsbt_dichvu) {
				showDichvu();
			} else if (v.getId() == R.id.lsbt_trangthai) {
				showTrangThai();
			}
		}
	};
	private int indexTrangThai = 0;
	private String idDv = "";

	private void showDichvu() {
		new DichVuDialog(getActivity(), idDv) {
			@Override
			public void sendData(ContentValues index) {
				idDv = index.getAsString(DichVu.ID);
				lsbt_dichvu.setText(index.getAsString(DichVu.service_name));
			}
		}.show();
	}

	private void showDanhBa() {

	}

	private void showTuDialog() {
		new DateDialog(getActivity(), lsbt_tu.getText().toString()) {

			@Override
			public void sendData(String date, String month, String year) {
				lsbt_tu.setText(String.format("%s/%s/%s", date, month, year));
			}
		}.show();
	}

	private void showDenDialog() {
		new DateDialog(getActivity(), lsbt_den.getText().toString()) {
			@Override
			public void sendData(String date, String month, String year) {
				lsbt_den.setText(String.format("%s/%s/%s", date, month, year));
			}
		}.show();
	}

	@Override
	public void onClick(View v) {
	}

	protected void showTrangThai() {
		TrangThaiDialog thaiDialog = new TrangThaiDialog(getActivity(), indexTrangThai) {

			@Override
			public void sendData(int index) {
				indexTrangThai = index;
				updateTrangThai();
			}
		};

		thaiDialog.show();
	}

	protected void updateTrangThai() {
		lsbt_trangthai.setText(getActivity().getResources().getStringArray(R.array.ltrangthai)[indexTrangThai]);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}