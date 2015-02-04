package vnp.com.mimusic.view;

import java.util.Random;

import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.db.BangXepHang;
import vnp.com.mimusic.R;
import vnp.com.mimusic.VApplication;
import vnp.com.mimusic.adapter.BangXepHangCursorAdaper;
import vnp.com.mimusic.base.diablog.VasProgessDialog;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

//vnp.com.mimusic.view.BangXephangListView
public class BangXephangListView extends LinearLayout {
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

		adaper.setText(message, noDataText);
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
		message.setText("");
		Bundle bxhParamBundle = new Bundle();
		bxhParamBundle.putString("type", type);
		((VApplication) getContext().getApplicationContext()).executeHttps(RequestMethod.GET, API.API_R024, bxhParamBundle, new IContsCallBack() {
			ProgressDialog progressDialog;

			@Override
			public void onSuscess(JSONObject response) {
				adaper.getFilter().filter("" + new Random().nextDouble());
				if (progressDialog != null)
					progressDialog.dismiss();
				message.setText("");
				if (adaper.getCount() == 0) {
					// message.setText(noDataText);
				} else {
					message.setText("");
				}
			}

			@Override
			public void onStart() {
				if (adaper.getCount() == 0) {
					progressDialog = new VasProgessDialog(getContext());
					progressDialog.show();
				}
			}

			@Override
			public void onError(String xmessage) {
				if (progressDialog != null)
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

	public void addHeader(HeaderView headerbangxepdichvu) {
		list.addHeaderView(headerbangxepdichvu);
		this.headerbangxepdichvu = headerbangxepdichvu;
	}

	public ListView list() {
		return list;
	}

	HeaderView headerbangxepdichvu;

	public void updateHeader(boolean b) {
		if (headerbangxepdichvu != null) {
			headerbangxepdichvu.showHeader(b);
		}
	}

}