package com.vnp.core.crash;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import vnp.com.api.CallBack;
import vnp.com.api.ExeCallBack;
import vnp.com.api.RestClient;
import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.util.Conts;
import vnp.com.mimusic.util.LogUtils;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class CrashExceptionHandler implements Thread.UncaughtExceptionHandler {
	public static final void onCreate(Activity activity) {
		Thread.setDefaultUncaughtExceptionHandler(new CrashExceptionHandler(activity));
	}

	public static final void sendCrash(final Context context) {
		if (!Conts.havenewWork(context)) {
			return;
		}

		CallBack callBack = new CallBack() {

			@Override
			public void onCallBack(Object object) {
				RestClient client = (RestClient) object;

				try {
					LogUtils.e("AAAAA", client.getResponse());
					JSONObject jsonObject = new JSONObject(client.getResponse());
					if ("1".equals(jsonObject.getString("status"))) {
						context.deleteFile("stack.trace");
					}
				} catch (Exception e) {
				}
			}

			@Override
			public Object execute() {
				RestClient client = new RestClient("http://vnpmanager.esy.es/api/crash.php");
				try {

					StringBuilder builder = new StringBuilder();
					try {
						BufferedReader reader = new BufferedReader(new InputStreamReader(context.openFileInput("stack.trace")));
						String trace = null;
						String line = null;
						while ((line = reader.readLine()) != null) {
							if (line != null) {
								builder.append(line).append("\n");
							}
						}
					} catch (FileNotFoundException fnfe) {
					} catch (IOException ioe) {
					}

					client.addParam("appname", context.getPackageName());

					Calendar calendar = Calendar.getInstance();
					client.addParam("time", String.format("%s-%s-%s %s:%s", calendar.get(Calendar.YEAR) + "", (calendar.get(Calendar.MONTH) + 1), calendar.get(Calendar.DATE),
							calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));

					client.addParam("log", builder.toString());

					if (builder.length() > 0)
						client.execute(RequestMethod.POST);

					LogUtils.e("Crash", client.getResponse() + builder.length());
				} catch (Exception x) {
					LogUtils.e("Crash", x);
				}
				return client;
			}
		};

		new ExeCallBack().executeAsynCallBack(callBack);
		// context.deleteFile("stack.trace");
	}

	private Thread.UncaughtExceptionHandler defaultUEH;

	private Activity app = null;

	public CrashExceptionHandler(Activity app) {
		this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
		this.app = app;
	}

	public void uncaughtException(Thread t, Throwable e) {
		StackTraceElement[] arr = e.getStackTrace();
		String report = e.toString() + "\n\n";
		report += "--------- Stack trace ---------\n\n";
		for (int i = 0; i < arr.length; i++) {
			report += "    " + arr[i].toString() + "\n";
		}
		report += "-------------------------------\n\n";

		// If the exception was thrown in a background thread inside
		// AsyncTask, then the actual exception can be found with getCause
		report += "--------- Cause ---------\n\n";
		Throwable cause = e.getCause();
		if (cause != null) {
			report += cause.toString() + "\n\n";
			arr = cause.getStackTrace();
			for (int i = 0; i < arr.length; i++) {
				report += "    " + arr[i].toString() + "\n";
			}
		}
		report += "-------------------------------\n\n";

		try {
			FileOutputStream trace = app.openFileOutput("stack.trace", Context.MODE_PRIVATE);
			trace.write(report.getBytes());
			trace.close();
		} catch (IOException ioe) {
		}

		defaultUEH.uncaughtException(t, e);
	}
}