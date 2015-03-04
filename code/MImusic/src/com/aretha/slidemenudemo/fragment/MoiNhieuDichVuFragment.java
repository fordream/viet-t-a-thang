package com.aretha.slidemenudemo.fragment;

import vnp.com.db.VasContact;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiNhieuDichVuAdapter;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.MenuRightItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MoiNhieuDichVuFragment extends BaseFragment implements android.view.View.OnClickListener, OnTouchListener {

	private View moi;
	private LinearLayout moinhieudichvu_dialog_list_hor;
	private MoiNhieuDichVuAdapter adapter;
	private EditText moinhieudichvu_dialog_search;
	private String sdt = "";
	private MenuRightItemView menuright_item;
	private String LISTIDDVSUDUNG = "";
	private MoiNhieuDichVuHeader moiNhieuDichVuHeader;

	public void updateMoi() {
		Conts.showAlpha(moi, (moinhieudichvu_dialog_list_hor.getChildCount() == 0));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moinhieudichvu_dialog, null);
		ListView maumoi_list = (ListView) view.findViewById(R.id.moinhieudichvu_dialog_list);
		maumoi_list.setOnTouchListener(this);
		moi = Conts.getView(view, R.id.moi);
		moiNhieuDichVuHeader = new MoiNhieuDichVuHeader(getActivity());
		maumoi_list.addHeaderView(moiNhieuDichVuHeader);

		moinhieudichvu_dialog_search = (EditText) view.findViewById(R.id.moinhieudichvu_dialog_search);
		moinhieudichvu_dialog_list_hor = (LinearLayout) view.findViewById(R.id.moinhieudichvu_dialog_list_hor);

		view.findViewById(R.id.back).setOnClickListener(this);
		view.findViewById(R.id.moi).setOnClickListener(this);

		menuright_item = (MenuRightItemView) view.findViewById(R.id.menurightitem);
		int getPosition = getArguments().getInt("getPosition");

		String where = String.format("%s = '%s'", VasContact._ID, getArguments().getString(VasContact._ID));
		if (Conts.isBlank(getArguments().getString(VasContact._ID))) {
			sdt = getArguments().getString("msisdn");
			LISTIDDVSUDUNG = "";
			menuright_item.initData(getArguments().getString("name"));
			moiNhieuDichVuHeader.initData(getArguments().getString("name"));
			where = String.format("%s = '%s'", VasContact.PHONE, sdt);
			Cursor cursor = getActivity().getContentResolver().query(VasContact.CONTENT_URI, null, where, null, null);
			if (cursor != null && cursor.getCount() >= 1) {
				cursor.moveToNext();
				LISTIDDVSUDUNG = cursor.getString(cursor.getColumnIndex(VasContact.LISTIDDVSUDUNG));
				sdt = cursor.getString(cursor.getColumnIndex(VasContact.PHONE));

				menuright_item.initData(cursor, "", getPosition);

				moiNhieuDichVuHeader.initData(cursor, "", getPosition);
			}

			if (cursor != null) {
				cursor.close();
			}

		} else {
			Cursor cursor = getActivity().getContentResolver().query(VasContact.CONTENT_URI, null, where, null, null);
			if (cursor != null && cursor.getCount() >= 1) {
				cursor.moveToNext();
				LISTIDDVSUDUNG = cursor.getString(cursor.getColumnIndex(VasContact.LISTIDDVSUDUNG));
				sdt = cursor.getString(cursor.getColumnIndex(VasContact.PHONE));

				menuright_item.initData(cursor, "", getPosition);
				moiNhieuDichVuHeader.initData(cursor, "", getPosition);
			}

			if (cursor != null) {
				cursor.close();
			}
		}

		// Cursor cursorDV =
		// getActivity().getContentResolver().query(DichVu.CONTENT_URI, null,
		// null, null, null);
		Cursor cursorDV = null;
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
		maumoi_list.setAdapter(adapter = new MoiNhieuDichVuAdapter(getActivity(), dichVuStore.getDichvu()) {

			@Override
			public void addOrRemove(final String _id, boolean isAdd, String icon) {
				if (isAdd) {
					final MoiNhieuDichVuAddItemView addItemView = new MoiNhieuDichVuAddItemView(getActivity());
					moinhieudichvu_dialog_list_hor.addView(addItemView);
					Conts.addViewScale(addItemView);
					updateMoi();
					addItemView.setMId(_id);
					addItemView.setIcon(icon);

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
									updateMoi();
								}
							});
							adapter.remove(_id);
							adapter.notifyDataSetChanged();
						}
					});
				} else {
					for (int i = 0; i < moinhieudichvu_dialog_list_hor.getChildCount(); i++) {
						final MoiNhieuDichVuAddItemView child = ((MoiNhieuDichVuAddItemView) moinhieudichvu_dialog_list_hor.getChildAt(i));
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
									updateMoi();
								}
							});
							break;
						}
					}
				}
			}

		});
		updateMoi();
		return view;
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			getActivity().onBackPressed();
		} else if (v.getId() == R.id.moi) {
			int size = adapter.getListSelect().size();
			if (size == 0) {
				Conts.showDialogThongbao(getActivity(), getString(R.string.validatesodichvu));
				return;
			}

			ContentValues values = new ContentValues();

//			String content = String.format(getString(R.string.ban_co_muon_moi_sdt_nay), Conts.getSDT(sdt));
			String content = getString(R.string.ban_co_muon_moi_sdt_nays);
			values.put("name", getString(R.string.app_name));
			values.put("content", content);
			values.put("btn_right", getString(R.string.dongy));
			new DangKyDialog(getActivity(), values) {
				public void mOpen() {
					super.mOpen();
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
					(((RootMenuActivity) getActivity())).gotoLoiMoiNhieuDichVU(sdt, adapter.getService_code(adapter.getListSelect().get(0)), service_codes);
				};
			}.show();

			// (((RootMenuActivity) getActivity())).gotoLoiMoiNhieuDichVU(sdt,
			// adapter.getService_code(adapter.getListSelect().get(0)),
			// service_codes);
		}
	}

	private class MoiNhieuDichVuAddItemView extends LinearLayout {

		public MoiNhieuDichVuAddItemView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinhieudichvu_add_item, this);
		}

		public void setIcon(String icon) {
			ImageView moinhieudichvu_img_icon = (ImageView) findViewById(R.id.imageView1);
			moinhieudichvu_img_icon.setImageResource(R.drawable.no_avatar);
			// ImageLoaderUtils.getInstance(getActivity()).displayImage(icon,
			// moinhieudichvu_img_icon, R.drawable.no_image);
			Conts.showLogoDichvu(moinhieudichvu_img_icon, icon);
		}

		private String mId = "";

		public String getmId() {
			return mId;
		}

		public void setMId(String _id) {
			mId = _id;
		}
	}

	public class MoiNhieuDichVuHeader extends LinearLayout {

		public MoiNhieuDichVuHeader(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinhieudv_header, this);
		}

		public void initData(Cursor cursor, String string, int getPosition) {
			((MenuRightItemView) findViewById(R.id.menurightitem)).initData(cursor, string, getPosition);
		}

		public void initData(Cursor cursor, String string) {
			((MenuRightItemView) findViewById(R.id.menurightitem)).initData(cursor, string);
		}

		public void initData(String string) {
			((MenuRightItemView) findViewById(R.id.menurightitem)).initData(string);
		}

	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		Conts.hiddenKeyBoard(getActivity());
		return false;
	}
}