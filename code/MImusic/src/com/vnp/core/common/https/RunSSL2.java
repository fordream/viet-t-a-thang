package com.vnp.core.common.https;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;

public class RunSSL2 {

	public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	public static void trustAllHosts() {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		} };

		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void xTest(final String mUrl, final List<NameValuePair> params) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(mUrl);
					trustAllHosts();
					HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
					https.setRequestMethod("POST");
					https.setDoInput(true);
					https.setDoOutput(true);
					https.setHostnameVerifier(DO_NOT_VERIFY);

					OutputStream os = https.getOutputStream();
					BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
					writer.write(getQuery(params));
					writer.flush();
					writer.close();
					os.close();
					https.connect();
					InputStream stream = https.getInputStream();
					InputStreamReader isReader = new InputStreamReader(stream);

					BufferedReader br = new BufferedReader(isReader);
					String result;
					String line;
					StringBuilder builder = new StringBuilder();
					while ((line = br.readLine()) != null) {
						if (line != null) {
							builder.append(line);
						}
					}

					br.close();
					// LogUtils.e("resx", https.getResponseCode() + "");
//					LogUtils.e("resx", builder.toString() + "");
					https.disconnect();
				} catch (Exception e) {
//					LogUtils.e("resx", e);
				}

			}
		}).start();

	}

	private static String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}
}