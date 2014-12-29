package com.aretha.slidemenudemo.fragment;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.adapter.MoiNhieuDichVuAdapter;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.MenuRightItemView;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MoiNhieuDichVuFragment extends Fragment implements android.view.View.OnClickListener {
	private LinearLayout moinhieudichvu_dialog_list_hor;
	private MoiNhieuDichVuAdapter adapter;
	private EditText moinhieudichvu_dialog_search;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moinhieudichvu_dialog, null);
		
		
		moinhieudichvu_dialog_search = (EditText) view.findViewById(R.id.moinhieudichvu_dialog_search);
		moinhieudichvu_dialog_list_hor = (LinearLayout) view.findViewById(R.id.moinhieudichvu_dialog_list_hor);
		HeaderView maumoi_headerview = (HeaderView) view.findViewById(R.id.moinhieudichvu_dialog_headerview);
		maumoi_headerview.setTextHeader(R.string.moi);
		maumoi_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		maumoi_headerview.setButtonRightImage(false, R.drawable.btn_back);
		maumoi_headerview.setButtonMoi(true);

		maumoi_headerview.findViewById(R.id.header_btn_right_moi).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});

		maumoi_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		String where = String.format("%s = '%s'", User._ID, getArguments().getString(User._ID));
		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, where, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			MenuRightItemView moinhieudichvu_dialog_menurightitem = (MenuRightItemView) view.findViewById(R.id.moinhieudichvu_dialog_menurightitem);
			moinhieudichvu_dialog_menurightitem.initData(cursor, "");
		}

		if (cursor != null) {
			cursor.close();
		}

		ListView maumoi_list = (ListView) view.findViewById(R.id.moinhieudichvu_dialog_list);
		Cursor cursorDV = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, null, null, null);
		moinhieudichvu_dialog_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				adapter.setTextSearch(moinhieudichvu_dialog_search.getText().toString().trim());
				adapter.notifyDataSetChanged();
			}
		});
		maumoi_list.setAdapter(adapter = new MoiNhieuDichVuAdapter(getActivity(), cursorDV) {

			@Override
			public void addOrRemove(final String _id, boolean isAdd) {
				if (isAdd) {
					final MoiNhieuDichVuAddItemView addItemView = new MoiNhieuDichVuAddItemView(getActivity());
					addItemView.setMId(_id);

					moinhieudichvu_dialog_list_hor.addView(addItemView);
					addItemView.findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							moinhieudichvu_dialog_list_hor.removeView(addItemView);
							adapter.remove(_id);
							adapter.notifyDataSetChanged();
						}
					});
				} else {
					for (int i = 0; i < moinhieudichvu_dialog_list_hor.getChildCount(); i++) {
						MoiNhieuDichVuAddItemView child = ((MoiNhieuDichVuAddItemView) moinhieudichvu_dialog_list_hor.getChildAt(i));
						if (child.getmId().equals(_id)) {
							moinhieudichvu_dialog_list_hor.removeView(child);
							break;
						}
					}
				}
			}

		});

		return view;
	}

	@Override
	public void onClick(View v) {
	}

	private class MoiNhieuDichVuAddItemView extends LinearLayout {
		public MoiNhieuDichVuAddItemView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinhieudichvu_add_item, this);
			// findViewById(R.id.x).setOnClickListener(new
			// View.OnClickListener() {
			// @Override
			// public void onClick(View v) {
			// moinhieudichvu_dialog_list_hor.removeView(MoiNhieuDichVuAddItemView.this);
			// }
			// });
		}

		private String mId = "";

		public String getmId() {
			return mId;
		}

		public void setMId(String _id) {
			mId = _id;
		}

	}
}