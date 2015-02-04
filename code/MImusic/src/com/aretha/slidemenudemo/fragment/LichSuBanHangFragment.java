package com.aretha.slidemenudemo.fragment;

import java.util.Calendar;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IShowDateDialog;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
		//updateTrangThai();

		// lsbt_trangthai.setText(R.string.tatca);
		// lsbt_dichvu.setText(R.string.tatca);
		return view;
	}

	protected void gotoChiTietLichSuBanHang() {

		Bundle bundle = new Bundle();
		bundle.putString("from", lsbt_tu.getText().toString());
		bundle.putString("to", lsbt_den.getText().toString());
		bundle.putString("service", idDv);
		bundle.putString("status", indexTrangThai == 0 ? "" : ("" + indexTrangThai));
		bundle.putString("customer", lsbh_sdt.getText().toString().trim());
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

	// private String phoneContact = "";

	private void showDichvu() {
		// new DichVuDialog(getActivity(), idDv) {
		// @Override
		// public void sendData(ContentValues index) {
		// idDv = index.getAsString(DichVu.ID);
		// lsbt_dichvu.setText(index.getAsString(DichVu.service_name));
		// }
		// }.show();

		Intent intent = new Intent(getActivity(), RootMenuActivity.class);
		intent.putExtra("type", Conts.CHONDICHVU);
		startActivityForResult(intent, 20001);
		
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
//			phoneContact = data.getStringExtra(User.USER);
//			lsbh_sdt.setText(data.getStringExtra(User.NAME_CONTACT));
//
//			if (!Conts.isBlank(phoneContact)) {
//				lsbh_sdt.setText(phoneContact);
//			}
			lsbh_sdt.setText(data.getStringExtra(User.USER));
			

			Conts.getSDT(lsbh_sdt);
		} else if (requestCode == 20001 && resultCode == Activity.RESULT_OK) {
			idDv = data.getStringExtra(DichVu.ID);
			lsbt_dichvu.setText(data.getStringExtra(DichVu.service_name));
		}
	}

	private void showTuDialog() {
		String mdate = lsbt_tu.getText().toString();
		if (Conts.isBlank(mdate)) {
			Calendar dateCalendar = Calendar.getInstance();
			mdate = String.format("%s/%s/%s", ((dateCalendar.get(Calendar.DATE) < 10 ? "0" : "") + dateCalendar.get(Calendar.DATE)) + "",
					(((dateCalendar.get(Calendar.MONTH) + 1) < 10 ? "0" : "") + (dateCalendar.get(Calendar.MONTH) + 1)) + "", dateCalendar.get(Calendar.YEAR) + "");
		}
		// new LSBHDateDialog(getActivity(), mdate) {
		// @Override
		// public void sendData(String date, String month, String year) {
		// lsbt_tu.setText(String.format("%s/%s/%s", date, month, year));
		// }
		// }.show();

		Conts.showDateDialog(getActivity(), R.string.chonngay, mdate, new IShowDateDialog() {
			@Override
			public void onSend(String year, String month, String date) {
				lsbt_tu.setText(String.format("%s/%s/%s", date, month, year));
			}
		});
	}

	private void showDenDialog() {
		String mdate = lsbt_den.getText().toString();
		if (Conts.isBlank(mdate)) {
			Calendar dateCalendar = Calendar.getInstance();
			mdate = String.format("%s/%s/%s", ((dateCalendar.get(Calendar.DATE) < 10 ? "0" : "") + dateCalendar.get(Calendar.DATE)) + "",
					(((dateCalendar.get(Calendar.MONTH) + 1) < 10 ? "0" : "") + (dateCalendar.get(Calendar.MONTH) + 1)) + "", dateCalendar.get(Calendar.YEAR) + "");

		}

		Conts.showDateDialog(getActivity(), R.string.chonngay, mdate, new IShowDateDialog() {
			@Override
			public void onSend(String year, String month, String date) {
				lsbt_den.setText(String.format("%s/%s/%s", date, month, year));
			}
		});
	}

	@Override
	public void onClick(View v) {
	}

	protected void showTrangThai() {

		final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Holo_Dialog);
		dialog.setContentView(R.layout.trangthai_layout);
		dialog.setTitle(getString(R.string.chontrangthai));
		dialog.findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		ListView datePicker1 = (ListView) dialog.findViewById(R.id.datePicker1);
		datePicker1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getActivity().getResources().getStringArray(R.array.ltrangthai)));
		datePicker1.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dialog.dismiss();
				indexTrangThai = position;
				updateTrangThai();
			}
		});
		dialog.show();

	}

	protected void updateTrangThai() {
		lsbt_trangthai.setText(getActivity().getResources().getStringArray(R.array.ltrangthai)[indexTrangThai]);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}