package com.vnp.core.common.https;

import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class RunSSL {
	private ClientConnectionManager clientConnectionManager;
	private HttpContext context;
	private HttpParams params;

	public RunSSL() {
		setup();
	}

	// prepare for the https connection
	// call this in the constructor of the class that does the connection if
	// it's used multiple times
	private void setup() {
		SchemeRegistry schemeRegistry = new SchemeRegistry();

		// http scheme
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		// https scheme
		schemeRegistry.register(new Scheme("https", new EasySSLSocketFactory(), 443));

		params = new BasicHttpParams();
		params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 1);
		params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(1));
		params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, "utf-8");

		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		// set the user credentials for our site "example.com"
		//credentialsProvider.setCredentials(new AuthScope("example.com", AuthScope.ANY_PORT), new UsernamePasswordCredentials("UserNameHere", "UserPasswordHere"));
		
		credentialsProvider.setCredentials(new AuthScope("https://125.235.40.85", AuthScope.ANY_PORT), new UsernamePasswordCredentials("UserNameHere", "UserPasswordHere"));
		
		clientConnectionManager = new ThreadSafeClientConnManager(params, schemeRegistry);

		context = new BasicHttpContext();
		context.setAttribute("http.auth.credentials-provider", credentialsProvider);
	}

//	public HttpResponse getResponseFromUrl(String url) {
//		DefaultHttpClient client = new DefaultHttpClient(clientConnectionManager, params);
//		HttpGet get = new HttpGet(url);
//		HttpResponse response = null;
//		try {
//			response = client.execute(get, context);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return response;
//	}

	public DefaultHttpClient getDefaultHttpClient(int timeout) {
		HttpConnectionParams.setConnectionTimeout(params, timeout);
		HttpConnectionParams.setSoTimeout(params, timeout);

		return new DefaultHttpClient(clientConnectionManager, params);
	}
}
