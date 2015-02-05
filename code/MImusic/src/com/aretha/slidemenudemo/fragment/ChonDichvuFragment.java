package com.aretha.slidemenudemo.fragment;

import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.ChonDichVuAdapter;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.MusicListView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.ListView;

import com.vnp.core.scroll.VasChonDichvuScrollListView;

public class ChonDichvuFragment extends BaseFragment implements android.view.View.OnClickListener {

	private vnp.com.mimusic.view.MusicListView bangxephang_list;
	private ChonDichVuAdapter adaper;
	private View view;

	private HeaderView headerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.chondv_dialog, null);
		headerView = new HeaderView(getActivity());
		headerView.showHeadderSearch();
		bangxephang_list = (MusicListView) view.findViewById(R.id.list);

		bangxephang_list.addHeaderView(headerView);
		bangxephang_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Intent intent = new Intent();
				if (adaper != null) {
					Cursor cursor = (Cursor) adaper.getItem(position - 1);
					intent.putExtra(DichVu.ID, cursor.getString(cursor.getColumnIndex(DichVu.ID)));
					intent.putExtra(DichVu.service_name, cursor.getString(cursor.getColumnIndex(DichVu.service_name)));
				}
				getActivity().setResult(Activity.RESULT_OK, intent);
				getActivity().onBackPressed();
			}
		});
		menu_right_editext = (EditText) view.findViewById(R.id.dichvu_edittext_search);
		HeaderView chitietdichvu_headerview = (HeaderView) view.findViewById(R.id.chitietdichvu_headerview);
		chitietdichvu_headerview.setTextHeader(R.string.timkiemdichvu);
		chitietdichvu_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitietdichvu_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);
		chitietdichvu_headerview.findViewById(R.id.header_btn_right__done).setVisibility(View.INVISIBLE);
		chitietdichvu_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(DichVu.ID, "");
				intent.putExtra(DichVu.service_name, getString(R.string.tatca));
				getActivity().setResult(Activity.RESULT_OK, intent);
				getActivity().onBackPressed();
			}
		});

		callSHowData();
		menu_right_editext.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				try {
					adaper.setTextSearch(menu_right_editext.getText().toString());
					adaper.notifyDataSetChanged();
				} catch (Exception exception) {

				}
			}
		});

		ListView listViews[] = new ListView[] { bangxephang_list };
		new VasChonDichvuScrollListView(chitietdichvu_headerview, headerView, listViews, getActivity());
		return view;
	}

	private void callSHowData() {
		Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, null, null, null);

		if (cursor != null) {
			adaper = new ChonDichVuAdapter(getActivity(), cursor) {

				@Override
				public void dangKy(ContentValues values) {

				}

				@Override
				public void moiDVChoNhieuNguoi(String id) {

				}

			};
			bangxephang_list.setAdapter(adaper);
		}
	}

	private EditText menu_right_editext;

	@Override
	public void onClick(View v) {
	}

}