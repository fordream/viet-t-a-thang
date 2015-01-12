package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MauMoiAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.DialogCallBack;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.LogUtils;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MauMoiFragment extends BaseFragment implements android.view.View.OnClickListener {
	private View loading;
	private MauMoiAdaper adaper;
	private boolean type = false;
	private String id, customers, sdt, service_code, service_codes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		type = getArguments().getBoolean("type");
		id = getArguments().getString("id");
		customers = getArguments().getString("customers");
		sdt = getArguments().getString("sdt");
		service_code = getArguments().getString("service_code");
		service_codes = getArguments().getString("service_codes");

		LogUtils.e("service_codes", service_codes + "");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.maumoi_dialog, null);
		loading = view.findViewById(R.id.loadingView1);
		view.setOnClickListener(null);
		final ListView maumoi_list = (ListView) view.findViewById(R.id.maumoi_list);

		view.findViewById(R.id.moinhieudichvu_dialog_moi).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				moi();
			}
		});
		view.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		view.findViewById(R.id.recomment_icon_bottom).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});

		if (!type) {
			String selection = DichVu.ID + "='" + id + "'";
			final Cursor mcursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);
			if (mcursor != null && mcursor.getCount() >= 1) {
				mcursor.moveToNext();
				service_code = mcursor.getString(mcursor.getColumnIndex(DichVu.service_code));
				mcursor.close();
			}
		}
		Bundle bundle = new Bundle();
		bundle.putString("service_code", service_code);
		execute(RequestMethod.GET, API.API_R022, bundle, new IContsCallBack() {

			@Override
			public void onSuscess(JSONObject response) {
				try {
					JSONArray array = response.getJSONArray("data");
					adaper = new MauMoiAdaper(getActivity(), new JSONObject[] {}, array);
					maumoi_list.setAdapter(adaper);

				} catch (JSONException e) {
				}
				Conts.showView(loading, false);
			}

			@Override
			public void onStart() {
				Conts.showView(loading, true);
			}

			@Override
			public void onError(String message) {
				Conts.toast(getActivity(), message);
				Conts.showView(loading, false);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "onError");
				Conts.showView(loading, false);
			}
		});
		return view;
	}

	protected void moi() {
		Bundle bundle = new Bundle();
		bundle.putString("template_id", adaper.getTemplate_id());
		if (!type) {
			bundle.putString("service_code", service_code);
			bundle.putString("customers", customers);
		} else {
			bundle.putString("customers", sdt);
			bundle.putString("service_code", service_codes);
		}

		execute(RequestMethod.POST, !type ? API.API_R015 : API.API_R016, bundle, new IContsCallBack() {
			ProgressDialog dialog;

			@Override
			public void onSuscess(JSONObject response) {
				String message = "";
				try {
					message = response.getString("message");
				} catch (JSONException e1) {
				}

				if (Conts.isBlank(message)) {
					message = getActivity().getString(R.string.success_moi);
				} else {
					message = String.format("%s\n%s", message, getActivity().getString(R.string.success_moi));
				}
				Conts.showDialogDongYCallBack(getActivity(), message, new DialogCallBack() {
					@Override
					public void callback(Object object) {
						((RootMenuActivity) getActivity()).closeActivity();
					}
				});
				if (dialog != null)
					dialog.dismiss();
			}

			@Override
			public void onStart() {
				if (dialog == null)
					dialog = ProgressDialog.show(getActivity(), null, getActivity().getString(R.string.loading));
			}

			@Override
			public void onError(String message) {
				Conts.showDialogThongbao(getActivity(), message);
				if (dialog != null)
					dialog.dismiss();
			}

			@Override
			public void onError() {
				onError("onError");
			}
		});
	}

	@Override
	public void onClick(View v) {
	}
}