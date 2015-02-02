package com.aretha.slidemenudemo.fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.User;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import vnp.com.mimusic.util.Conts.IShowDateDialog;
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
import eu.janmuller.android.simplecropimage.CropImage;
import eu.janmuller.android.simplecropimage.example.InternalStorageContentProvider;

public class InforFragment extends BaseFragment implements OnItemClickListener, View.OnClickListener {
	protected static final int REQUEST_CODE_TAKE_PICTURE = 103;
	private static final int REQUEST_CODE_CROP_IMAGE = 10003;
	protected static final int REQUEST_CODE_GALLERY = 102;
	private File mFileTemp;
	private ImageView menu_left_img_cover, menu_left_img_avatar, infor_cover_click_change;
	private TextView menu_left_tv_name;
	private TextView activity_login_btn;
	private EditText infor_name, infor_bidanh, infor_diachi;
	private TextView infor_ngaysinh;

	private LoadingView loadingView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.infor, null);
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			mFileTemp = new File(Environment.getExternalStorageDirectory(), InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
		} else {
			mFileTemp = new File(getActivity().getFilesDir(), InternalStorageContentProvider.TEMP_PHOTO_FILE_NAME);
		}

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
		activity_login_btn = (TextView) view.findViewById(R.id.activity_login_btn);
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
			menu_left_tv_name.setText(String.format("%s (%s)", Conts.getName(cursor), Conts.getSDT(cursor.getString(cursor.getColumnIndex(User.USER)))));
			infor_name.setText(Conts.getName(cursor));
			infor_bidanh.setText(cursor.getString(cursor.getColumnIndex(User.nickname)));
			infor_diachi.setText(cursor.getString(cursor.getColumnIndex(User.address)));
			infor_ngaysinh.setText(cursor.getString(cursor.getColumnIndex(User.birthday)));

			String cover = cursor.getString(cursor.getColumnIndex(User.COVER));
			Conts.showImage(cover, menu_left_img_cover, 0);

			String avatar = cursor.getString(cursor.getColumnIndex(User.AVATAR));

			Conts.showImage(avatar, menu_left_img_avatar, R.drawable.new_no_avatar);
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

		} else if (requestCode == REQUEST_CODE_GALLERY && resultCode == Activity.RESULT_OK) {
			// if (path == null) {
			// Conts.showDialogThongbao(getActivity(),
			// getActivity().getString(R.string.khonglayduocanh));
			// return;
			// }

			// beginCrop(data.getData());
			// String path = data.getData().toString();
			// uploadAvatar(path);

			try {
				InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
				FileOutputStream fileOutputStream = new FileOutputStream(mFileTemp);
				copyStream(inputStream, fileOutputStream);
				fileOutputStream.close();
				inputStream.close();
				startCropImage();
			} catch (Exception e) {

			}
		} else if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == Activity.RESULT_OK) {

			// if (path == null) {
			// Conts.showDialogThongbao(getActivity(),
			// getActivity().getString(R.string.khonglayduocanh));
			// return;
			// }
			// String path = this.path.toString();
			// uploadAvatar(path);

			startCropImage();
		} else if (REQUEST_CODE_CROP_IMAGE == requestCode && resultCode == Activity.RESULT_OK) {
			String path = data.getStringExtra(CropImage.IMAGE_PATH);
			if (path == null) {
				return;
			}

			uploadAvatar(path);
		}
	}

	public static void copyStream(InputStream input, OutputStream output) throws IOException {

		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	private void startCropImage() {

		Intent intent = new Intent(getActivity(), CropImage.class);
		intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
		intent.putExtra(CropImage.SCALE, true);

		intent.putExtra(CropImage.ASPECT_X, 2);
		intent.putExtra(CropImage.ASPECT_Y, 2);

		startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
	}

	private void uploadAvatar(final String path) {
		// Conts.showDialogThongbao(getActivity(), path + " " + new
		// File(path).exists());
		LogUtils.e("A", new File(path).exists() + "");
		if (!Conts.isBlank(path)) {
			executeUpdateHttpsAvatar(path, new IContsCallBack() {
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

					ContentValues contentValues = new ContentValues();
					contentValues.put(User.AVATAR, path);
					int index = getActivity().getContentResolver().update(User.CONTENT_URI, contentValues, String.format("%s = '1'", User.STATUS), null);

					showData();
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

			Conts.showDateDialog(getActivity(), R.string.chonngaysinh, infor_ngaysinh.getText().toString(), new IShowDateDialog() {
				@Override
				public void onSend(String year, String month, String date) {
					infor_ngaysinh.setText(String.format("%s/%s/%s", date, month, year));
				}
			});
		} else if (v.equals(menu_left_img_avatar)) {
			Builder builder = new Builder(getActivity());
			builder.setItems(new String[] { getString(R.string.chupanhmoi), getString(R.string.chonanhcosan) }, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if (which == 1) {
						// ACTION_GET_CONTENT
						Intent intent = new Intent(Intent.ACTION_PICK);
						intent.setType("image/*");
						startActivityForResult(Intent.createChooser(intent, getActivity().getString(R.string.chonanh)), REQUEST_CODE_GALLERY);

					} else {
						Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
						// path = Uri.fromFile(new
						// File(Environment.getExternalStorageDirectory(),
						// "tmp_avatar_" + System.currentTimeMillis() +
						// ".jpg"));
						// cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
						// path);
						// cameraIntent.putExtra("return-data", true);
						// startActivityForResult(cameraIntent, 103);

						Uri mImageCaptureUri = null;
						String state = Environment.getExternalStorageState();
						if (Environment.MEDIA_MOUNTED.equals(state)) {
							mImageCaptureUri = Uri.fromFile(mFileTemp);
						} else {
							mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
						}
						cameraIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
						cameraIntent.putExtra("return-data", true);
						startActivityForResult(cameraIntent, REQUEST_CODE_TAKE_PICTURE);
					}
				}
			});
			builder.show();
		} else if (v.equals(infor_cover_click_change)) {
			Builder builder = new Builder(getActivity());
			builder.setItems(new String[] { getString(R.string.chupanhmoi), getString(R.string.chonanhcosan) }, new DialogInterface.OnClickListener() {

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

			execute(RequestMethod.POST, API.API_R007, bundle, new IContsCallBack() {

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

					Conts.showView(loadingView, false);
					Conts.showDialogThongbao(getActivity(), message);
				}

				@Override
				public void onError() {
					onError("onError");
				}
			});
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	}
}