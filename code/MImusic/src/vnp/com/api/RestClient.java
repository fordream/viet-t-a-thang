package vnp.com.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import vnp.com.mimusic.util.Conts;
import android.content.Context;

import com.vnp.core.common.https.RunSSL;
import com.vnp.core.common.https.RunSSL2;

public class RestClient {
	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}

	public static final int TIME_OUT = 10 * 1000;
	public static final int BUFFER = 1024 * 2;
	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;

	private String url;

	private int responseCode;
	private String message;

	private String response;

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public void execute(RequestMethod method) throws Exception {
		HttpUriRequest request = null;
		switch (method) {
		case GET: {
			// add parameters
			String combinedParams = "";
			try {
				if (!params.isEmpty()) {
					combinedParams += "?";
					for (NameValuePair p : params) {

						String name = p.getName();
						String value = p.getValue();

						if (Conts.isBlank(name)) {
							name = "";
						}

						if (Conts.isBlank(value)) {
							value = "";
						}
						String paramString = name + "=" + URLEncoder.encode(value, "UTF-8");
						if (combinedParams.length() > 1) {
							combinedParams += "&" + paramString;
						} else {
							combinedParams += paramString;
						}
					}
				}
			} catch (Exception exception) {
			}
			request = new HttpGet(url + combinedParams);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			// this.executeRequest(request, url);
			break;
		}
		case POST: {
			request = new HttpPost(url);

			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}

			if (!params.isEmpty()) {
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}

			// this.executeRequest(request, url);
			break;
		}
		case PUT: {
			request = new HttpPut(url);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}
			if (!params.isEmpty()) {
				((HttpPut) request).setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			}
			// this.executeRequest(request, url);
			break;

		}
		case DELETE: {
			request = new HttpDelete(url);
			// add headers
			for (NameValuePair h : headers) {
				request.addHeader(h.getName(), h.getValue());
			}
			// this.executeRequest(request, url);
			break;

		}

		}
		// if (url != null && url.startsWith("https")) {
		// executeHttps();
		// return;
		// }
		if (request != null)
			this.executeRequest(request, url);
	}

	private void executeHttps() {
		try {
			URL urlConnection = new URL(url);
			RunSSL2.trustAllHosts();
			HttpsURLConnection https = (HttpsURLConnection) urlConnection.openConnection();
			https.setHostnameVerifier(RunSSL2.DO_NOT_VERIFY);

			// InputStream in = new
			// BufferedInputStream(urlConnection.getInputStream());
			// readStream(in);
		} catch (Exception e) {
		}
	}

	private void executeRequest(HttpUriRequest request, String url) {
		int timeout = TIME_OUT;
		HttpParams httpParameters = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeout);
		HttpConnectionParams.setSoTimeout(httpParameters, timeout);

		// HttpClient client = new DefaultHttpClient(httpParameters);
		HttpClient client = new DefaultHttpClient(httpParameters);
		client = new RunSSL().getDefaultHttpClient(timeout);

		HttpResponse httpResponse;

		try {
			httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				response = EntityUtils.toString(entity);
			}
		} catch (ClientProtocolException e) {
			client.getConnectionManager().shutdown();
		} catch (IOException e) {
			client.getConnectionManager().shutdown();
		}
	}

	public void executeUploadFile(Context context, boolean isPOST) throws Exception {
		final HttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
		HttpClient client = new DefaultHttpClient(httpParams);
		// HttpPut request = new HttpPut(url);
		// client.getParams().setIntParameter("http.connection.timeout", 15 *
		// 1000);
		HttpEntityEnclosingRequestBase request = new HttpPost(url);
		if (!isPOST) {
			request = new HttpPut(url);
		}
		MultipartEntity partEntity = new MultipartEntity();

		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}

		for (NameValuePair p : params) {
			if (p.getName().equals("images")) {
				partEntity.addPart(p.getName(), new FileBody(Conts.getFileFromPath(context, p.getValue()), "image/jpeg"));
			}
			if (p.getName().equals("file")) {
				partEntity.addPart(p.getName(), new FileBody(Conts.getFileFromPath(context, p.getValue()), "image/jpeg"));
			} else {
				partEntity.addPart(p.getName(), new StringBody(p.getValue()));
			}
		}

		request.setEntity(partEntity);
		try {
			HttpResponse httpResponse = client.execute(request);
			responseCode = httpResponse.getStatusLine().getStatusCode();
			message = httpResponse.getStatusLine().getReasonPhrase();

			HttpEntity entity = httpResponse.getEntity();

			if (entity != null) {
				response = EntityUtils.toString(entity);
			}

		} catch (Exception e) {
			client.getConnectionManager().shutdown();
			e.printStackTrace();
			// LogUtils.e("ABC", e);
		}
	}

	public void exeDownloadFile(RequestInfo requestInfo, final IDownloadUploadFileCallBack downloadUploadFileCallBack) {

		if (downloadUploadFileCallBack != null) {
			downloadUploadFileCallBack.start();
		}

		File mDirectory = new File(requestInfo.getFileFolderSaveFile());

		if (!mDirectory.exists()) {
			mDirectory.mkdirs();
		}

		if (!mDirectory.exists()) {
			if (downloadUploadFileCallBack != null) {
				downloadUploadFileCallBack.error(IDownloadUploadFileCallBack.STATUS_CREATE_FILE_FOLDER_FAIL);
			}

			return;
		}

		// create file
		File mFile = new File(mDirectory, requestInfo.getFileNameSave());

		HttpURLConnection urlConnection = null;
		FileOutputStream fileOutput = null;
		long total = 0;
		long fileSize = 0;
		try {
			URL tmp = new URL(requestInfo.getUrl());
			urlConnection = (HttpURLConnection) tmp.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(TIME_OUT);
			urlConnection.setConnectTimeout(TIME_OUT);
			urlConnection.connect();

			String typeData = urlConnection.getHeaderField("content-type");
			if (typeData.contains("text/plain")) {

			} else {
				fileSize = Long.parseLong(urlConnection.getHeaderField("content-length"));
				String getDateModifier = urlConnection.getHeaderField("last-modified");

				if (downloadUploadFileCallBack != null) {

					downloadUploadFileCallBack.onProcess(fileSize, total);
				}

				fileOutput = new FileOutputStream(mFile);

				InputStream inputStream = urlConnection.getInputStream();

				byte[] buffer = new byte[BUFFER];
				int bufferLength = 0;

				while ((bufferLength = inputStream.read(buffer)) > 0) {
					total += bufferLength;
					fileOutput.write(buffer, 0, bufferLength);

					if (downloadUploadFileCallBack != null) {
						downloadUploadFileCallBack.onProcess(fileSize, total);
					}
				}

				fileOutput.close();
			}
		} catch (Exception e) {
		} finally {
			try {
				urlConnection.disconnect();
			} catch (Exception e2) {
			}
		}

		if (fileSize == 0 || mFile.length() < fileSize && fileSize != 0) {
			mFile.delete();
			if (downloadUploadFileCallBack != null) {
				downloadUploadFileCallBack.error(IDownloadUploadFileCallBack.STATUS_DOWNLOAD_UPLOAD_FAIL);
			}
		} else {
			if (downloadUploadFileCallBack != null) {
				downloadUploadFileCallBack.sucess();
			}
		}
	}

	public interface IDownloadUploadFileCallBack {
		public static final int STATUS_CREATE_FILE_FOLDER_FAIL = 1;
		public static final int STATUS_SUCESS = 2;
		public static final int STATUS_DOWNLOAD_UPLOAD_FAIL = 3;

		/**
		 * 
		 */
		public void start();

		/**
		 * @param code
		 */
		public void error(int code);

		public void sucess();

		/**
		 * 
		 * @param total
		 * @param curent
		 */
		public void onProcess(long total, long curent);

	}

	public File exeDownloadFile(final Context context) {

		File mDirectory = android.os.Environment.getExternalStorageDirectory();
		// create file
		File mFile = new File(mDirectory, System.currentTimeMillis() + "");

		HttpURLConnection urlConnection = null;
		FileOutputStream fileOutput = null;
		long total = 0;
		long fileSize = 0;
		try {
			URL tmp = new URL(url);
			urlConnection = (HttpURLConnection) tmp.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.setReadTimeout(TIME_OUT);
			urlConnection.setConnectTimeout(TIME_OUT);
			urlConnection.connect();

			String typeData = urlConnection.getHeaderField("content-type");
			if (typeData.contains("text/plain")) {

			} else {
				fileSize = Long.parseLong(urlConnection.getHeaderField("content-length"));
				String getDateModifier = urlConnection.getHeaderField("last-modified");

				fileOutput = new FileOutputStream(mFile);

				InputStream inputStream = urlConnection.getInputStream();

				byte[] buffer = new byte[BUFFER];
				int bufferLength = 0;

				while ((bufferLength = inputStream.read(buffer)) > 0) {
					total += bufferLength;
					fileOutput.write(buffer, 0, bufferLength);

				}

				fileOutput.close();
			}
		} catch (Exception e) {
			// LogUtils.e("mException", e);
		} finally {
			try {
				urlConnection.disconnect();
			} catch (Exception e2) {
			}
		}

		return mFile;
	}
}

// ============================================================
// RequestInfo
