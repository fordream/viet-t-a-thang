package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.datastore.DichVuStore;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.NewHomeAdapter.UpdateSuccess;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.HomeDichVuItemView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.MusicListView;
import vnp.com.mimusic.view.NewHomeBlogView;
import android.content.ContentValues;
import android.content.Context;
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

		HeaderView headerView = new HeaderView(getActivity());
		headerView.showHeader(true);
		list.addHeaderView(headerView);

		addDichvuDexuat(list);
		addSothuebaonenmoi(list);
		addDichvuBanChay(list);

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
		if (sothuebaonenmoi != null)
			sothuebaonenmoi.update();
		if (dichvubanchay != null)
			dichvubanchay.update();
		if (dichcudexuat != null)
			dichcudexuat.update();
		if (adapter != null)
			adapter.notifyDataSetChanged();
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		NewHomeItem homeItem = (NewHomeItem) parent.getItemAtPosition(position);
		if (homeItem.type == 2) {
			(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHome(parent, view, position, id);
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
				view = new HomeDichVuItemView(parent.getContext());
			}
			JSONObject object = (JSONObject) getItem(position);

			final ContentValues values = new ContentValues();
			values.put("name", String.format(parent.getContext().getString(R.string.title_dangky), Conts.getString(object, DichVuStore.service_name)));
			values.put(DichVuStore.service_name, Conts.getString(object, DichVuStore.service_name));
			values.put(DichVuStore.service_code, Conts.getString(object, DichVuStore.service_code));
			String content = String.format(parent.getContext().getString(R.string.xacnhandangky_form), Conts.getString(object, DichVuStore.service_name),
					Conts.getString(object, DichVuStore.service_price));
			values.put("content", content);
			values.put(DichVuStore.ID, Conts.getString(object, DichVuStore.ID));
			values.put("type", "dangky");
			final String service_code = Conts.getString(object, DichVuStore.service_code);
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					((RootMenuActivity) parent.getContext()).gotoChiTietDichVuFromHome(service_code);
				}
			});

			view.findViewById(R.id.home_item_right_control_2).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(service_code, position);
				}
			});

			final boolean isDangKy = dichVuStore.isRegister(Conts.getString(object, DichVuStore.service_code));

			if (isDangKy) {
				view.findViewById(R.id.home_item_right_control_1).setOnClickListener(null);
			} else {
				view.findViewById(R.id.home_item_right_control_1).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dangky(values);
					}
				});
			}
			((HomeDichVuItemView) view).setData(object, position);
			return view;
		}

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
					JSONArray data = new DichVuStore(getContext()).getDichvu();
					if (data != null) {
						if (data.length() <= 3) {
							array = data;
						} else {
							for (int i = 0; i < 3; i++) {
								try {
									array.put(data.get(i));
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

	public void dangky(final ContentValues values) {

		new DangKyDialog(getActivity(), values) {
			public void updateUiDangKy() {
				Conts.showDialogThongbao(getContext(), String.format(getContext().getString(R.string.bandangkythanhcongdichvu), values.getAsString(DichVuStore.service_name)));
				updateUI(updateSuccess);
			};
		}.show();
	}
}