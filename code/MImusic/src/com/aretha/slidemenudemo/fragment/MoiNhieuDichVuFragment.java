package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.MoiDanhBaAdapter;
import vnp.com.mimusic.adapter.MoiNhieuDichVuAdapter;
import vnp.com.mimusic.view.HeaderView;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

public class MoiNhieuDichVuFragment extends Fragment implements android.view.View.OnClickListener {
	private LinearLayout moinhieudichvu_dialog_list_hor;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.moinhieudichvu_dialog, null);

		moinhieudichvu_dialog_list_hor = (LinearLayout) view.findViewById(R.id.moinhieudichvu_dialog_list_hor);

		moinhieudichvu_dialog_list_hor.addView(new MoiNhieuDichVuAddItemView(getActivity()));
		moinhieudichvu_dialog_list_hor.addView(new MoiNhieuDichVuAddItemView(getActivity()));
		moinhieudichvu_dialog_list_hor.addView(new MoiNhieuDichVuAddItemView(getActivity()));
		moinhieudichvu_dialog_list_hor.addView(new MoiNhieuDichVuAddItemView(getActivity()));
		moinhieudichvu_dialog_list_hor.addView(new MoiNhieuDichVuAddItemView(getActivity()));

		HeaderView maumoi_headerview = (HeaderView) view.findViewById(R.id.moinhieudichvu_dialog_headerview);
		maumoi_headerview.setTextHeader(R.string.moi);
		maumoi_headerview.setButtonLeftImage(true, R.drawable.btn_back);
		maumoi_headerview.setButtonRightImage(false, R.drawable.btn_back);
		maumoi_headerview.setButtonMoi(true);
		
		maumoi_headerview.findViewById(R.id.header_btn_right_moi).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
		
		maumoi_headerview.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
			}
		});
		ListView maumoi_list = (ListView) view.findViewById(R.id.moinhieudichvu_dialog_list);
		maumoi_list.setAdapter(new MoiNhieuDichVuAdapter(getActivity(), new String[] { "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a", "a" }));

		return view;
	}

	@Override
	public void onClick(View v) {
	}

	private class MoiNhieuDichVuAddItemView extends LinearLayout {
		public MoiNhieuDichVuAddItemView(Context context) {
			super(context);
			((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.moinhieudichvu_add_item, this);
			findViewById(R.id.x).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					moinhieudichvu_dialog_list_hor.removeView(MoiNhieuDichVuAddItemView.this);
				}
			});
		}

	}
}