package com.aretha.slidemenudemo.fragment;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ThongTinCaNhanFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();

		getActivity().registerReceiver(update, new IntentFilter("updateprofile"));
	}

	private BroadcastReceiver update = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			showData(getView());
		}
	};

	@Override
	public void onPause() {
		super.onPause();
		getActivity().unregisterReceiver(update);
	}

	private ImageView menu_left_img_cover, menu_left_img_avatar;
	private LoadingView loadingView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.thongtincanhan, null);
		loadingView = (LoadingView) view.findViewById(R.id.loadingView1);

		menu_left_img_cover = (ImageView) view.findViewById(R.id.menu_left_img_cover);
		menu_left_img_avatar = (ImageView) view.findViewById(R.id.menu_left_img_avatar);
		HeaderView headerView = (HeaderView) view.findViewById(R.id.activity_login_header);
		headerView.setTextHeader(R.string.thongtincanhan);
		headerView.setButtonLeftImage(true, R.drawable.btn_back);
		headerView.setButtonRightImage(true, R.drawable.thongtincanhan_right);

		headerView.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		headerView.findViewById(R.id.header_btn_right).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				(((RootMenuActivity) getActivity())).gotoChinhsuaThongTinCaNhan();
			}
		});
		showData(view);
		execute(RequestMethod.GET, API.API_R006, new Bundle(), new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				Conts.showView(loadingView, false);

				showData(view);
			}

			@Override
			public void onStart() {
				Conts.showView(loadingView, true);
			}

			@Override
			public void onError(String message) {
				Conts.toast(getActivity(), message);
				Conts.showView(loadingView, false);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "onError");
				Conts.showView(loadingView, false);
			}
		});

		return view;
	}

	private void showData(View view) {
		try {
			if (view == null)
				view = getView();
			Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s = '1'", User.STATUS), null, null);
			if (cursor != null && cursor.getCount() >= 1) {
				cursor.moveToNext();

				((TextView) view.findViewById(R.id.text_name)).setText(Conts.getName(cursor));
				((TextView) view.findViewById(R.id.text_bidanh)).setText(cursor.getString(cursor.getColumnIndex(User.nickname)));
				((TextView) view.findViewById(R.id.text_ngaysinh)).setText(cursor.getString(cursor.getColumnIndex(User.birthday)));
				((TextView) view.findViewById(R.id.text_diachi)).setText(cursor.getString(cursor.getColumnIndex(User.address)));
				((TextView) view.findViewById(R.id.text_sogiaodichthanhcongtrongthang)).setText(getText(cursor.getString(cursor.getColumnIndex(User.exchange_number_month))));
				((TextView) view.findViewById(R.id.text_sogiaodichthanhcong)).setText(getText(cursor.getString(cursor.getColumnIndex(User.exchange_number))));
				((TextView) view.findViewById(R.id.text_sotienhoahongtrongthang)).setText(getText(cursor.getString(cursor.getColumnIndex(User.poundage_month))) + getString(R.string.vnd));
				((TextView) view.findViewById(R.id.text_sotienhoahong)).setText(getText(cursor.getString(cursor.getColumnIndex(User.poundage))) + getString(R.string.vnd));
				String cover = cursor.getString(cursor.getColumnIndex(User.COVER));
				Conts.showImage(cover, menu_left_img_cover, 0);

				String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
				Conts.showImage(avatar, menu_left_img_avatar, R.drawable.no_avatar);
				cursor.close();
			}
		} catch (Exception exception) {

		}
	}

	private String getText(String str) {
		if (str == null)
			return "0";

		return str;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position, id);
	}
}