package com.aretha.slidemenudemo.fragment;

import vnp.com.db.Recomment;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MenuRightAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;

public class MoiThanhVienFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private MenuRightAdaper adapter;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moithanhvien, null);

		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.activity_login_header);
		chitiettintuc_headerview.setTextHeader(R.string.moithanhvien);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.btn_back);

		dichvu_list = (ListView) view.findViewById(R.id.dichvu_list);
		dichvu_list.setOnItemClickListener(this);

		callSHowData();
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

		Cursor cursor = Recomment.getCursorFromUser(getActivity(), -1);

		if (cursor != null) {
			dichvu_list.setAdapter(adapter = new MenuRightAdaper(getActivity(), cursor) {
				@Override
				public void openMoi(ContentValues contentValues) {

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
		Cursor cursor = (Cursor)parent.getItemAtPosition(position);
		(((RootMenuActivity) getActivity())).moiContactUserFragment(Conts.getStringCursor(cursor, User._ID));
	}
}