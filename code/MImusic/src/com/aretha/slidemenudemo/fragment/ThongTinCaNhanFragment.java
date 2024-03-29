package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.datastore.AccountStore;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.viettel.vtt.vdealer.R;

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
		Conts.showView(loadingView, false);
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
				if (getActivity() != null) {
					Conts.showView(loadingView, false);

					showData(view);
				}
			}

			@Override
			public void onStart() {
				// Conts.showView(loadingView, true);
			}

			@Override
			public void onError(String message) {
				
				if (getActivity() != null) {
					Conts.showDialogDongYCallBack(getActivity(), message);
//					Conts.toast(getActivity(), message);
					Conts.showView(loadingView, false);
				}
			}

		});

		return view;
	}

	private void showData(View view) {

		try {
			if (view == null)
				view = getView();

			((TextView) view.findViewById(R.id.text_name)).setText(accountStore.getStringInFor(AccountStore.fullname));
			((TextView) view.findViewById(R.id.text_bidanh)).setText(accountStore.getStringInFor(AccountStore.nickname));
			((TextView) view.findViewById(R.id.text_ngaysinh)).setText(accountStore.getStringInFor(AccountStore.birthday));
			((TextView) view.findViewById(R.id.text_diachi)).setText(accountStore.getStringInFor(AccountStore.address));
			((TextView) view.findViewById(R.id.text_sogiaodichthanhcongtrongthang)).setText(getText(accountStore.getStringInFor(AccountStore.exchange_number_month)));
			((TextView) view.findViewById(R.id.text_sogiaodichthanhcong)).setText(getText(accountStore.getStringInFor(AccountStore.exchange_number)));
			((TextView) view.findViewById(R.id.text_sotienhoahongtrongthang)).setText(getText(accountStore.getStringInFor(AccountStore.poundage_month)) + " " + getString(R.string.vnd));
			((TextView) view.findViewById(R.id.text_sotienhoahong)).setText(getText(accountStore.getStringInFor(AccountStore.poundage)) + " " + getString(R.string.vnd));
			String cover = accountStore.getStringInFor(AccountStore.cover);
			Conts.displayImageCover(cover, menu_left_img_cover);

			String avatar = accountStore.getStringInFor(AccountStore.avatar);
			Conts.showInforAvatar(avatar, menu_left_img_avatar);

			TextView menu_left_tv_name = (TextView) view.findViewById(R.id.menu_left_tv_name);
			menu_left_tv_name.setText(String.format("%s (%s)", accountStore.getStringInFor(AccountStore.fullname), Conts.getSDT(accountStore.getUser())));
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
		// (((RootMenuActivity)
		// getActivity())).gotoChiTietCaNhanTungDichVu1(parent, view, position,
		// id);
	}
}