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
import vnp.com.mimusic.view.NewHomeBlogView;
import vnp.com.mimusic.view.ReCommentDichVuItemView;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;

import com.meetme.android.horizontallistview.HorizontalListView;
import com.vnp.core.scroll.VasHomeScrollListView;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	private vnp.com.mimusic.view.MusicListView list;
	// private HomeHeaderView home_header;
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

		addDichvuDexuat(list);
		addSothuebaonenmoi(list);
		addDichvuBanChay(list);

		list.setAdapter(new ArrayAdapter<String>(getActivity(), 0, new String[] {}));

		updateUI(updateSuccess);

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

			@Override
			public void onError() {
				onError("");
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
						Conts.showDialogThongbao(getContext(), String.format(getContext().getString(R.string.bandangkythanhcongdichvu), values.getAsString(DichVu.service_name)));
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

		sothuebaonenmoi.update();
		dichvubanchay.update();
		dichcudexuat.update();
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

}