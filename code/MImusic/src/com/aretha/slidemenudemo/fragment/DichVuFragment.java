package com.aretha.slidemenudemo.fragment;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.DichVuAdapter;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.LoadingView;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
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
	public void onCLickButtonLeft() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuLeft();
	}

	@Override
	public void onCLickButtonRight() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuRight();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dichvu, null);
		createHeader(getString(R.string.dichvu), true, true);

		((LinearLayout) view.findViewById(R.id.header)).addView(getHeaderView());

		loadingView1 = (LoadingView) view.findViewById(R.id.loadingView1);
		dichvu_list = (ListView) view.findViewById(R.id.dichvu_list);
		dichvu_list.setOnItemClickListener(this);

		callSHowData();
		Conts.showView(loadingView1, false);
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