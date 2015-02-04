package com.aretha.slidemenudemo.fragment;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.ChonSoDienThoaiAdaper;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
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

import com.vnp.core.scroll.VasChonSdtScrollListView;

public class ChonSoDienThoaiFragment extends BaseFragment implements android.view.View.OnClickListener {

	private com.woozzu.android.widget.IndexableListView bangxephang_list;
	private ChonSoDienThoaiAdaper adaper;
	private View view;
	private HeaderView headerView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.chonsodienthoai, null);
		headerView = new HeaderView(getActivity());
		headerView.showHeadderSearch();
		bangxephang_list = (com.woozzu.android.widget.IndexableListView) view.findViewById(R.id.list);
		bangxephang_list.addHeaderView(headerView);

		bangxephang_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent();
				if (adaper != null) {
					Cursor cursor = (Cursor) adaper.getItem(position - 1);
					intent.putExtra(User.NAME_CONTACT, cursor.getString(cursor.getColumnIndex(User.NAME_CONTACT)));
					intent.putExtra(User.USER, cursor.getString(cursor.getColumnIndex(User.USER)));
				}
				getActivity().setResult(Activity.RESULT_OK, intent);
				getActivity().onBackPressed();
			}
		});
		menu_right_editext = (EditText) view.findViewById(R.id.dichvu_edittext_search);
		HeaderView chitietdichvu_headerview = (HeaderView) view.findViewById(R.id.chitietdichvu_headerview);
		chitietdichvu_headerview.setTextHeader(R.string.timkiemdanhba);
		chitietdichvu_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitietdichvu_headerview.setButtonRightImage(false, R.drawable.chititetdichvu_right);

		chitietdichvu_headerview.findViewById(R.id.header_btn_right__done).setVisibility(View.INVISIBLE);

		chitietdichvu_headerview.findViewById(R.id.header_btn_right__done).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(User.NAME_CONTACT, getString(R.string.tatca));
				intent.putExtra(User.USER, "");
				getActivity().setResult(Activity.RESULT_OK, intent);
				getActivity().onBackPressed();
			}
		});

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
					adaper.setTextSearch(menu_right_editext.getText().toString());
					// adaper.notifyDataSetChanged();
				} catch (Exception exception) {

				}
			}
		});

		new VasChonSdtScrollListView(chitietdichvu_headerview, headerView, bangxephang_list);
		return view;
	}

	private void callSHowData() {
		String where = String.format("%s = '0'", User.STATUS);
		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, where, null, User.NAME_CONTACT);

		if (cursor != null) {
			adaper = new ChonSoDienThoaiAdaper(getActivity(), cursor);
			bangxephang_list.setAdapter(adaper);
		}
	}

	private EditText menu_right_editext;

	@Override
	public void onClick(View v) {
	}

}