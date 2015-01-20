package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiDvChoNhieuNguoiAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.ChiTietDichVuNoFeatureView;
import vnp.com.mimusic.view.HeaderView;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MoiDvChoNhieuNguoiFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private ListView moi_list;
	private View main_mm;
	private LinearLayout moinhieudichvu_dialog_list_hor;
	private EditText moidichvuchonhieunguoi_number;
	private MoiDvChoNhieuNguoiAdaper adaper;
	private String service_code = "";
	private View moidichvuchonhieunguoi_add_plus;
	private CheckBox moidichvuchonhieunguoi_contact;

	private OnClickListener moidichvuchonhieunguoi_add_plusOnCLick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Conts.hiddenKeyBoard(getActivity());
			String moidichvuchonhieunguoi_numberText = moidichvuchonhieunguoi_number.getText().toString();
			if (Conts.isVietTelNUmber(moidichvuchonhieunguoi_numberText, getActivity())) {
				addSodt(moidichvuchonhieunguoi_numberText);
			} else {
				Conts.toast(getActivity(), String.format(getString(R.string.format_check_sdt), moidichvuchonhieunguoi_numberText));
			}
		}
	};
	private ChiTietDichVuNoFeatureView headerOfList;
	private ChiTietDichVuNoFeatureView header;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moidichvuchonhieunguoi, null);
		main_mm = view.findViewById(R.id.main_mm);
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
		chitiettintuc_headerview.setTextHeader(R.string.moi);
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

		moi_list = (ListView) view.findViewById(R.id.moidichvuchonhieunguoi_list);

		/**
		 * show data
		 */
		header = (ChiTietDichVuNoFeatureView) view.findViewById(R.id.moidichvuchonhieunguoi_chiteitdichvunofeatureview);
		headerOfList = new ChiTietDichVuNoFeatureView(getActivity());
		headerOfList.showFooter();
		moi_list.addHeaderView(headerOfList);

		final String id = getArguments().getString(DichVu.ID);
		String selection = DichVu.ID + "='" + id + "'";

		if (Conts.isBlank(id)) {
			String mService_code = getArguments().getString(DichVu.service_code);
			selection = DichVu.service_code + "='" + mService_code + "'";
		}
		final Cursor mcursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

		if (mcursor != null && mcursor.getCount() >= 1) {
			mcursor.moveToNext();
			service_code = mcursor.getString(mcursor.getColumnIndex(DichVu.service_code));

			header.setData(mcursor);
			headerOfList.setData(mcursor);
			mcursor.close();
		}
		headerOfList.setBackground(android.R.color.white);
		headerOfList.useValue2(true);
		headerOfList.setOnClickListener(null);
		headerOfList.findViewById(R.id.chitietdichvu_no_feature_dangky).setVisibility(View.INVISIBLE);
		headerOfList.findViewById(R.id.chitietdichvu_no_feature_moi).setVisibility(View.INVISIBLE);

		header.setBackground(android.R.color.white);
		header.useValue2(true);
		header.setOnClickListener(null);
		header.findViewById(R.id.chitietdichvu_no_feature_dangky).setVisibility(View.INVISIBLE);
		header.findViewById(R.id.chitietdichvu_no_feature_moi).setVisibility(View.INVISIBLE);

		moi_list.setOnItemClickListener(this);

		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s ='0'", User.STATUS), null, null);
		adaper = new MoiDvChoNhieuNguoiAdaper(getActivity(), cursor, service_code) {

			@Override
			public void addOrRemove(final String _id, boolean isAdd) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(getActivity());
					addItemView.setMId(_id);

					moinhieudichvu_dialog_list_hor.addView(addItemView);
					Conts.addViewScale(addItemView);
					addItemView.findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {

							Conts.removeViewScale(addItemView, new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {

								}

								@Override
								public void onAnimationRepeat(Animation animation) {

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									moinhieudichvu_dialog_list_hor.removeView(addItemView);
								}
							});
							adaper.remove(_id);
							adaper.notifyDataSetChanged();
						}
					});
				} else {
					for (int i = 0; i < moinhieudichvu_dialog_list_hor.getChildCount(); i++) {
						final MoiNhieuSDTAddItemView child = ((MoiNhieuSDTAddItemView) moinhieudichvu_dialog_list_hor.getChildAt(i));
						if (child.getmId().equals(_id)) {

							Conts.removeViewScale(child, new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {

								}

								@Override
								public void onAnimationRepeat(Animation animation) {

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									moinhieudichvu_dialog_list_hor.removeView(child);
								}
							});
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

							Conts.removeViewScale(addItemView, new AnimationListener() {

								@Override
								public void onAnimationStart(Animation animation) {

								}

								@Override
								public void onAnimationRepeat(Animation animation) {

								}

								@Override
								public void onAnimationEnd(Animation animation) {
									moinhieudichvu_dialog_list_hor.removeView(addItemView);
								}
							});
							adaper.remove(sdt);
							adaper.notifyDataSetChanged();
						}
					});
				}
			}
		};

		moi_list.setAdapter(adaper);
		moi_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				monScroll();
			}

		});
		return view;
	}

	protected void addSodt(String moidichvuchonhieunguoi_numberText) {

		// add
		while (moidichvuchonhieunguoi_numberText.startsWith("0")) {
			moidichvuchonhieunguoi_numberText = moidichvuchonhieunguoi_numberText.substring(1, moidichvuchonhieunguoi_numberText.length());
		}

		// TODO
		Bundle bundle = new Bundle();
		bundle.putString("msisdn", moidichvuchonhieunguoi_numberText);
		bundle.putString("service_code", service_code);

		final String sdt = moidichvuchonhieunguoi_numberText;
		execute(RequestMethod.POST, API.API_R019, bundle, new IContsCallBack() {
			private ProgressDialog progressDialog;

			@Override
			public void onSuscess(JSONObject response) {
				progressDialog.dismiss();
				// Conts.showDialogThongbao(getActivity(), response.toString());
				adaper.addSdt((sdt.startsWith("84") ? "" : "84") + sdt, getActivity());
			}

			@Override
			public void onStart() {
				progressDialog = ProgressDialog.show(getActivity(), "", getString(R.string.loading));
			}

			@Override
			public void onError(String message) {
				Conts.showDialogThongbao(getActivity(), message);
				progressDialog.dismiss();
			}

			@Override
			public void onError() {
				onError("");
			}
		});
		// adaper.addSdt(moidichvuchonhieunguoi_numberText, getActivity());

	}

	int currentTop = 0;

	private void monScroll() {
		int top = headerOfList.getTop();
		int heightt = header.getHeight();
		int next = 0;
		if (top < 0) {
			if (top + heightt <= 0) {
				next = -heightt;
			} else {
				next = top;
			}
		} else if (top > 0) {
			next = top;
		}
		currentTop = next;
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) main_mm.getLayoutParams();
		params.setMargins(0, next, 0, 0);
		main_mm.setLayoutParams(params);
	}

	@Override
	public void onClick(View v) {
	}

	private void gotoLoiMoi(String id) {

		int count = adaper.getListAdd().size() + adaper.getListSeList().size();
		if (count == 0) {
			Conts.toast(getActivity(), getString(R.string.validateaddnguoi));
			return;
		}

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

			Conts.getSDT(findViewById(R.id.moinhieudichvu_item_tv_name));
		}
	}

}