package com.aretha.slidemenudemo.fragment;

import java.util.ArrayList;
import java.util.List;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MoiDvChoNhieuNguoiAdaper;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.MusicListView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

public class ChonSoDienThoaiFragment extends BaseFragment implements android.view.View.OnClickListener {

	private vnp.com.mimusic.view.MusicListView bangxephang_list;
	private MoiDvChoNhieuNguoiAdaper adaper;
	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.choncontact_dialog, null);
		bangxephang_list = (MusicListView) view.findViewById(R.id.bangxephang_list);

		menu_right_editext = (EditText) view.findViewById(R.id.menu_right_editext);
		HeaderView chitietdichvu_headerview = (HeaderView) view.findViewById(R.id.chitietdichvu_headerview);
		chitietdichvu_headerview.setTextHeader(R.string.chonsdt);
		chitietdichvu_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitietdichvu_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);
		chitietdichvu_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
					adaper.setTextSearch(menu_right_editext.toString());
					adaper.notifyDataSetChanged();
				} catch (Exception exception) {

				}
			}
		});
		return view;
	}

	private void callSHowData() {
		String where = String.format("%s = '0'", User.STATUS);
		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, where, null, null);

		if (cursor != null) {
			adaper = new MoiDvChoNhieuNguoiAdaper(getActivity(), cursor, "") {

				@Override
				public void addOrRemove(String _id, boolean isAdd) {

				}

				@Override
				public void addOrRemoveSdt(boolean isAdd, String sdt) {

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