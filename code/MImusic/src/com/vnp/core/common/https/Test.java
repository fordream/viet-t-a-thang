package com.vnp.core.common.https;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import vnp.com.mimusic.util.LogUtils;
import android.net.Credentials;
import android.util.Log;

public class Test {
	String MY_APP_TAG = "net.ajzele.beanstalk.stalkmanager";
	String username = "brankoa1";
	String host = "brankoa.beanstalkapp.com";
	String password = "MyPassHere";

	String urlBasePath = "http://" + username + ".beanstalkapp.com/api/";
	String urlApiCall_FindAllRepositories = urlBasePath + "repositories.xml";

	public void connect() {
		// host = "125.235.40.85";
		// urlBasePath = "https://125.235.40.85/api.php/";
		// urlApiCall_FindAllRepositories = urlBasePath;
		// username = "vdealer";
		// password = "";
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					HttpClient client = new DefaultHttpClient();
					AuthScope as = new AuthScope(host, 443);
					UsernamePasswordCredentials upc = new UsernamePasswordCredentials(username, password);
					((AbstractHttpClient) client).getCredentialsProvider().setCredentials(as, upc);
					BasicHttpContext localContext = new BasicHttpContext();
					BasicScheme basicAuth = new BasicScheme();
					localContext.setAttribute("preemptive-auth", basicAuth);
					HttpHost targetHost = new HttpHost(host, 443, "https");

					HttpGet httpget = new HttpGet(urlApiCall_FindAllRepositories);
					httpget.setHeader("Content-Type", "application/xml");
					HttpResponse response = client.execute(targetHost, httpget, localContext);
					HttpEntity entity = response.getEntity();
					Object content = EntityUtils.toString(entity);
					LogUtils.e(MY_APP_TAG, "OK: " + content.toString());
				} catch (Exception e) {
					e.printStackTrace();
					LogUtils.e(MY_APP_TAG, e);
					LogUtils.e(MY_APP_TAG, "Error: " + e.getMessage());
				}
			}
		}).start();

	}
}