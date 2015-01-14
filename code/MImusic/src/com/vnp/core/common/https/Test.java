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

import android.util.Log;

public class Test {
	String MY_APP_TAG = "net.ajzele.beanstalk.stalkmanager";
	String username = "brankoa";
	String host = "brankoa.beanstalkapp.com";
	String password = "MyPassHere";

	String urlBasePath = "http://" + username + ".beanstalkapp.com/api/";
	String urlApiCall_FindAllRepositories = urlBasePath + "repositories.xml";

	public void connect() {
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
					Log.d(MY_APP_TAG, "OK: " + content.toString());
				} catch (Exception e) {
					e.printStackTrace();
					Log.d(MY_APP_TAG, "Error: " + e.getMessage());
				}
			}
		}).start();

	}
}