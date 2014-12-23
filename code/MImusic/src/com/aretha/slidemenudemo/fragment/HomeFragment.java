package com.aretha.slidemenudemo.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import vnp.com.mimusic.R;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.HomeAdapter;
import vnp.com.mimusic.view.HeaderView;
import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class HomeFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.home, null);

		View home_header = ((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.home_header, null);
		home_header.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		ListView menu_left_list = (ListView) view.findViewById(R.id.home_list);
		menu_left_list.addHeaderView(home_header);
		menu_left_list.setOnItemClickListener(this);
		List<ContentValues> objects = new ArrayList<ContentValues>();
		addBlock(R.string.khuyenmai, objects);
		addBlock(R.string.recoment, objects);

		menu_left_list.setAdapter(new HomeAdapter(getActivity(), objects) {
			@Override
			public void moiDVChoNhieuNguoi() {
				(((RootMenuActivity) getActivity())).gotoMoiDvChoNhieuNguoi();
			}
		});

		return view;
	}

	private void addBlock(int recoment, List<ContentValues> objects) {

		for (int i = 0; i < 10; i++) {
			ContentValues contentValues = new ContentValues();
			contentValues.put("type", false);

			contentValues.put("dangky", new Random().nextBoolean());
			contentValues.put("icon", "icon");
			contentValues.put("name", "Dịch vụ Imusiz " + i);
			contentValues.put("link", "http://imusiz.vn/" + i);
			contentValues
					.put("content",
							"Website nhạc trực tuyến lớn nhất VN, đầy đủ album, video clip tất cả các thể loại, cập nhật liên tục bài hát mới, ca khúc hot, MV chất lượng cao, cài đặt nhạc chờ, nhạc chuông hay nhất hiện nay. ... Infinite F · Zing Music Awards 2014 ");
			objects.add(contentValues);
		}
	}

	@Override
	public void onClick(View v) {

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		(((RootMenuActivity) getActivity())).gotoChiTietDichVu(parent, view, position, id);
	}
}