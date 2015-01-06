package vnp.com.api;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class MImusicService extends Service {

	private MImusicBin mImusicBin;
	public static final String ACTION = "vnp.com.api.MImusicService";
	public static final String KEY = "KEY";
	public static final String VALUE = "VALUE";
	public static final String METHOD = "METHOD";

	public MImusicService() {
		super();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		mImusicBin = new MImusicBin(this);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return mImusicBin;
	}

	private List<String> listCallBack = new ArrayList<String>();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		if ("add".equals(intent.getStringExtra(KEY))) {
			if (!listCallBack.contains(intent.getStringExtra(VALUE))) {
				listCallBack.add(intent.getStringExtra(VALUE));
			}
		} else if ("remove".equals(intent.getStringExtra(KEY))) {
			if (listCallBack.contains(intent.getStringExtra(VALUE))) {
				listCallBack.remove(intent.getStringExtra(VALUE));
			}
		}
		callApi(intent);
		return super.onStartCommand(intent, flags, startId);
	}

	private void callApi(Intent intent) {
		if (intent != null) {
			Conts.execute((RequestMethod) intent.getSerializableExtra(METHOD), intent.getStringExtra(KEY), this, new Bundle(), new IContsCallBack() {
				@Override
				public void onSuscess(JSONObject response) {

				}

				@Override
				public void onError(String message) {

				}

				@Override
				public void onError() {

				}
			});
		}
	}

}