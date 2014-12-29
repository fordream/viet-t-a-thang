package com.aretha.slidemenudemo.fragment;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ThongTinCaNhanFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.thongtincanhan, null);
		HeaderView headerView = (HeaderView) view.findViewById(R.id.activity_login_header);
		headerView.setTextHeader(R.string.thongtincanhan);
		headerView.setButtonLeftImage(true, R.drawable.btn_back);
		headerView.setButtonRightImage(true, R.drawable.thongtincanhan_right);

		headerView.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		headerView.findViewById(R.id.header_btn_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoChinhsuaThongTinCaNhan();
			}
		});
		showData(view);
		return view;
	}

	private void showData(View view) {
		if (view == null)
			view = getView();
		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s = '1'", User.STATUS), null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();

			((TextView) view.findViewById(R.id.text_name)).setText(Conts.getName(cursor));
			((TextView) view.findViewById(R.id.text_bidanh)).setText(cursor.getString(cursor.getColumnIndex(User.BIDANH)));
			((TextView) view.findViewById(R.id.text_ngaysinh)).setText(cursor.getString(cursor.getColumnIndex(User.NGAYSINH)));
			((TextView) view.findViewById(R.id.text_diachi)).setText(cursor.getString(cursor.getColumnIndex(User.DIACHI)));

			((TextView) view.findViewById(R.id.text_sogiaodichthanhcongtrongthang)).setText(getText(cursor.getString(cursor.getColumnIndex(User.SOGIAODICHTHANHCONGTRONGTHANG))));
			((TextView) view.findViewById(R.id.text_sogiaodichthanhcong)).setText(getText(cursor.getString(cursor.getColumnIndex(User.SOGIAODICHTHANHCONG))));
			((TextView) view.findViewById(R.id.text_sotienhoahongtrongthang)).setText(getText(cursor.getString(cursor.getColumnIndex(User.SOTIENHOAHONGTRONGTHANG))) + getString(R.string.vnd));
			((TextView) view.findViewById(R.id.text_sotienhoahong)).setText(getText(cursor.getString(cursor.getColumnIndex(User.SOTIENHOAHONG)))+ getString(R.string.vnd));

			cursor.close();
		}
	}

	private String getText(String str) {
		if (str == null)
			return "0";

		return str;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}