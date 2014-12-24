package vnp.com.mimusic;

import vnp.com.db.DichVu;
import vnp.com.db.User;
import vnp.com.mimusic.main.BaseMusicSlideMenuActivity;
import vnp.com.mimusic.view.HeaderView;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivty extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		((VApplication) getApplication()).dongbodanhba();
		String selection = User.STATUS + "='1'";
		Cursor cursor = getContentResolver().query(User.CONTENT_URI, null, selection, null, null);
		if (cursor != null && cursor.getCount() >= 1) {
			cursor.close();
			gotoHome();
		} else {
			overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_slide_left_out);

			setContentView(R.layout.activity_login);
			findViewById(R.id.activity_login_btn).setOnClickListener(this);
			HeaderView header = (HeaderView) findViewById(R.id.activity_login_header);
			header.setTextHeader(R.string.dangnhap);
			header.showButton(false, false);
		}
	}

	private void gotoHome() {
		startActivity(new Intent(this, BaseMusicSlideMenuActivity.class));
		finish();
		overridePendingTransition(R.anim.abc_slide_right_in, R.anim.abc_slide_left_out);
	}

	@Override
	public void onClick(View v) {
		String numberPhone = ((TextView) findViewById(R.id.activity_login_number_phone)).getText().toString();
		String password = ((TextView) findViewById(R.id.activity_login_password)).getText().toString();

		if (!numberPhone.trim().equals("") && !password.trim().equals("")) {
			ContentValues values = new ContentValues();
			values.put(User.USER, numberPhone);
			values.put(User.PASSWORD, password);
			values.put(User.STATUS, "1");

			Uri uri = getContentResolver().insert(User.CONTENT_URI, values);
			int _id = Integer.parseInt(uri.getPathSegments().get(1));

			if (_id > 0) {
				addDichVu();
				gotoHome();
			} else {
				Toast.makeText(this, "login fail", Toast.LENGTH_SHORT).show();
			}

		} else {
			Toast.makeText(this, "input password and number phone", Toast.LENGTH_SHORT).show();
		}
	}

	private void addDichVu() {
		String icon = "http://ecx.images-amazon.com/images/I/719CZqP48hL._SY300_.png";
		String linkFullContent = "http://searchenginewatch.com/sew/how-to/2340758/what-type-of-content-should-you-create-long-or-short";
		String name = "IMusic";
		String shortContent = "Website nhạc trực tuyến của tập đoàn viễn thông quân đội,Website nhạc trực tuyến của tập đoàn viễn thông quân đội,Website nhạc trực tuyến của tập đoàn viễn thông quân đội,Website nhạc trực tuyến của tập đoàn viễn thông quân đội ";
		String url = "http://imusic.vn";

		addDichVu("1", icon, linkFullContent, "Dịch vụ IMusic", shortContent, url);
		addDichVu("2", icon, linkFullContent, "Dịch vụ mobileTV", shortContent, url);
		addDichVu("3", icon, linkFullContent, "Dịch vụ OMEGA Book", shortContent, url);
		addDichVu("4", icon, linkFullContent, "Dịch vụ ORA", shortContent, url);
		addDichVu("5", icon, linkFullContent, "Dịch vụ chăm sóc trẻ", shortContent, url);
		addDichVu("6", icon, linkFullContent, "Dịch Vụ Cho người già", shortContent, url);
		addDichVu("7", icon, linkFullContent, "Dịch vụ trẻ", shortContent, url);
		addDichVu("8", icon, linkFullContent, "Dịch vụ vì nhân dân", shortContent, url);
		addDichVu("9", icon, linkFullContent, "Dịch vụ nghe đài", shortContent, url);
		addDichVu("10", icon, linkFullContent, "Dịch vụ tìm thông tin", shortContent, url);
		addDichVu("11", icon, linkFullContent, "Dịch vụ thám hiểm", shortContent, url);
	}

	private void addDichVu(String isSystem, String icon, String linkFullContent, String name, String shortContent, String url) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DichVu.ICON, icon);
		contentValues.put(DichVu.ID, isSystem);
		contentValues.put(DichVu.LINK_FULL_CONTENT, linkFullContent);
		contentValues.put(DichVu.NAME, name);
		contentValues.put(DichVu.SHORTCONTENT, shortContent);
		contentValues.put(DichVu.URL, url);
		String selection = String.format("%s='%s'", DichVu.ID, isSystem);
		Cursor cursor = getContentResolver().query(DichVu.CONTENT_URI, null, selection, null, null);

		if (cursor != null && cursor.getCount() >= 1) {
			cursor.close();
			getContentResolver().update(DichVu.CONTENT_URI, contentValues, selection, null);
		} else {
			getContentResolver().insert(DichVu.CONTENT_URI, contentValues);
		}
	}
}