package com.vnp.core.common.https;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import android.util.Log;

public class RunSSL1 {
	private void example() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		// http scheme
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		// https scheme
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

		HttpParams params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
	}

	public void execute() {
		HttpURLConnection connection = null;
		try {
			URL url = new URL("https://notwithoutmycode.com");
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.setRequestProperty("Accept", "*/*");
			connection.connect();
			int responseCode = connection.getResponseCode();
			System.out.println("HTTP responseCode: " + responseCode);
			InputStreamReader in = new InputStreamReader(connection.getInputStream(), "UTF-8");
			int bytesRead;
			char[] buffer = new char[512];
			StringBuilder output = new StringBuilder();
			while ((bytesRead = in.read(buffer)) != -1) {
				output.append(buffer, 0, bytesRead);
			}
			in.close();
			System.out.println("Response: " + output.toString().trim());
		} catch (Exception e) {
			Log.e("SSL", e.getMessage(), e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}
}