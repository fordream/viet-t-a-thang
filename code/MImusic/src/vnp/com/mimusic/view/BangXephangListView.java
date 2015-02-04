package vnp.com.mimusic.view;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.BangXepHang;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.adapter.BangXepHangCursorAdaper;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

//vnp.com.mimusic.view.BangXephangListView
public class BangXephangListView extends LinearLayout {
	// private vnp.com.mimusic.view.ChitietCaNhanBangXepHangTungDichVuView
	// chitiet;
	private vnp.com.mimusic.view.MusicListView list;
	private String type = BangXepHang.typeDOANHTHU;
	private String noDataText;
	private TextView message;
	private BangXepHangCursorAdaper adaper;

	public void setType(String type) {
		this.type = type;
		adaper = new BangXepHangCursorAdaper(getContext(), BangXepHang.getBangXepHang(getContext(), type));
		adaper.setType(type);
		list.setAdapter(adaper);
		message.setText("");

		if (BangXepHang.typeSOLUONG.equals(type)) {
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

	private void init() {
		((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.bangxephanglist, this);
		list = (MusicListView) findViewById(R.id.list);
		message = Conts.getView(this, R.id.message);

	}

	public void execute() {
		// adaper.setType(type);
		// adaper.changeData(new JSONArray());
		// adaper.notifyDataSetChanged();

		Bundle bxhParamBundle = new Bundle();
		bxhParamBundle.putString("type", type);
		((VApplication) getContext().getApplicationContext()).executeHttps(RequestMethod.GET, API.API_R024, bxhParamBundle, new IContsCallBack() {
			ProgressDialog progressDialog;

			@Override
			public void onSuscess(JSONObject response) {
				// try {
				// JSONArray jsonArray = response.getJSONArray("data");
				// adaper.changeData(jsonArray);
				// adaper.notifyDataSetChanged();
				//
				// if (jsonArray.length() == 0) {
				// if (list.getCount() <= 0) {
				// message.setText(noDataText);
				// } else {
				// message.setText("");
				// }
				//
				// } else {
				// message.setText("");
				// }
				// } catch (Exception exception) {
				//
				// }
				adaper.getFilter().filter("");
				progressDialog.dismiss();
			}

			@Override
			public void onStart() {
				progressDialog = ProgressDialog.show(getContext(), null, getResources().getString(R.string.loading));
			}

			@Override
			public void onError(String xmessage) {
				progressDialog.dismiss();

				if (list.getCount() <= 0) {
					message.setText(noDataText);
				} else {
					message.setText("");
				}
			}

			@Override
			public void onError() {
				onError("");
			}
		});
	}

	public void setOnItemClick(android.widget.AdapterView.OnItemClickListener listener) {
		list.setOnItemClickListener(listener);

	}

	public String getType() {
		return type;
	}

}