package com.eproximiti.testingapp.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import android.util.Log;

/**
 * 
 */
public class API {
	private static final String TAG = "BaseAPI";

	public static final String URL_DEBUG = "http://testing.eproximiti.com/api/games";

	protected static HttpClient mHttpClient;

	public API() {
		buildHttpClient();
	}

	public static void buildHttpClient() {
		if (mHttpClient == null) {
			BasicHttpParams params = new BasicHttpParams();
			SchemeRegistry schemeRegistry = new SchemeRegistry();
			schemeRegistry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			final SSLSocketFactory sslSocketFactory = SSLSocketFactory
					.getSocketFactory();
			schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
			ClientConnectionManager cm = new ThreadSafeClientConnManager(
					params, schemeRegistry);
			mHttpClient = new DefaultHttpClient(cm, params);
		}
	}

	/**
	 * A GET HTTP request. Passing in {@code true} will include the users GUID.
	 * 
	 * @param url
	 * @param includeGUID
	 * @return
	 */
	public static String getListOfGames() {
		buildHttpClient();
		HttpGet getRequest = new HttpGet(URL_DEBUG);
		return httpHelper(getRequest);
	}

	public static String httpHelper(HttpRequestBase request) {
		Log.d(TAG, request.getURI().toString());
		// Get the response
		try {
			HttpResponse response = mHttpClient.execute(request);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				// A Simple JSON Response Read
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				// Log.i(TAG, result);
				return result;
			}
		} catch (UnknownHostException e) {
			Log.e(TAG, "Could not connect to host.");
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Converts the given {@link InputStream} to a String.
	 * 
	 * @param is
	 * @return
	 */
	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {

				is.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return sb.toString();
	}

	public static class Headers {
		public static final String AUTHORIZATION = "Authorization";
		public static final String CONTENT_TYPE = "Content-Type";
		public static final String CONTENT_TYPE_URLENCODED = "application/x-www-form-urlencoded";
		public static final String ACCEPT = "Accept";
		public static final String ACCEPT_JSON = "application/json";
	}

}
