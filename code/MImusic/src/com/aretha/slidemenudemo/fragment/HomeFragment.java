package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.Recomment;
import vnp.com.db.VasContact;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.NewHomeAdapter.UpdateSuccess;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.MusicListView;
import vnp.com.mimusic.view.NewHomeBlogView;
import vnp.com.mimusic.view.NewHomeItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;

import com.viettel.vtt.vdealer.R;
import com.vnp.core.scroll.VasHomeScrollListView;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private vnp.com.mimusic.view.MusicListView list;
	// private HomeHeaderView home_header;
	private LoadingView loadingView;
	private HeaderView headerView;
	private NewHomeAdapter adapter;

	@Override
	public void onCLickButtonLeft() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuLeft();
	}

	@Override
	public void onCLickButtonRight() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuRight();
	}

	@Override
	public void onResume() {
		super.onResume();
		updateUI(updateSuccess);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);
		loadHeader(view, R.id.header, R.string.kenhbanvas, true, true);
		adapter = new NewHomeAdapter(getActivity());

		loadingView = Conts.getView(view, R.id.loadingView1);
		list = (MusicListView) view.findViewById(R.id.list);
		list.setOnItemClickListener(this);
		HeaderView headerView = new HeaderView(getActivity());
		headerView.showHeader(true);
		list.addHeaderView(headerView);

		addDichvuDexuat(list);
		// addSothuebaonenmoi(list);
		// addDichvuBanChay(list);

		list.setAdapter(adapter);
		adapter.getFilter().filter("");

		Bundle bundle = new Bundle();
		execute(RequestMethod.GET, API.API_R026, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				Conts.showView(loadingView, false);
			}

			@Override
			public void onSuscess(JSONObject response) {
				Conts.showView(loadingView, false);
				updateUI(updateSuccess);

			}

			@Override
			public void onError(String message) {
				updateUI(updateSuccess);
			}

		});

		new VasHomeScrollListView(getHeaderView(), headerView, new ListView[] { list }, getActivity());

		return view;
	}

	private NewHomeBlogView dichcudexuat, dichvubanchay, sothuebaonenmoi;

	private void addDichvuDexuat(MusicListView list2) {
		dichcudexuat = new NewHomeBlogView(getActivity()) {

			@Override
			public int type() {
				return 0;
			}

			@Override
			public void onClickHeader() {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.dichvudexuat));
			}

			@Override
			public void enableScroll(boolean b) {
				super.enableScroll(b);

			}
		};
		list.addHeaderView(dichcudexuat);
	}

	private void addDichvuBanChay(MusicListView list2) {
		dichvubanchay = new NewHomeBlogView(getActivity()) {

			@Override
			public int type() {
				return 2;
			}

			@Override
			public void onClickHeader() {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.dichvubanchay));
			}

			@Override
			public void dangky(final ContentValues values) {
				super.dangky(values);
				new DangKyDialog(getActivity(), values) {
					public void updateUiDangKy() {
						Conts.showDialogThongbao(getContext(), String.format(getContext().getString(R.string.bandangkythanhcongdichvu), values.getAsString(DichVuStore.service_name)));
						updateUI(updateSuccess);
					};
				}.show();
			}
		};
		list.addHeaderView(dichvubanchay);
	}

	private void addSothuebaonenmoi(MusicListView list) {
		sothuebaonenmoi = new NewHomeBlogView(getActivity()) {

			@Override
			public int type() {
				return 1;
			}

			@Override
			public void onClickHeader() {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.moithanhvien));
			}
		};

		list.addHeaderView(sothuebaonenmoi);
	}

	private UpdateSuccess updateSuccess = new UpdateSuccess() {

		@Override
		public void onUpdateSuccess() {
			Conts.showView(loadingView, false);
		}
	};

	private void updateUI(UpdateSuccess updateSuceess) {
		// if (sothuebaonenmoi != null)
		// sothuebaonenmoi.update();
		// if (dichvubanchay != null)
		// dichvubanchay.update();
		if (dichcudexuat != null)
			dichcudexuat.update();
		if (adapter != null) {
			adapter.getFilter().filter("");
			adapter.notifyDataSetChanged();
		}
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		JSONObject object = (JSONObject) parent.getItemAtPosition(position);

		String type = Conts.getString(object, "xtype");
		if ("2".equals(type)) {
			// (((RootMenuActivity)
			// getActivity())).gotoChiTietDichVuFromHome(parent, view, position,
			// id);
			(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHomeJson(Conts.getString(object, DichVuStore.service_code));
		} else if ("0".equals(type)) {
			// TODO
			String strHeader = Conts.getString(object, "strHeader");
			if (strHeader.equals(getString(R.string.moithanhvien))) {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.moithanhvien));
			} else {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.dichvubanchay));
			}
		} else if ("1".equals(type)) {
			// TODO
			String name = Conts.getString(object, "name");
			String user = Conts.getString(object, "user");
			(((RootMenuActivity) getActivity())).moiContactUser(user, name, position);
		}
	}

	private class NewHomeAdapter extends ArrayAdapter<JSONObject> {
		public NewHomeAdapter(Context context) {
			super(context, 0);
		}

		private JSONArray array = new JSONArray();

		@Override
		public int getCount() {
			return array.length();
		}

		@Override
		public JSONObject getItem(int position) {
			try {
				return array.getJSONObject(position);
			} catch (Exception exception) {
				return null;
			}
		}

		@Override
		public View getView(final int position, View view, final ViewGroup parent) {

			if (view == null) {
				view = new NewHomeItemView(parent.getContext());
			}

			String type = Conts.getString(getItem(position), "xtype");
			if ("0".equals(type)) {
				((NewHomeItemView) view).showHeader(Conts.getString(getItem(position), "strHeader"));
			} else if ("1".equals(type)) {
				((NewHomeItemView) view).showContact(Conts.getString(getItem(position), "name"), Conts.getString(getItem(position), "user"), Conts.getString(getItem(position), "avatar"),
						Conts.getString(getItem(position), "contact_id"), position);
			} else if ("2".equals(type)) {
				((NewHomeItemView) view).showDichvu(getItem(position), position - xPosition);
			}
			final String service_code = Conts.getString(getItem(position), DichVuStore.service_code);
			view.findViewById(R.id.home_item_right_control_1).setOnClickListener(new DangKyOnClickListener(service_code, position));
			view.findViewById(R.id.home_item_right_control_2).setOnClickListener(new MoiOnClickListener(service_code, position));
			return view;
		}

		private int xPosition = 0;

		@Override
		public Filter getFilter() {
			return new Filter() {

				@Override
				protected void publishResults(CharSequence constraint, FilterResults results) {
					array = (JSONArray) results.values;
					notifyDataSetChanged();
				}

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults filterResults = new FilterResults();

					JSONArray array = new JSONArray();
					/**
					 * add STB Nen moi
					 */

					Cursor cursorUserRecomment = Recomment.getCursorFromUser(getContext(), 5);
					if (cursorUserRecomment != null) {
						if (cursorUserRecomment.getCount() > 0) {
							array.put(jsonHeader("0", getString(R.string.moithanhvien)));
						}
						while (cursorUserRecomment.moveToNext()) {
							final String user = Conts.getStringCursor(cursorUserRecomment, VasContact.PHONE);
							final String name = Conts.getStringCursor(cursorUserRecomment, VasContact.NAME_CONTACT);
							final String avatar = Conts.getStringCursor(cursorUserRecomment, VasContact.AVATAR);

							final String contact_id = Conts.getStringCursor(cursorUserRecomment, VasContact.contact_id);
							array.put(jsonUser("1", user, name, avatar, contact_id));
						}
						cursorUserRecomment.close();
					}

					/**
					 * add dichvu
					 */
					JSONArray data = new DichVuStore(getContext()).getDichvu();
					array.put(jsonHeader("0", getString(R.string.dichvubanchay)));
					xPosition = array.length();
					if (data != null) {
						if (data.length() <= 3) {
							for (int i = 0; i < data.length(); i++) {
								try {

									JSONObject json = data.getJSONObject(i);
									json.put("xtype", "2");
									array.put(json);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						} else {
							for (int i = 0; i < 3; i++) {
								try {
									JSONObject json = data.getJSONObject(i);
									json.put("xtype", "2");
									array.put(json);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}
					}
					filterResults.values = array;
					return filterResults;
				}

			};
		}

	}

	private Object jsonHeader(String type, String strHeader) {
		JSONObject object = new JSONObject();
		try {
			object.put("xtype", type);
			object.put("strHeader", strHeader);
		} catch (JSONException e) {
		}
		return object;
	}

	/**
	 * 
	 * @param type
	 * @param user
	 * @param name
	 * @param avatar
	 * @param contact_id
	 * @return
	 */
	private Object jsonUser(String type, String user, String name, String avatar, String contact_id) {
		JSONObject object = new JSONObject();
		try {
			object.put("xtype", "1");
			object.put("user", user);
			object.put("name", name);
			object.put("avatar", avatar);
			object.put("contact_id", contact_id);

		} catch (JSONException e) {
		}
		return object;
	}

	public void dangky(final ContentValues values) {

		new DangKyDialog(getActivity(), values) {
			public void updateUiDangKy() {
				Conts.showDialogThongbao(getContext(), String.format(getContext().getString(R.string.bandangkythanhcongdichvu), values.getAsString(DichVuStore.service_name)));
				updateUI(updateSuccess);
			};
		}.show();
	}

	private class DangKyOnClickListener implements OnClickListener {
		private String serviceCode = "";
		private int position = 0;

		public DangKyOnClickListener(String serviceCode, int position) {
			this.serviceCode = serviceCode;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			JSONObject object = new DichVuStore(getActivity()).getDvByServiceCode(serviceCode);
			ContentValues values = new ContentValues();
			values.put("name", String.format(getString(R.string.title_dangky), Conts.getString(object, DichVuStore.service_name)));
			values.put(DichVuStore.service_name, Conts.getString(object, DichVuStore.service_name));
			values.put(DichVuStore.service_code, Conts.getString(object, DichVuStore.service_code));
			String content = String.format(getString(R.string.xacnhandangky_form), Conts.getString(object, DichVuStore.service_name), Conts.getString(object, DichVuStore.service_price));
			values.put("content", content);
			values.put(DichVuStore.ID, Conts.getString(object, DichVuStore.ID));
			values.put("type", "dangky");
			dangky(values);
		}
	}

	private class MoiOnClickListener implements OnClickListener {
		private String serviceCode = "";
		private int position = 0;

		public MoiOnClickListener(String serviceCode, int position) {
			this.serviceCode = serviceCode;
			this.position = position;
		}

		@Override
		public void onClick(View v) {
			(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(serviceCode, 0);
		}
	}
}