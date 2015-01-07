package com.aretha.slidemenudemo.fragment;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiNhieuDichVuAdapter;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.ImageLoaderUtils;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class MoiNhieuDichVuFragment extends Fragment implements android.view.View.OnClickListener {
	private LinearLayout moinhieudichvu_dialog_list_hor;
	private MoiNhieuDichVuAdapter adapter;
	private EditText moinhieudichvu_dialog_search;
	private String sdt = "";

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

				int size = adapter.getListSelect().size();

				if (size == 0 || size > 5) {
					Conts.toast(getActivity(), getString(R.string.validatesodichvu));
					return;
				}
				// TODO
				String service_codes = "";
				for (String _id : adapter.getListSelect()) {
					String service_code = adapter.getService_code(_id);
					if (!Conts.isBlank(service_code)) {
						if (Conts.isBlank(service_codes)) {
							service_codes = service_code;
						} else {
							service_codes = String.format("%s,%s", service_codes, service_code);
						}
					}
				}

				(((RootMenuActivity) getActivity())).gotoLoiMoi(sdt,adapter.getService_code(adapter.getListSelect().get(0)), service_codes);
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

			sdt = cursor.getString(cursor.getColumnIndex(User.USER));

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
			public void addOrRemove(final String _id, boolean isAdd, String icon) {
				if (isAdd) {
					final MoiNhieuDichVuAddItemView addItemView = new MoiNhieuDichVuAddItemView(getActivity());
					moinhieudichvu_dialog_list_hor.addView(addItemView);

					addItemView.setMId(_id);
					addItemView.setIcon(icon);

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
		}

		public void setIcon(String icon) {
			ImageView moinhieudichvu_img_icon = (ImageView) findViewById(R.id.imageView1);
			moinhieudichvu_img_icon.setImageResource(R.drawable.no_avatar);
			ImageLoaderUtils.getInstance(getActivity()).DisplayImage(icon, moinhieudichvu_img_icon);

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