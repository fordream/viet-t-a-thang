package com.aretha.slidemenudemo.fragment;

import java.util.List;

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
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MoiDvChoNhieuNguoiFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private LinearLayout moinhieudichvu_dialog_list_hor;
	private EditText moidichvuchonhieunguoi_number;
	private MoiDvChoNhieuNguoiAdaper adaper;

	private View moidichvuchonhieunguoi_add_plus;
	private CheckBox moidichvuchonhieunguoi_contact;

	private OnClickListener moidichvuchonhieunguoi_add_plusOnCLick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String moidichvuchonhieunguoi_numberText = moidichvuchonhieunguoi_number.getText().toString();
			if (Conts.isVietTelNUmber(moidichvuchonhieunguoi_numberText, getActivity())) {
				adaper.addSdt(moidichvuchonhieunguoi_numberText, getActivity());
			} else {
				Conts.toast(getActivity(), String.format(getString(R.string.format_check_sdt), moidichvuchonhieunguoi_numberText));
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moidichvuchonhieunguoi, null);
		moidichvuchonhieunguoi_add_plus = view.findViewById(R.id.moidichvuchonhieunguoi_add_plus);

		moidichvuchonhieunguoi_add_plus.setOnClickListener(moidichvuchonhieunguoi_add_plusOnCLick);
		moidichvuchonhieunguoi_contact = (CheckBox) view.findViewById(R.id.moidichvuchonhieunguoi_contact);
		moidichvuchonhieunguoi_contact.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				moidichvuchonhieunguoi_add_plus.setVisibility(moidichvuchonhieunguoi_contact.isChecked() ? View.VISIBLE : View.GONE);
				moidichvuchonhieunguoi_number.setHint(moidichvuchonhieunguoi_contact.isChecked() ? R.string.nhapsodienthoai : R.string.timkiemdanhba);
				moidichvuchonhieunguoi_number.setText("");
				adaper.setTextSearch("");
				adaper.notifyDataSetChanged();

				moidichvuchonhieunguoi_number.setInputType(moidichvuchonhieunguoi_contact.isChecked() ? InputType.TYPE_CLASS_PHONE : InputType.TYPE_CLASS_TEXT);
			}
		});

		moinhieudichvu_dialog_list_hor = (LinearLayout) view.findViewById(R.id.moinhieudichvu_dialog_list_hor);
		moidichvuchonhieunguoi_number = (EditText) view.findViewById(R.id.moidichvuchonhieunguoi_number);

		// moidichvuchonhieunguoi_number ime
		moidichvuchonhieunguoi_number.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == KeyEvent.KEYCODE_ENDCALL || event == null || KeyEvent.KEYCODE_CALL == actionId) {
					Conts.hiddenKeyBoard(getActivity());
					moidichvuchonhieunguoi_add_plusOnCLick.onClick(null);
				}
				return false;
			}
		});
		moidichvuchonhieunguoi_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (!moidichvuchonhieunguoi_contact.isChecked()) {
					adaper.setTextSearch(moidichvuchonhieunguoi_number.getText().toString().trim());
					adaper.notifyDataSetChanged();
				}
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

			@Override
			public void addOrRemoveSdt(boolean isAdd, final String sdt) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(getActivity());
					addItemView.setMId(sdt);

					moinhieudichvu_dialog_list_hor.addView(addItemView);

					addItemView.findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							moinhieudichvu_dialog_list_hor.removeView(addItemView);
							adaper.remove(sdt);
							adaper.notifyDataSetChanged();
						}
					});
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

		int count = adaper.getListAdd().size() + adaper.getListSeList().size();
		if (count == 0 || count > 5) {
			Conts.toast(getActivity(), getString(R.string.validateaddnguoi));
			return;
		}

		// TODO
		String customers = "";
		for (String _id : adaper.getListSeList()) {
			String user = adaper.getUserFrom_ID(_id);
			if (!Conts.isBlank(user)) {
				if (customers.endsWith("\"")) {
					customers = String.format("%s,\"%s\"", customers, user);
				} else {
					customers = String.format("\"%s\"", user);
				}
			}
		}

		for (String user : adaper.getListAdd()) {
			if (!Conts.isBlank(user)) {
				if (customers.endsWith("\"")) {
					customers = String.format("%s,\"%s\"", customers, user);
				} else {
					customers = String.format("\"%s\"", user);
				}
			}
		}
		customers = String.format("{%s}", customers);
		(((RootMenuActivity) getActivity())).gotoLoiMoi(id, customers);
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
			} else {
				((TextView) findViewById(R.id.moinhieudichvu_item_tv_name)).setText(_id);
			}
		}
	}
}