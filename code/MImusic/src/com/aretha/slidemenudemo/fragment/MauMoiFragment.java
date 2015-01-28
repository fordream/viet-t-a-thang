package com.aretha.slidemenudemo.fragment;

import org.json.JSONArray;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.db.MauMoi;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MauMoiAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.maumoi_dialog, null);
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

//		view.findViewById(R.id.recomment_icon_bottom).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				getActivity().onBackPressed();
//			}
//		});

		if (!type) {
			String selection = DichVu.ID + "='" + id + "'";
			final Cursor mcursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);
			if (mcursor != null && mcursor.getCount() >= 1) {
				mcursor.moveToNext();
				service_code = mcursor.getString(mcursor.getColumnIndex(DichVu.service_code));
				mcursor.close();
			}
		}

		JSONArray array = MauMoi.getCursorMauMoiListJson(getActivity(), service_code);
		if (array.length() > 0) {
			adaper = new MauMoiAdaper(getActivity(), new JSONObject[] {}, array);
			maumoi_list.setAdapter(adaper);
		} else {
			Bundle bundle = new Bundle();
			bundle.putString("service_code", service_code);
			execute(RequestMethod.GET, API.API_R022, bundle, new IContsCallBack() {
				@Override
				public void onSuscess(JSONObject response) {
					JSONArray array = MauMoi.getCursorMauMoiListJson(getActivity(), service_code);
					// try {
					// JSONArray array = response.getJSONArray("data");
					adaper = new MauMoiAdaper(getActivity(), new JSONObject[] {}, array);
					maumoi_list.setAdapter(adaper);

					// } catch (JSONException e) {
					// }
					Conts.showView(loading, false);
				}

				@Override
				public void onStart() {
					Conts.showView(loading, true);
				}

				@Override
				public void onError(String message) {
					Conts.showDialogThongbao(getActivity(), message);
					Conts.showView(loading, false);
				}

				@Override
				public void onError() {
					Conts.toast(getActivity(), "onError");
					Conts.showView(loading, false);
				}
			});
		}
		view.findViewById(R.id.recomment_main_background).setOnTouchListener(new OnTouchListener() {

			float x = 0, y = 0;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x = event.getX();
					y = event.getY();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// close();
					if (event.getY() - y < 0) {
						close();
					}
				} else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
					if (event.getY() - y < 0) {
						close();
					}
					// close();
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getY() - y >= 0) {
					} else {
						float dy = event.getY() - y;
						RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.findViewById(R.id.maumoi_main).getLayoutParams();
						params.setMargins(0, (int) dy, 0, 0);
						view.findViewById(R.id.maumoi_main).setLayoutParams(params);
						try {
							float f = ((float) Math.abs(dy)) / (float) view.findViewById(R.id.maumoi_main).getHeight();
							view.findViewById(R.id.recomment_main_background).setAlpha(1 - f);
						} catch (Exception exception) {

						}
					}
				}
				return true;
			}
		});
		return view;
	}

	protected void close() {
		getActivity().onBackPressed();
	}

	protected void moi() {
		if (adaper == null || adaper != null && Conts.isBlank(adaper.getTemplate_id())) {
			Conts.showDialogThongbao(getActivity(), getString(R.string.banchuachonloimoi));
			return;
		}
		Bundle bundle = new Bundle();
		bundle.putString("template_id", adaper.getTemplate_id());
		if (!type) {
			bundle.putString("service_code", service_code);
			bundle.putString("customers", customers);
		} else {
			bundle.putString("customers", sdt);
			bundle.putString("service_code", service_codes);
		}

		((RootMenuActivity) getActivity()).moi(type, bundle);

	}

	@Override
	public void onClick(View v) {
	}
}