package com.aretha.slidemenudemo.fragment;

import java.util.Calendar;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.base.diablog.DichVuDialog;
import vnp.com.mimusic.base.diablog.LSBHDateDialog;
import vnp.com.mimusic.base.diablog.TrangThaiDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

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
				gotoChiTietLichSuBanHang();
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
		lsbh_sdt = (TextView) view.findViewById(R.id.lsbh_sdt);
		updateTrangThai();
		lsbt_dichvu.setText(getActivity().getString(R.string.tatca));
		lsbh_sdt.setText(getActivity().getString(R.string.tatca));
		return view;
	}

	protected void gotoChiTietLichSuBanHang() {

		Bundle bundle = new Bundle();
		bundle.putString("from", lsbt_tu.getText().toString());
		bundle.putString("to", lsbt_den.getText().toString());
		bundle.putString("service", idDv);
		bundle.putString("status", indexTrangThai == 0 ? "" : ("" + indexTrangThai));
		bundle.putString("customer", phoneContact);
		(((RootMenuActivity) getActivity())).gotoChiTietLichSuBanHang(bundle);
	}

	private TextView lsbt_trangthai, lsbt_tu, lsbt_den, lsbt_dichvu, lsbh_sdt;

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
	private String phoneContact = "";

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
		Intent intent = new Intent(getActivity(), RootMenuActivity.class);
		intent.putExtra("type", Conts.CHONSODIENTHOAIFRAGMENT);
		startActivityForResult(intent, 2000);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 2000 && resultCode == Activity.RESULT_OK) {
			phoneContact = data.getStringExtra(User.USER);
			lsbh_sdt.setText(data.getStringExtra(User.NAME_CONTACT));
		} else {

		}
	}

	private void showTuDialog() {
		String mdate = lsbt_tu.getText().toString();
		if (Conts.isBlank(mdate)) {
			Calendar dateCalendar = Calendar.getInstance();
			mdate = String.format("%s/%s/%s", ((dateCalendar.get(Calendar.DATE) < 10 ? "0" : "") + dateCalendar.get(Calendar.DATE)) + "",
					(((dateCalendar.get(Calendar.MONTH) + 1) < 10 ? "0" : "") + (dateCalendar.get(Calendar.MONTH) + 1)) + "", dateCalendar.get(Calendar.YEAR) + "");
		}
		new LSBHDateDialog(getActivity(), mdate) {
			@Override
			public void sendData(String date, String month, String year) {
				lsbt_tu.setText(String.format("%s/%s/%s", date, month, year));
			}
		}.show();
	}

	private void showDenDialog() {
		String mdate = lsbt_den.getText().toString();
		if (Conts.isBlank(mdate)) {
			Calendar dateCalendar = Calendar.getInstance();
			mdate = String.format("%s/%s/%s", ((dateCalendar.get(Calendar.DATE) < 10 ? "0" : "") + dateCalendar.get(Calendar.DATE)) + "",
					(((dateCalendar.get(Calendar.MONTH) + 1) < 10 ? "0" : "") + (dateCalendar.get(Calendar.MONTH) + 1)) + "", dateCalendar.get(Calendar.YEAR) + "");

		}
		new LSBHDateDialog(getActivity(), mdate) {
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