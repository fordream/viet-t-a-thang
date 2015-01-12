package com.aretha.slidemenudemo.fragment;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.DichVu;
import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.HomeAdapter;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private HomeAdapter adapter;
	ListView menu_left_list;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);

		View home_header = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_header, null);
		home_header.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		menu_left_list = (ListView) view.findViewById(R.id.home_list);
		// menu_left_list.addHeaderView(home_header);
		menu_left_list.setOnItemClickListener(this);

		Bundle bundle = new Bundle();

		execute(RequestMethod.GET, API.API_R004, bundle, new IContsCallBack() {
			@Override
			public void onStart() {
			}

			@Override
			public void onSuscess(JSONObject response) {
				callSHowData();
			}

			@Override
			public void onError(String message) {
				Conts.toast(getActivity(), message);
			}

			@Override
			public void onError() {
				Conts.toast(getActivity(), "onError");
			}
		});

		return view;
	}

	protected void callSHowData() {
		Cursor cursor = getActivity().getContentResolver().query(DichVu.CONTENT_URI, null, null, null, null);
		if (cursor != null) {
			adapter = new HomeAdapter(getActivity(), cursor) {

				@Override
				public void updateUI() {

				}

				@Override
				public void moiDVChoNhieuNguoi(ContentValues contentValues) {
					(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi(contentValues.getAsString(DichVu.ID));
				}
			};
			menu_left_list.setAdapter(adapter);
		}
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		(((RootMenuActivity) getActivity())).gotoChiTietDichVuFromHome(parent, view, position, id);
	}
}