package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.Recomment;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.NewHomeAdapter;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.MusicListView;
import vnp.com.mimusic.view.ReCommentDichVuItemView;
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
import android.widget.LinearLayout;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private vnp.com.mimusic.view.MusicListView list;
	private View home_header;
	private LoadingView loadingView;

	@Override
	public void onCLickButtonLeft() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuLeft();
	}

	@Override
	public void onCLickButtonRight() {
		((NewMusicSlideMenuActivity) getActivity().getParent()).openMenuRight();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (loadingView.getVisibility() == View.GONE)
			updateUI();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);

		createHeader(getString(R.string.kenhbanvas), true, true);
		loadingView = Conts.getView(view, R.id.loadingView1);
		list = (MusicListView) view.findViewById(R.id.list);

		list.setOnItemClickListener(this);
		home_header = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_header, null);

		Conts.showView(home_header.findViewById(R.id.home_header_main), false);

		home_header.findViewById(R.id.dichvubanchay).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.dichvubanchay));
			}
		});

		list.addHeaderView(getHeaderView());
		list.addHeaderView(home_header);
		list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1));
		Bundle bundle = new Bundle();
		execute(RequestMethod.GET, API.API_R026, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				Conts.showView(loadingView, true);
			}

			@Override
			public void onSuscess(JSONObject response) {
				updateUI();
				Conts.showView(loadingView, false);
			}

			@Override
			public void onError(String message) {
				updateUI();
				Conts.showView(loadingView, false);
			}

			@Override
			public void onError() {
				onError("");
			}
		});
		// callSHowData();

		return view;
	}

	private void updateUI() {
		int current = list.getFirstVisiblePosition();
		LinearLayout gallery = Conts.getView(home_header, R.id.gallery1);

		// if (gallery.getChildCount() <= 0) {

		((ReCommentDichVuItemView) Conts.getView(gallery, R.id.recommen1)).setData(null);
		((ReCommentDichVuItemView) Conts.getView(gallery, R.id.recommen2)).setData(null);
		((ReCommentDichVuItemView) Conts.getView(gallery, R.id.recommen3)).setData(null);
		((ReCommentDichVuItemView) Conts.getView(gallery, R.id.recommen4)).setData(null);
		Cursor cursor = Recomment.getCursorFromDichvu(getActivity(), 3);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				Conts.showView(home_header.findViewById(R.id.home_header_main), true);
				while (cursor.moveToNext()) {
					ReCommentDichVuItemView dichVuItemView = null;
					if (cursor.getPosition() == 0) {
						dichVuItemView = Conts.getView(gallery, R.id.recommen1);
					} else if (cursor.getPosition() == 1) {
						dichVuItemView = Conts.getView(gallery, R.id.recommen2);
					} else if (cursor.getPosition() == 2) {
						dichVuItemView = Conts.getView(gallery, R.id.recommen3);
					}
					// ReCommentDichVuItemView dichVuItemView = new
					// ReCommentDichVuItemView(getActivity());
					// gallery.addView(dichVuItemView);
					if (dichVuItemView != null) {
						dichVuItemView.setData(cursor);
						dichVuItemView.setOnClickListener(new RecomendDvOnClickListener(Conts.getStringCursor(cursor, DichVu.ID)));
					}
				}
			} else {
				Conts.showView(home_header.findViewById(R.id.home_header_main), false);
			}
			cursor.close();
			// }
			// else {
			// Conts.showView(home_header.findViewById(R.id.home_header_main),
			// false);
			// }
		} else {
			Conts.showView(home_header.findViewById(R.id.home_header_main), true);

		}
		if (homeAdapter == null) {
			list.setAdapter(homeAdapter = new NewHomeAdapter(getActivity()) {

				@Override
				public void moiDVChoNhieuNguoi(String id) {
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(id);
				}

				@Override
				public void dangKy(ContentValues values) {
					new DangKyDialog(getActivity(), values) {
						public void updateUiDangKy() {
							updateUI();
						};
					}.show();
				}

				@Override
				public void xemall(String name) {
					(((RootMenuActivity) getActivity())).homeXemall(name);

				}

				@Override
				public void moiContactUser(String user, String name) {
					(((RootMenuActivity) getActivity())).moiContactUser(user, name);
				}
			});
		} else {
			homeAdapter.update();
		}
		// list.setSelection(current);
	}

	private NewHomeAdapter homeAdapter;

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		NewHomeItem homeItem = (NewHomeItem) parent.getItemAtPosition(position);
		if (homeItem.type == 2) {
			// call to dich vu
			(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHome(parent, view, position, id);
		}
	}

	private class RecomendDvOnClickListener implements OnClickListener {
		private String id = "";

		public RecomendDvOnClickListener(String id) {
			this.id = id;

		}

		@Override
		public void onClick(View v) {
			(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHome(id);
		}

	}
}