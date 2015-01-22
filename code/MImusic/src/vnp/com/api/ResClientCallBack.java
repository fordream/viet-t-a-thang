package vnp.com.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import android.content.Context;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;

public abstract class ResClientCallBack extends CallBack {
	private Context context;

	public ResClientCallBack(Context context) {
		this.context = context;
	}

	private Map<String, String> maps = new HashMap<String, String>();

	public final void addParam(String name, String value) {
		maps.put(name, value);
	}

	public RestClient.RequestMethod getMedthod() {
		return RestClient.RequestMethod.POST;
	}

	public abstract String getApiName();

	@Override
	public Object execute() {
		System.setProperty("http.keepAlive", "false");
		RestClient client = new RestClient(Conts.SERVER + getApiName());
		try {
			Set<String> set = maps.keySet();
			for (String key : set) {
				client.addParam(key, maps.get(key));
			}

			client.execute(getMedthod());
		} catch (Exception e) {
			LogUtils.e("response", e);
		}

		return client;
	}

	@Override
	public void onCallBack(Object object) {
		RestClient client = (RestClient) object;
		if (client.getResponseCode() == 200) {
			onSuccess(client.getResponse());
		} else {
			onError(client.getErrorMessage());

		}
	}

	public void onError(String errorMessage) {

	}

	public void onSuccess(String response) {

	}
}