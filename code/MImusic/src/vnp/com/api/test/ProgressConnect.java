package vnp.com.api.test;

import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.API;
import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.R;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import vnp.com.mimusic.util.Conts.IContsCallBack;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

public class ProgressConnect {
	private Context context;

	public ProgressConnect(Context context) {
		this.context = context;
	}

	public void execute(final String api, final RequestMethod method, final Bundle arguments, final IContsCallBack contsCallBack) {
		new AsyncTask<String, String, String>() {
			protected String doInBackground(String[] params) {
				RestClient mRestClient = new RestClient(Conts.SERVERS + api);
				try {
					if (arguments != null) {
						Set<String> keys = arguments.keySet();
						for (String key : keys) {
							LogUtils.e("keys", key + " : " + arguments.getString(key));
							mRestClient.addParam(key, arguments.getString(key));
						}
					}

					if (!Conts.isBlank(Conts.getHttpsToken(context))) {
						mRestClient.addParam("token", Conts.getHttpsToken(context));
					}

					if (API.API_R023.equals(api)) {
						mRestClient.executeUploadFile(context, true);
					} else{
						mRestClient.execute(method);
					}
				} catch (Exception exception) {
					LogUtils.e("aaaaaaaaaaa", exception);
				}
				return mRestClient.getResponse();
			}

			protected void onPostExecute(String response) {
				String message = "";
				String errorCode = "";
				try {
					if (Conts.isBlank(response)) {
						response = context.getString(R.string.default_error);
					}

					JSONObject jsonObject = new JSONObject(response);
					errorCode = jsonObject.getString("errorCode");
					message = jsonObject.getString("message");
					if ("0".equals(errorCode) || "0000".equals(errorCode)) {
						if (contsCallBack != null)
							contsCallBack.onSuscess(jsonObject);
					} else {
						try {
							JSONObject errorMessage = jsonObject.getJSONObject("errorMessage");
							java.util.Iterator<String> iterator = errorMessage.keys();
							while (iterator.hasNext()) {
								String key = iterator.next();
								message += "\n" + errorMessage.getString(key);
							}
						} catch (Exception exception) {
						}

						try {
							JSONArray errorMessage = jsonObject.getJSONArray("customers_fail");
							String mNewData = "";
							for (int i = 0; i < errorMessage.length(); i++) {
								String newData = errorMessage.get(i).toString();
								mNewData += "" + newData;
							}

							if (!Conts.isBlank(mNewData)) {
								mNewData = mNewData.replace("{", "").replace("}", "").replace("\"\"", " , ").replace("\"", "");
								message += "\n" + String.format(context.getString(R.string.danhsachsodienthoaikhongthemoi), mNewData);
							}
						} catch (Exception exception) {

						}
						if (contsCallBack != null)
							contsCallBack.onError(message);
					}
				} catch (Exception exception) {
					if (contsCallBack != null)
						contsCallBack.onError();
				}
			};
		}.execute("");

	}
}
