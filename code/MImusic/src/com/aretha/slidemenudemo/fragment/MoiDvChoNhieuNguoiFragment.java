package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiDvChoNhieuNguoiAdaper;
import vnp.com.mimusic.base.diablog.VasProgessDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.ProgressDialog;
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
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MoiDvChoNhieuNguoiFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener, OnTouchListener {
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
			if (Conts.isVietTelNUmber(moidichvuchonhieunguoi_numberText, getActivity())) {
				addSodt(moidichvuchonhieunguoi_numberText);
				boardView.clear();
				boardView.setVisibility(View.GONE);
				mkeyboard(false);
				// moidichvuchonhieunguoi_number.setEnabled(true);
			} else {
				Conts.toast(getActivity(), String.format(getString(R.string.format_check_sdt), moidichvuchonhieunguoi_numberText));
			}
		}
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moidichvuchonhieunguoi, null);
		nhieuNgoiHeaderView = new MoiNhieuNgoiHeaderView(getActivity());
		view.findViewById(R.id.LinearLayout01).setOnClickListener(null);
		mkeyboard = view.findViewById(R.id.mkeyboard);
		mkeyboard.setOnClickListener(this);
		boardView = Conts.getView(view, R.id.keyBoardView1);
		boardView.getKeEditText().setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == KeyEvent.KEYCODE_ENDCALL || event == null || KeyEvent.KEYCODE_CALL == actionId) {
					Conts.hiddenKeyBoard(getActivity());
					moidichvuchonhieunguoi_add_plusOnCLick.onClick(null);
				}
				return false;
			}
		});
		boardView.addActionOnClickAdd(moidichvuchonhieunguoi_add_plusOnCLick);
		moidichvuchonhieunguoi_contact = view.findViewById(R.id.moidichvuchonhieunguoi_contact);
		moidichvuchonhieunguoi_contact.setOnClickListener(this);

		moinhieudichvu_dialog_list_hor = (LinearLayout) view.findViewById(R.id.moinhieudichvu_dialog_list_hor);
		moidichvuchonhieunguoi_number = (EditText) view.findViewById(R.id.search);

		// moidichvuchonhieunguoi_number ime
		moidichvuchonhieunguoi_number.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == KeyEvent.KEYCODE_ENDCALL || event == null || KeyEvent.KEYCODE_CALL == actionId) {
					Conts.hiddenKeyBoard(getActivity());
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
				// if (!moidichvuchonhieunguoi_contact.isChecked()) {
				adaper.setTextSearch(moidichvuchonhieunguoi_number.getText().toString().trim());
				adaper.notifyDataSetChanged();
				// }
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

		final String id = getArguments().getString(DichVuStore.ID);
		String selection = DichVuStore.ID + "='" + id + "'";

		if (Conts.isBlank(id)) {
			String mService_code = getArguments().getString(DichVuStore.service_code);
			selection = DichVuStore.service_code + "='" + mService_code + "'";
		}

		// final Cursor mcursor =
		// getActivity().getContentResolver().query(DichVuStore.CONTENT_URI,
		// null, selection, null, null);
		final Cursor mcursor = null;
		if (mcursor != null && mcursor.getCount() >= 1) {
			mcursor.moveToNext();
			service_code = mcursor.getString(mcursor.getColumnIndex(DichVuStore.service_code));
			Conts.setTextViewCursor(view.findViewById(R.id.name), mcursor, DichVuStore.service_name);
			Conts.setTextViewCursor(view.findViewById(R.id.gia), mcursor, DichVuStore.service_price);
			ImageView home_item_img_icon = (ImageView) view.findViewById(R.id.icon);

			String service_icon = Conts.getStringCursor(mcursor, DichVuStore.service_icon);
			// ImageLoaderUtils.getInstance(getActivity()).displayImage(service_icon,
			// home_item_img_icon, R.drawable.no_image);

			Conts.showLogoDichvu(home_item_img_icon, service_icon);
			nhieuNgoiHeaderView.setData(mcursor);
			mcursor.close();
			// /
		}

		moi_list.setOnItemClickListener(this);

		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, null, null, User.NAME_CONTACT);
		adaper = new MoiDvChoNhieuNguoiAdaper(getActivity(), cursor, service_code) {

			@Override
			public void addOrRemove(final String _id, boolean isAdd, int postition) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(getActivity());
					addItemView.setMId(_id, postition);

					moinhieudichvu_dialog_list_hor.addView(addItemView);
					Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this.getView().findViewById(R.id.moi), (moinhieudichvu_dialog_list_hor.getChildCount() == 0));
					Conts.addViewScale(addItemView);
					addItemView.setOnClickListener(new View.OnClickListener() {
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
									Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this.getView().findViewById(R.id.moi), (moinhieudichvu_dialog_list_hor.getChildCount() == 0));

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
									Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this.getView().findViewById(R.id.moi), (moinhieudichvu_dialog_list_hor.getChildCount() == 0));

								}
							});
							break;
						}
					}
				}
			}

			@Override
			public void addOrRemoveSdt(boolean isAdd, final String sdt, int position) {
				if (isAdd) {
					final MoiNhieuSDTAddItemView addItemView = new MoiNhieuSDTAddItemView(getActivity());
					addItemView.setMId(sdt, position);

					moinhieudichvu_dialog_list_hor.addView(addItemView);
					Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this.getView().findViewById(R.id.moi), (moinhieudichvu_dialog_list_hor.getChildCount() == 0));

					addItemView.setOnClickListener(new View.OnClickListener() {
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
									Conts.showAlpha(MoiDvChoNhieuNguoiFragment.this.getView().findViewById(R.id.moi), (moinhieudichvu_dialog_list_hor.getChildCount() == 0));
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

		Conts.showAlpha(view.findViewById(R.id.moi), (moinhieudichvu_dialog_list_hor.getChildCount() == 0));
		return view;
	}

	protected void addSodt(String moidichvuchonhieunguoi_numberText) {

		// add
		while (moidichvuchonhieunguoi_numberText.startsWith("0")) {
			moidichvuchonhieunguoi_numberText = moidichvuchonhieunguoi_numberText.substring(1, moidichvuchonhieunguoi_numberText.length());
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
				// Conts.showDialogThongbao(getActivity(), response.toString());
				adaper.addSdt((sdt.startsWith("84") ? "" : "84") + sdt, getActivity());
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
						message = getActivity().getString(R.string.sdtkhongphaicuabiettel);
					}
					Conts.showDialogThongbao(getActivity(), message);
				}

				progressDialog.dismiss();
			}

			@Override
			public void onError() {
				onError("");
			}
		});

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			getActivity().onBackPressed();
		} else if (v.getId() == R.id.moi) {

			Conts.hiddenKeyBoard(getActivity());
			boardView.setVisibility(View.GONE);
			mkeyboard(false);
			gotoLoiMoi(getArguments().getString(DichVuStore.ID));
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
			mkeyboard.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.abc_alpha_in));
		} else {
			// Animation animation = AnimationUtils.loadAnimation(getActivity(),
			// R.anim.abc_alpha_out);
			// animation.setAnimationListener(new VTAnimationListener() {
			// @Override
			// public void onAnimationEnd(Animation animation) {
			// super.onAnimationEnd(animation);
			// //mkeyboard.setVisibility(View.GONE);
			// }
			// });
			// mkeyboard.startAnimation(animation);
			//
			mkeyboard.setVisibility(View.GONE);

		}
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
			user = Conts.getSDT(user);

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

		public void setMId(String _id, int position) {
			mId = _id;

			Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s =='%s'", User._ID, mId), null, null);

			if (cursor != null && cursor.moveToNext()) {
				((TextView) findViewById(R.id.moinhieudichvu_item_tv_name)).setText(User.getName(cursor));
				String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
				String contact_id = Conts.getStringCursor(cursor, User.contact_id);
				Conts.showAvatarContact(((ImageView) findViewById(R.id.imageView1)), avatar, contact_id, Conts.resavatar()[position % Conts.resavatar().length]);
			} else {
				((TextView) findViewById(R.id.moinhieudichvu_item_tv_name)).setText(_id);
			}

			Conts.getSDT(findViewById(R.id.moinhieudichvu_item_tv_name));
		}
	}

	private MoiNhieuNgoiHeaderView nhieuNgoiHeaderView;

	private class MoiNhieuNgoiHeaderView extends LinearLayout {
		public MoiNhieuNgoiHeaderView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinghieunguoi_header, this);
		}

		public void setData(Cursor mcursor) {
			Conts.setTextViewCursor(findViewById(R.id.name), mcursor, DichVuStore.service_name);
			Conts.setTextViewCursor(findViewById(R.id.gia), mcursor, DichVuStore.service_price);
			ImageView home_item_img_icon = (ImageView) findViewById(R.id.icon);
			String service_icon = Conts.getStringCursor(mcursor, DichVuStore.service_icon);
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