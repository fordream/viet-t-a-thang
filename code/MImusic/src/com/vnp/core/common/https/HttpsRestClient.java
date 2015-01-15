package com.vnp.core.common.https;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import vnp.com.api.RestClient.RequestMethod;
import vnp.com.mimusic.util.LogUtils;

public class HttpsRestClient {
	public static final int BUFFER = 1024 * 2;
	private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
	private ArrayList<NameValuePair> headers = new ArrayList<NameValuePair>();

	public HttpsRestClient(String url) {
		this.url = url;
	}

	private String url;

	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	private HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	private void trustAllHosts() {
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

	public void execute(RequestMethod method) {
		String methodStr = "GET";
		if (method == RequestMethod.GET) {
			methodStr = "GET";
			String s = getQuery(params);
			url = url + ("".equals(s) ? "" : ("?" + getQuery(params)));
		} else if (method == RequestMethod.POST) {
			methodStr = "POST";
		}

		try {
			URL mxurl = new URL(url);
			trustAllHosts();
			HttpsURLConnection https = (HttpsURLConnection) mxurl.openConnection();
			https.setRequestMethod(methodStr);

			/**
			 * add header
			 */
			for (NameValuePair h : headers) {
				https.setRequestProperty(h.getName(), h.getValue());
			}
			// myURLConnection.setRequestProperty("Content-Type",
			// "application/x-www-form-urlencoded");
			// myURLConnection.setRequestProperty("Content-Length", "" +
			// Integer.toString(postData.getBytes().length));
			// myURLConnection.setRequestProperty("Content-Language", "en-US");

			// https.setUseCaches(false);
			https.setDoInput(true);
			https.setDoOutput(true);
			https.setHostnameVerifier(DO_NOT_VERIFY);

			if (method == RequestMethod.POST) {
				OutputStream os = https.getOutputStream();
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
				writer.write(getQuery(params));
				writer.flush();
				writer.close();
				os.close();
			}

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
			response = builder.toString();
			responseCode = https.getResponseCode();
			message = https.getResponseMessage();
			https.disconnect();
		} catch (Exception exception) {
			LogUtils.e("resx", exception);
		}
	}

	private String getQuery(List<NameValuePair> params) {
		try {
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
		} catch (Exception exception) {
			return "";
		}
	}
}