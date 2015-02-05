package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.Recomment;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.NewHomeAdapter;
import vnp.com.mimusic.adapter.NewHomeAdapter.UpdateSuccess;
import vnp.com.mimusic.adapter.data.NewHomeItem;
import vnp.com.mimusic.base.diablog.DangKyDialog;
import vnp.com.mimusic.main.NewMusicSlideMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import vnp.com.mimusic.view.MusicListView;
import vnp.com.mimusic.view.ReCommentDichVuItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.vnp.core.scroll.VasHomeScrollListView;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private vnp.com.mimusic.view.MusicListView list;
	private HomeHeaderView home_header;
	private LoadingView loadingView;
	private HeaderView headerView;

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
		updateUI(updateSuccess);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);

		loadHeader(view, R.id.header, R.string.kenhbanvas, true, true);

		loadingView = Conts.getView(view, R.id.loadingView1);
		list = (MusicListView) view.findViewById(R.id.list);

		HeaderView headerView = new HeaderView(getActivity());
		headerView.showHeader(true);
		list.addHeaderView(headerView);
		list.setOnItemClickListener(this);

		home_header = new HomeHeaderView(getActivity());

		home_header.findViewById(R.id.dichvubanchay).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).homeXemall(getString(R.string.dichvudexuat));
			}
		});

		headerView = new HeaderView(getActivity());
		headerView.showHeader(false);

		list.addHeaderView(headerView);
		list.addHeaderView(home_header);

		homeAdapter = new NewHomeAdapter(getActivity()) {

			@Override
			public void moiDVChoNhieuNguoi(String id) {
				(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(id);
			}

			@Override
			public void dangKy(ContentValues values) {
				new DangKyDialog(getActivity(), values) {
					public void updateUiDangKy() {
						updateUI(updateSuccess);
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
		};

		list.setAdapter(homeAdapter);

		Bundle bundle = new Bundle();
		execute(RequestMethod.GET, API.API_R026, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
				Conts.showView(loadingView, true);
			}

			@Override
			public void onSuscess(JSONObject response) {
				updateUI(updateSuccess);

			}

			@Override
			public void onError(String message) {
				updateUI(updateSuccess);

			}

			@Override
			public void onError() {
				onError("");
			}
		});

		new VasHomeScrollListView(getHeaderView().findViewById(R.id.header_main_content), headerView, new ListView[] { list }, getActivity());
		return view;
	}

	private UpdateSuccess updateSuccess = new UpdateSuccess() {

		@Override
		public void onUpdateSuccess() {
			Conts.showView(loadingView, false);
		}
	};

	private void updateUI(UpdateSuccess updateSuceess) {
		home_header.updateData();
		homeAdapter.update(updateSuceess);
	}

	private NewHomeAdapter homeAdapter;

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

	private class HomeHeaderView extends LinearLayout {

		public HomeHeaderView(Context context) {
			super(context);
			((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.new_home_header, this);
			Conts.showView(findViewById(R.id.home_header_main), false);
		}

		public void updateData() {

			new AsyncTask<String, String, String>() {
				Cursor cursor;

				protected String doInBackground(String[] params) {
					cursor = Recomment.getCursorFromDichvu(getActivity(), -1);
					return null;
				};

				protected void onPostExecute(String result) {
					LinearLayout gallery1 = Conts.getView(HomeHeaderView.this, R.id.gallery1);
					gallery1.removeAllViews();

					if (cursor != null) {
						if (cursor.getCount() > 0) {
							Conts.showView(home_header.findViewById(R.id.home_header_main), true);
							while (cursor.moveToNext()) {
								ReCommentDichVuItemView dichVuItemView = new ReCommentDichVuItemView(getContext());
								gallery1.addView(dichVuItemView);
								dichVuItemView.setData(cursor);
								dichVuItemView.setOnClickListener(new RecomendDvOnClickListener(Conts.getStringCursor(cursor, DichVu.ID)));
							}
						} else {
							Conts.showView(findViewById(R.id.home_header_main), false);
						}
						cursor.close();
					} else {
						Conts.showView(findViewById(R.id.home_header_main), true);
					}
				};
			}.execute("");

		}
	}

}