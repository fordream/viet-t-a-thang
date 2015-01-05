package vnp.com.api;

import org.json.JSONObject;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class MImusicService extends Service {
	public static final String ACTION = "vnp.com.api.MImusicService";
	public static final String KEY = "KEY";
	public static final String METHOD = "METHOD";

	public MImusicService() {
		super();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
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