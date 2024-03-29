package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.VasContact;
import vnp.com.db.VasContactUseService;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiDvChoNhieuNguoiAdaper;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.base.diablog.VasProgessDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.viettel.vtt.vdealer.R;

public class MoiDvChoNhieuNguoiFragment extends BaseFragment implements
		OnItemClickListener, View.OnClickListener, OnTouchListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private ListView moi_list;
	private LinearLayout moinhieudichvu_dialog_list_hor;
	private EditText moidichvuchonhieunguoi_number;
	private MoiDvChoNhieuNguoiAdaper adaper;
	private String service_code = "";
	private View moidichvuchonhieunguoi_contact;
	private vnp.com.mimusic.view.KeyBoardView boardView;
	private View mkeyboard;
	private OnClickListener moidichvuchonhieunguoi_add_plusOnCLick = new OnClickListener() {
		@Override
		public void onClick(View v) {

			Conts.hiddenKeyBoard(getActivity());
			String moidichvuchonhieunguoi_numberText = boardView.getString();
			if (Conts.isVietTelNUmber(moidichvuchonhieunguoi_numberText,
					getActivity())) {
				addSodt(moidichvuchonhieunguoi_numberText);
				boardView.clear();
				boardView.setVisibility(View.GONE);
				mkeyboard(false);
			} else {
				Conts.showDialogDongYCallBack(getActivity(), String.format(
						getString(R.string.format_check_sdt),
						moidichvuchonhieunguoi_numberText));
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moidichvuchonhieunguoi, null);
		nhieuNgoiHeaderView = new MoiNhieuNgoiHeaderView(getActivity());
		view.findViewById(R.id.LinearLayout01).setOnClickListener(null);
		mkeyboard = view.findViewById(R.id.mkeyboard);
		mkeyboard.setOnClickListener(this);
		boardView = Conts.getView(view, R.id.keyBoardView1);
		boardView.getKeEditText().setOnEditorActionListener(
				new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == KeyEvent.KEYCODE_ENDCALL
								|| event == null
								|| KeyEvent.KEYCODE_CALL == actionId) {
							Conts.hiddenKeyBoard(getActivity());
							moidichvuchonhieunguoi_add_plusOnCLick
									.onClick(null);
						}
						return false;
					}
				});
		boardView.addActionOnClickAdd(moidichvuchonhieunguoi_add_plusOnCLick);
		moidichvuchonhieunguoi_contact = view
				.findViewById(R.id.moidichvuchonhieunguoi_contact);
		moidichvuchonhieunguoi_contact.setOnClickListener(this);

		moinhieudichvu_dialog_list_hor = (LinearLayout) view
				.findViewById(R.id.moinhieudichvu_dialog_list_hor);
		moidichvuchonhieunguoi_number = (EditText) view
				.findViewById(R.id.search);

		// moidichvuchonhieunguoi_number ime
		moidichvuchonhieunguoi_number
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == KeyEvent.KEYCODE_ENDCALL
								|| event == null
								|| KeyEvent.KEYCODE_CALL == actionId) {
							Conts.hiddenKeyBoard(getActivity());
						}
						return false;
					}
				});
		moidichvuchonhieunguoi_number.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				adaper.setTextSearch(moidichvuchonhieunguoi_number.getText()
						.toString().trim());
				adaper.notifyDataSetChanged();
			}
		});

		view.findViewById(R.id.moi).setOnClickListener(this);
		view.findViewById(R.id.back).setOnClickListener(this);

		moi_list = (ListView) view.findViewById(R.id.list);
		moi_list.setOnTouchListener(this);
		moi_list.addHeaderView(nhieuNgoiHeaderView);
		/**
		 * show data
		 */

		String mService_code = getArguments().getString(
				DichVuStore.service_code);

		final JSONObject mcursor = dichVuStore
				.getDvByServiceCode(mService_code);
		service_code = Conts.getString(mcursor, DichVuStore.service_code);
		Conts.setTextView(view.findViewById(R.id.name), mcursor,
				DichVuStore.service_name);

		service_name = Conts.getString(mcursor, DichVuStore.service_name);
		Conts.setTextView(view.findViewById(R.id.gia), mcursor,
				DichVuStore.service_price);
		ImageView home_item_img_icon = (ImageView) view.findViewById(R.id.icon);

		String service_icon = Conts
				.getString(mcursor, DichVuStore.service_icon);
		// ImageLoaderUtils.getInstance(getActivity()).displayImage(service_icon,
		// home_item_img_icon, R.drawable.no_image);

		Conts.showLogoDichvu(home_item_img_icon, service_icon);
		nhieuNgoiHeaderView.setData(mcursor);

		moi_list.setOnItemClickListener(this);
		String phones = VasContactUseService.queryListPhoneCanUse(
				getActivity(), service_code);
		String where = String.format("%s  in %s", VasContact.PHONE, phones);
		Cursor cursor = getActivity().getContentResolver().query(
				VasContact.CONTENT_URI, null, where, null,
				VasContact.NAME_CONTACT_ENG);

		adaper = new MoiDvChoNhieuNguoiAdaper(getActivity(), cursor,
				service_code) {

			@Override
			public void addOrRemove(final String _id, boolean isAdd,
					int postition) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(
							getActivity());
					addItemView.setMId(_id, postition);

					moinhieudichvu_dialog_list_hor.addView(addItemView);
					Conts.showAlpha(
							MoiDvChoNhieuNguoiFragment.this.getView()
									.findViewById(R.id.moi),
							(moinhieudichvu_dialog_list_hor.getChildCount() == 0));
					Conts.addViewScale(addItemView);
					addItemView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							moinhieudichvu_dialog_list_hor
									.removeView(addItemView);
							Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this
									.getView().findViewById(R.id.moi),
									(moinhieudichvu_dialog_list_hor
											.getChildCount() == 0));

							adaper.remove(_id);
							adaper.notifyDataSetChanged();
						}
					});
				} else {
					for (int i = 0; i < moinhieudichvu_dialog_list_hor
							.getChildCount(); i++) {
						final MoiNhieuSDTAddItemView child = ((MoiNhieuSDTAddItemView) moinhieudichvu_dialog_list_hor
								.getChildAt(i));
						if (child.getmId().equals(_id)) {
							moinhieudichvu_dialog_list_hor.removeView(child);
							Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this
									.getView().findViewById(R.id.moi),
									(moinhieudichvu_dialog_list_hor
											.getChildCount() == 0));

							break;
						}
					}
				}
			}

			@Override
			public void addOrRemoveSdt(boolean isAdd, final String sdt,
					int position) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(
							getActivity());
					addItemView.setMId(sdt, position);

					moinhieudichvu_dialog_list_hor.addView(addItemView);
					Conts.showAlpha(
							MoiDvChoNhieuNguoiFragment.this.getView()
									.findViewById(R.id.moi),
							(moinhieudichvu_dialog_list_hor.getChildCount() == 0));

					addItemView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							moinhieudichvu_dialog_list_hor
									.removeView(addItemView);
							Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this
									.getView().findViewById(R.id.moi),
									(moinhieudichvu_dialog_list_hor
											.getChildCount() == 0));

							adaper.remove(sdt);
							adaper.notifyDataSetChanged();
						}
					});
				}
			}
		};

		moi_list.setAdapter(adaper);

		Conts.showAlpha(view.findViewById(R.id.moi),
				(moinhieudichvu_dialog_list_hor.getChildCount() == 0));
		return view;
	}

	protected void addSodt(String moidichvuchonhieunguoi_numberText) {

		// add
		while (moidichvuchonhieunguoi_numberText.startsWith("0")) {
			moidichvuchonhieunguoi_numberText = moidichvuchonhieunguoi_numberText
					.substring(1, moidichvuchonhieunguoi_numberText.length());
		}

		Bundle bundle = new Bundle();
		bundle.putString("msisdn", moidichvuchonhieunguoi_numberText);
		bundle.putString("service_code", service_code);

		final String sdt = moidichvuchonhieunguoi_numberText;
		execute(RequestMethod.POST, API.API_R019, bundle, new IContsCallBack() {
			private ProgressDialog progressDialog;

			@Override
			public void onSuscess(JSONObject response) {
				progressDialog.dismiss();
				adaper.addSdt((sdt.startsWith("84") ? "" : "84") + sdt,
						getActivity());
			}

			@Override
			public void onStart() {
				if (progressDialog == null) {
					progressDialog = new VasProgessDialog(getActivity());
					progressDialog.show();
				}
			}

			@Override
			public void onError(String message) {

				if (!Conts.isBlank(message)) {
					if (message.contains("Phone")) {
						message = getActivity().getString(
								R.string.sdtkhongphaicuabiettel);
					}
					message = getActivity().getString(
							R.string.error_sdt_viettel);
					Conts.showDialogThongbao(getActivity(), message);
				}

				progressDialog.dismiss();
			}

		});

	}

	private String service_name;

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			getActivity().onBackPressed();
		} else if (v.getId() == R.id.moi) {

			Conts.hiddenKeyBoard(getActivity());
			boardView.setVisibility(View.GONE);
			mkeyboard(false);

			gotoLoiMoi(getArguments().getString(DichVuStore.service_code));
		} else if (v.getId() == R.id.moidichvuchonhieunguoi_contact) {

			if (boardView.getVisibility() == View.VISIBLE) {
				Conts.hiddenKeyBoard(getActivity());
				boardView.setVisibility(View.GONE);

				mkeyboard(false);
			} else {
				Conts.showKeyBoard(boardView.getKeEditText());
				boardView.setVisibility(View.VISIBLE);
				mkeyboard(true);
			}
		} else if (v.getId() == R.id.mkeyboard) {
			Conts.hiddenKeyBoard(getActivity());
			boardView.setVisibility(View.GONE);
			mkeyboard(false);
		}
	}

	public void mkeyboard(boolean show) {
		if (show) {
			mkeyboard.setVisibility(View.VISIBLE);
			mkeyboard.startAnimation(AnimationUtils.loadAnimation(
					getActivity(), R.anim.abc_alpha_in));
		} else {
			mkeyboard.setVisibility(View.GONE);
		}
	}

	private void gotoLoiMoi(final String serviceCode) {

		int count = adaper.getListAdd().size() + adaper.getListSeList().size();
		if (count == 0) {
			Conts.showDialogDongYCallBack(getActivity(),
					getString(R.string.validateaddnguoi));
			return;
		}

		ContentValues values = new ContentValues();

		String content = getString(R.string.ban_co_muon_moi_dv_nays);
		values.put("name", getString(R.string.app_name));
		values.put("content", content);
		values.put("btn_right", getString(R.string.dongy));

		new DangKyDialog(getActivity(), values) {
			public void mOpen() {
				super.mOpen();
				String customers = "";
				for (String _id : adaper.getListSeList()) {
					String user = adaper.getUserFrom_ID(_id);
					user = Conts.getSDT(user);

					if (!Conts.isBlank(user)) {
						if (customers.endsWith("\"")) {
							customers = String.format("%s,\"%s\"", customers,
									user);
						} else {
							customers = String.format("\"%s\"", user);
						}
					}
				}

				for (String user : adaper.getListAdd()) {
					if (!Conts.isBlank(user)) {
						if (customers.endsWith("\"")) {
							customers = String.format("%s,\"%s\"", customers,
									user);
						} else {
							customers = String.format("\"%s\"", user);
						}
					}
				}
				customers = String.format("{%s}", customers);
				(((RootMenuActivity) getActivity())).gotoLoiMoi(serviceCode,
						customers);
			};
		}.show();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}

	private class MoiNhieuSDTAddItemView extends LinearLayout {
		public MoiNhieuSDTAddItemView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.moinhieusdt_add_item, this);
		}

		private String mId = "";

		public String getmId() {
			return mId;
		}

		public void setMId(String _id, int position) {
			mId = _id;

			Cursor cursor = getActivity().getContentResolver()
					.query(VasContact.CONTENT_URI, null,
							String.format("%s =='%s'", VasContact._ID, mId),
							null, null);

			if (cursor != null && cursor.moveToNext()) {
				((TextView) findViewById(R.id.moinhieudichvu_item_tv_name))
						.setText(VasContact.getName(cursor));
				String avatar = cursor.getString(cursor
						.getColumnIndex(VasContact.AVATAR));
				String contact_id = Conts.getStringCursor(cursor,
						VasContact.contact_id);
				Conts.showAvatarContact(
						((ImageView) findViewById(R.id.imageView1)), avatar,
						contact_id,
						Conts.resavatar()[position % Conts.resavatar().length]);
			} else {
				((TextView) findViewById(R.id.moinhieudichvu_item_tv_name))
						.setText(_id);
			}

			Conts.getSDT(findViewById(R.id.moinhieudichvu_item_tv_name));
		}
	}

	private MoiNhieuNgoiHeaderView nhieuNgoiHeaderView;

	private class MoiNhieuNgoiHeaderView extends LinearLayout {
		public MoiNhieuNgoiHeaderView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(
					Context.LAYOUT_INFLATER_SERVICE)).inflate(
					R.layout.moinghieunguoi_header, this);
		}

		public void setData(JSONObject mcursor) {
			Conts.setTextView(findViewById(R.id.name), mcursor,
					DichVuStore.service_name);
			Conts.setTextView(findViewById(R.id.gia), mcursor,
					DichVuStore.service_price);
			ImageView home_item_img_icon = (ImageView) findViewById(R.id.icon);
			String service_icon = Conts.getString(mcursor,
					DichVuStore.service_icon);
			Conts.showLogoDichvu(home_item_img_icon, service_icon);
		}

		public void setData(Cursor mcursor) {
			Conts.setTextViewCursor(findViewById(R.id.name), mcursor,
					DichVuStore.service_name);
			Conts.setTextViewCursor(findViewById(R.id.gia), mcursor,
					DichVuStore.service_price);
			ImageView home_item_img_icon = (ImageView) findViewById(R.id.icon);
			String service_icon = Conts.getStringCursor(mcursor,
					DichVuStore.service_icon);
			// ImageLoaderUtils.getInstance(getActivity()).displayImage(service_icon,
			// home_item_img_icon, R.drawable.no_image);
			Conts.showLogoDichvu(home_item_img_icon, service_icon);
		}

	}

	public boolean onBackPressed() {
		if (boardView.getVisibility() == View.VISIBLE) {
			Conts.showView(boardView, false);
			Conts.showView(mkeyboard, false);
			return true;
		}
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Conts.hiddenKeyBoard(getActivity());
		return false;
	}
}