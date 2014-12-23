package com.aretha.slidemenudemo.fragment;

import vnp.com.mimusic.R;
import vnp.com.mimusic.view.HeaderView;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class HuongDanBanHangFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.huongdanbanhang, null);
		HeaderView header = (HeaderView) view.findViewById(R.id.huongdanbanhang_header);
		header.setTextHeader(R.string.huongdanbanhang);
		header.showButton(true, false);
		header.setButtonLeftImage(true, R.drawable.btn_back);
		header.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.abc_slide_left_in, R.anim.abc_slide_right_out);

			}
		});
		return view;
	}

	@Override
	public void onClick(View v) {
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}