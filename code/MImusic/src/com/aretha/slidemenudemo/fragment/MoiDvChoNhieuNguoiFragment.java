package com.aretha.slidemenudemo.fragment;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiDvChoNhieuNguoiAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import vnp.com.mimusic.view.HeaderView;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MoiDvChoNhieuNguoiFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LinearLayout moinhieudichvu_dialog_list_hor;
	private EditText moidichvuchonhieunguoi_number;
	MoiDvChoNhieuNguoiAdaper adaper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moidichvuchonhieunguoi, null);
		moinhieudichvu_dialog_list_hor = (LinearLayout) view.findViewById(R.id.moinhieudichvu_dialog_list_hor);
		moidichvuchonhieunguoi_number = (EditText) view.findViewById(R.id.moidichvuchonhieunguoi_number);
		moidichvuchonhieunguoi_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				adaper.setTextSearch(moidichvuchonhieunguoi_number.getText().toString().trim());
				adaper.notifyDataSetChanged();
			}
		});

		HeaderView chitiettintuc_headerview = (HeaderView) view.findViewById(R.id.moidichvuchonhieunguoi_headerview);
		chitiettintuc_headerview.setTextHeader(R.string.moidichvuchonhieunguoi);
		chitiettintuc_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonRightImage(false, R.drawable.btn_back);
		chitiettintuc_headerview.setButtonMoi(true);
		chitiettintuc_headerview.findViewById(R.id.header_btn_right_moi).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gotoLoiMoi(getArguments().getString(DichVu.ID));
			}
		});
		chitiettintuc_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		ListView moi_list = (ListView) view.findViewById(R.id.moidichvuchonhieunguoi_list);

		/**
		 * show data
		 */
		ChiTietDichVuNoFeatureView header = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.moidichvuchonhieunguoi_chiteitdichvunofeatureview);

		final String id = getArguments().getString(DichVu.ID);
		String service_code = "";
		String selection = DichVu.ID + "='" + id + "'";
		final Cursor mcursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

		if (mcursor != null && mcursor.getCount() >= 1) {
			mcursor.moveToNext();
			service_code = mcursor.getString(mcursor.getColumnIndex(DichVu.service_code));
			header.setData(mcursor);

			mcursor.close();
		}

		header.setBackground(android.R.color.white);
		header.useValue2(true);
		header.setOnClickListener(null);
		header.findViewById(R.id.chitietdichvu_no_feature_dangky).setVisibility(View.INVISIBLE);
		header.findViewById(R.id.chitietdichvu_no_feature_moi).setVisibility(View.INVISIBLE);

		moi_list.setOnItemClickListener(this);

		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s ='0'", User.STATUS), null, null);
		adaper = new MoiDvChoNhieuNguoiAdaper(getActivity(), cursor) {

			@Override
			public void addOrRemove(final String _id, boolean isAdd) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(getActivity());
					addItemView.setMId(_id);

					moinhieudichvu_dialog_list_hor.addView(addItemView);

					addItemView.findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							moinhieudichvu_dialog_list_hor.removeView(addItemView);
							adaper.remove(_id);
							adaper.notifyDataSetChanged();
						}
					});
				} else {
					for (int i = 0; i < moinhieudichvu_dialog_list_hor.getChildCount(); i++) {
						MoiNhieuSDTAddItemView child = ((MoiNhieuSDTAddItemView) moinhieudichvu_dialog_list_hor.getChildAt(i));
						if (child.getmId().equals(_id)) {
							moinhieudichvu_dialog_list_hor.removeView(child);
							break;
						}
					}
				}
			}
		};

		moi_list.setAdapter(adaper);

		return view;
	}

	@Override
	public void onClick(View v) {
	}

	private void gotoLoiMoi(String id) {
		(((RootMenuActivity) getActivity())).gotoLoiMoi(id);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}

	private class MoiNhieuSDTAddItemView extends LinearLayout {
		public MoiNhieuSDTAddItemView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinhieusdt_add_item, this);
		}

		private String mId = "";

		public String getmId() {
			return mId;
		}

		public void setMId(String _id) {
			mId = _id;

			Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s =='%s'", User._ID, mId), null, null);

			if (cursor != null && cursor.moveToNext()) {
				((TextView) findViewById(R.id.moinhieudichvu_item_tv_name)).setText(Conts.getName(cursor));
			}
		}
	}
}