package vnp.com.mimusic.view;

import org.json.JSONArray;
import org.json.JSONObject;

import com.aretha.slidemenudemo.fragment.BangXepHangFragment;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.activity.RootMenuActivity;
import vnp.com.mimusic.adapter.BangXepHangAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//vnp.com.mimusic.view.BangXephangListView
public class BangXephangListView extends LinearLayout {
//	private vnp.com.mimusic.view.ChitietCaNhanBangXepHangTungDichVuView chitiet;
	private vnp.com.mimusic.view.MusicListView list;
	private String type = "1";
	private String noDataText;
	private TextView message;

	public void setType(String type) {
		this.type = type;

//		chitiet.setType(type);
		if ("2".equals(type)) {
			noDataText = getResources().getString(R.string.bangxephang_so_luong_no_data);
		} else {
			noDataText = getResources().getString(R.string.bangxephang_doanh_thu_no_data);
		}
	}

	public BangXephangListView(Context context) {
		super(context);
		init();
	}

	public BangXephangListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	@Override
	public void setVisibility(int visibility) {
		super.setVisibility(visibility);

		if (visibility == GONE) {
//			chitiet.gone();
		}
	}

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bangxephanglist, this);

		//chitiet = Conts.getView(this, R.id.chitiet);

		list = (MusicListView) findViewById(R.id.list);
		message = Conts.getView(this, R.id.message);
	}

	public void execute() {
		Bundle bxhParamBundle = new Bundle();
		bxhParamBundle.putString("type", type);
		((VApplication) getContext().getApplicationContext()).executeHttps(RequestMethod.GET, API.API_R024, bxhParamBundle, new IContsCallBack() {
			ProgressDialog progressDialog;

			@Override
			public void onSuscess(JSONObject response) {
				try {
					JSONArray jsonArray = response.getJSONArray("data");
					BangXepHangAdaper adaper = new BangXepHangAdaper(getContext(), jsonArray);
					adaper.setType(type);
					list.setAdapter(adaper);
					if (jsonArray.length() == 0) {
						// list.setTextNoData(true, noDataText);
						if (list.getCount() <= 0) {
							message.setText(noDataText);
						} else {
							message.setText("");
						}

					} else {
						message.setText("");
					}
				} catch (Exception exception) {

				}

				progressDialog.dismiss();
			}

			@Override
			public void onStart() {
				progressDialog = ProgressDialog.show(getContext(), null, getResources().getString(R.string.loading));
			}

			@Override
			public void onError(String xmessage) {
				progressDialog.dismiss();
				// list.setTextNoData(true, noDataText);

				if (list.getCount() <= 0) {
					message.setText(noDataText);
				} else {
					message.setText("");
				}
			}

			@Override
			public void onError() {
				onError("check network");
			}
		});
	}

	public void setOnItemClick(android.widget.AdapterView.OnItemClickListener listener) {
		list.setOnItemClickListener(listener);
//		list.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3) {
//				JSONObject jo = (JSONObject) parent.getItemAtPosition(position);
//				Bundle intent = new Bundle();
//				intent.putString("type", Conts.CHITIETCANHANBANGXEPHANGTUNGDICHVU);
//				intent.putString("xtype",type);
//				intent.putString("position", Conts.getString(jo, "position"));
//				intent.putString("mtype", Conts.getString(jo, "type"));
//				intent.putString("avatar", Conts.getString(jo, "avatar"));
//				intent.putString("ranking_id", Conts.getString(jo, "id"));
//				intent.putString("nickname", Conts.getString(jo, "nickname"));
//				intent.putString("quantity", Conts.getString(jo, "quantity"));
//				intent.putString("commission", Conts.getString(jo, "commission"));
//
//				chitiet.setVisibility(VISIBLE);
//				chitiet.initData(intent);
//			}
//		});
	}

//	public boolean onBackPressed() {
//		if (chitiet.getVisibility() == VISIBLE) {
//			chitiet.gone();
//			return true;
//		}
//		return false;
//	}

}