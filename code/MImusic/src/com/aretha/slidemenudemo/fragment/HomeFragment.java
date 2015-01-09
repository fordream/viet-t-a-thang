package com.aretha.slidemenudemo.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.HomeAdapter;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private HomeAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);

		View home_header = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_header, null);
		home_header.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		ListView menu_left_list = (ListView) view.findViewById(R.id.home_list);
		//menu_left_list.addHeaderView(home_header);
		menu_left_list.setOnItemClickListener(this);
		Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, null, null, null);
		if (cursor != null) {
			adapter = new HomeAdapter(getActivity(), cursor) {

				@Override
				public void updateUI() {

				}

				@Override
				public void moiDVChoNhieuNguoi(ContentValues contentValues) {
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(contentValues.getAsString(DichVu.ID));
				}
			};
			menu_left_list.setAdapter(adapter);
		}

		return view;
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHome(parent, view, position, id);
	}
}