package com.aretha.slidemenudemo.fragment;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.base.diablog.DateDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.view.HeaderView;
import vnp.com.mimusic.view.LoadingView;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

public class InforFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	private ImageView menu_left_img_cover, menu_left_img_avatar, infor_cover_click_change;
	private TextView menu_left_tv_name;
	private Button activity_login_btn;
	private EditText infor_name, infor_bidanh, infor_diachi;
	private TextView infor_ngaysinh;

	private LoadingView loadingView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.infor, null);

		loadingView = (LoadingView) view.findViewById(R.id.loadingView1);
		Conts.showView(loadingView, false);
		HeaderView header = (HeaderView) view.findViewById(R.id.infor_header);
		header.setTextHeader(R.string.thuongtinnguoidung);
		header.showButton(true, false);
		header.setButtonLeftImage(true, R.drawable.btn_back);
		header.findViewById(R.id.header_btn_left).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getActivity().onBackPressed();
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
			infor_bidanh.setText(cursor.getString(cursor.getColumnIndex(User.nickname)));
			infor_diachi.setText(cursor.getString(cursor.getColumnIndex(User.address)));
			infor_ngaysinh.setText(cursor.getString(cursor.getColumnIndex(User.birthday)));

			String cover = cursor.getString(cursor.getColumnIndex(User.COVER));
			Conts.showImage(cover, menu_left_img_cover, 0);

			String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));
			Conts.showImage(avatar, menu_left_img_avatar, R.drawable.no_avatar);

			cursor.close();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
			String path = data.getData().toString();
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
			String path = this.path.toString();
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

		} else if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
			String path = data.getData().toString();
			uploadAvatar(path);
		} else if (requestCode == 103 && resultCode == Activity.RESULT_OK) {
			String path = this.path.toString();
			uploadAvatar(path);
		}
	}

	private void uploadAvatar(String path) {
		if (!Conts.isBlank(path)) {
			// ContentValues contentValues = new ContentValues();
			// contentValues.put(User.AVATAR, path);

			getmImusicService().executeUpdateAvatar(path, new IContsCallBack() {
				ProgressDialog progressDialog;

				@Override
				public void onStart() {
					if (progressDialog == null)
						progressDialog = ProgressDialog.show(getActivity(), null, getActivity().getString(R.string.loading));
				}

				@Override
				public void onError() {
					onError("onError");
				}

				@Override
				public void onError(String message) {
					Conts.showDialogThongbao(getActivity(), message);
					if (progressDialog != null) {
						progressDialog.dismiss();
					}
				}

				@Override
				public void onSuscess(JSONObject response) {
					Conts.showDialogThongbao(getActivity(), response.toString());
					if (progressDialog != null) {
						progressDialog.dismiss();
					}
				}
			});
		} else {
			Conts.showDialogThongbao(getActivity(), getActivity().getString(R.string.khongthelayduocduongdan));
		}
	}

	private Uri path;

	@Override
	public void onClick(View v) {
		if (v.equals(infor_ngaysinh)) {
			new DateDialog(getActivity(), infor_ngaysinh.getText().toString()) {
				@Override
				public void sendData(String date, String month, String year) {
					infor_ngaysinh.setText(String.format("%s/%s/%s", date, month, year));
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

						path = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_avatar_" + System.currentTimeMillis() + ".jpg"));
						cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, path);
						cameraIntent.putExtra("return-data", true);
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
						Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
						startActivityForResult(i, 100);
					} else {
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						path = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "tmp_avatar_" + System.currentTimeMillis() + ".jpg"));
						cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, path);
						cameraIntent.putExtra("return-data", true);
						startActivityForResult(cameraIntent, 101);
					}
				}
			});
			builder.show();
		} else if (v.equals(activity_login_btn)) {
			Bundle bundle = new Bundle();
			bundle.putString("birthday", infor_ngaysinh.getText().toString());
			bundle.putString("address", infor_diachi.getText().toString());
			bundle.putString("nickname", infor_bidanh.getText().toString());
			bundle.putString("fullname", infor_name.getText().toString());
			// fullname

			getmImusicService().execute(RequestMethod.POST, API.API_R007, bundle, new IContsCallBack() {

				@Override
				public void onSuscess(JSONObject response) {
					try {
						Conts.showDialogThongbao(getActivity(), response.getString("message"));
					} catch (JSONException e) {
					}
					Conts.showView(loadingView, false);
					showData();
				}

				@Override
				public void onStart() {
					Conts.showView(loadingView, true);
				}

				@Override
				public void onError(String message) {
					Conts.toast(getActivity(), message);
					Conts.showView(loadingView, false);

					Conts.showDialogThongbao(getActivity(), message);
				}

				@Override
				public void onError() {
					onError("");
				}
			});
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}