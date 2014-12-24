package com.aretha.slidemenudemo.fragment;

import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DateDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class InforFragment extends Fragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private ImageView menu_left_img_cover, menu_left_img_avatar, infor_cover_click_change;
	private TextView menu_left_tv_name;
	private Button activity_login_btn;
	private EditText infor_name, infor_bidanh, infor_diachi;
	private TextView infor_ngaysinh;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.infor, null);

		HeaderView header = (HeaderView) view.findViewById(R.id.infor_header);
		header.setTextHeader(R.string.thuongtinnguoidung);
		header.showButton(true, false);
		header.setButtonLeftImage(true, R.drawable.btn_back);
		header.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().finish();
				getActivity().overridePendingTransition(R.anim.abc_slide_left_in, R.anim.abc_slide_right_out);
			}
		});
		infor_cover_click_change = (ImageView) view.findViewById(R.id.infor_cover_click_change);
		menu_left_img_cover = (ImageView) view.findViewById(R.id.menu_left_img_cover);
		menu_left_img_avatar = (ImageView) view.findViewById(R.id.menu_left_img_avatar);
		menu_left_tv_name = (TextView) view.findViewById(R.id.menu_left_tv_name);
		activity_login_btn = (Button) view.findViewById(R.id.activity_login_btn);
		infor_name = (EditText) view.findViewById(R.id.infor_name);
		infor_bidanh = (EditText) view.findViewById(R.id.infor_didanh);
		infor_ngaysinh = (TextView) view.findViewById(R.id.infor_ngaysinh);
		infor_diachi = (EditText) view.findViewById(R.id.infor_address);

		/**
		 * get data
		 */

		showData();
		activity_login_btn.setOnClickListener(this);
		infor_cover_click_change.setOnClickListener(this);
		menu_left_img_avatar.setOnClickListener(this);
		infor_ngaysinh.setOnClickListener(this);
		return view;
	}

	private void showData() {
		Cursor cursor = getActivity().getContentResolver().query(User.CONTENT_URI, null, String.format("%s = '1'", User.STATUS), null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.moveToNext();
			menu_left_tv_name.setText(String.format("%s(%s)", Conts.getName(cursor), cursor.getString(cursor.getColumnIndex(User.USER))));
			infor_name.setText(Conts.getName(cursor));
			infor_bidanh.setText(cursor.getString(cursor.getColumnIndex(User.BIDANH)));
			infor_diachi.setText(cursor.getString(cursor.getColumnIndex(User.DIACHI)));
			infor_ngaysinh.setText(cursor.getString(cursor.getColumnIndex(User.NGAYSINH)));

			String cover = cursor.getString(cursor.getColumnIndex(User.COVER));

			if (!Conts.isBlank(cover)) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeFile(cover, options);
				menu_left_img_cover.setImageBitmap(bitmap);
			}

			String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));

			if (!Conts.isBlank(avatar)) {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				Bitmap bitmap = BitmapFactory.decodeFile(avatar, options);
				menu_left_img_avatar.setImageBitmap(bitmap);
			} else {
				menu_left_img_avatar.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.no_avatar));

			}
			cursor.close();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			String path = Conts.getPath(getActivity(), data.getData());
			if (path != null) {
				ContentValues contentValues = new ContentValues();
				contentValues.put(User.COVER, path);
				int index = getActivity().getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '1'", User.STATUS), null);

				if (index > 0) {
					Toast.makeText(getActivity(), getActivity().getString(R.string.updatethanhcong), Toast.LENGTH_SHORT).show();
					showData();
				} else {
					Toast.makeText(getActivity(), getActivity().getString(R.string.updatethatbai), Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), getActivity().getString(R.string.khongthelayduocduongdan), Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
		} else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
			String path = Conts.getPath(getActivity(), data.getData());
			if (path != null) {
				ContentValues contentValues = new ContentValues();
				contentValues.put(User.AVATAR, path);
				int index = getActivity().getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '1'", User.STATUS), null);

				if (index > 0) {
					Toast.makeText(getActivity(), getActivity().getString(R.string.updatethanhcong), Toast.LENGTH_SHORT).show();
					showData();
				} else {
					Toast.makeText(getActivity(), getActivity().getString(R.string.updatethatbai), Toast.LENGTH_SHORT).show();
				}
			} else {
				Toast.makeText(getActivity(), getActivity().getString(R.string.khongthelayduocduongdan), Toast.LENGTH_SHORT).show();
			}
		} else if (requestCode == 103 && resultCode == Activity.RESULT_OK) {
			// avatar
			Bitmap photo = (Bitmap) data.getExtras().get("data");
		}
	}

	@Override
	public void onClick(View v) {
		if (v.equals(infor_ngaysinh)) {
			new DateDialog(getActivity()) {
				@Override
				public void sendData(String date, String month, String year) {

				}
			}.show();
		} else if (v.equals(menu_left_img_avatar)) {
			Builder builder = new Builder(getActivity());
			builder.setItems(new String[] { "Camera", "Gallery" }, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 1) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.chonanh)), 102);
					} else {
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(cameraIntent, 103);
					}
				}
			});
			builder.show();
		} else if (v.equals(infor_cover_click_change)) {
			Builder builder = new Builder(getActivity());
			builder.setItems(new String[] { "Camera", "Gallery" }, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 1) {
						Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.chonanh)), 100);
					} else {
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						startActivityForResult(cameraIntent, 101);
					}
				}
			});
			builder.show();
		} else if (v.equals(activity_login_btn)) {
			String name = infor_name.getText().toString().trim();
			if (name.equals("")) {
				// Toat
				Toast.makeText(getActivity(), getActivity().getString(R.string.bancannhapthongtin), Toast.LENGTH_SHORT).show();
			} else {
				ContentValues contentValues = new ContentValues();
				contentValues.put(User.NAME, name);
				contentValues.put(User.BIDANH, infor_bidanh.getText().toString());
				contentValues.put(User.NGAYSINH, infor_ngaysinh.getText().toString());
				contentValues.put(User.DIACHI, infor_diachi.getText().toString());
				int index = getActivity().getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '1'", User.STATUS), null);

				if (index > 0) {
					Toast.makeText(getActivity(), getActivity().getString(R.string.updatethanhcong), Toast.LENGTH_SHORT).show();
					showData();
				} else {
					Toast.makeText(getActivity(), getActivity().getString(R.string.updatethatbai), Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}