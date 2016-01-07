package jat.imview.rest;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class RestClient {
    private static final int CHUNK_SIZE = 1024;
	private static final String LOG_TAG = "MyRequest";

	public Response execute(Request request) {
		HttpURLConnection connection = null;
		Response response = null;
		int statusCode = -1;
		try {
			URL url = request.getRequestUri().toURL();
			Log.d(LOG_TAG, url.toString());
			connection = (HttpURLConnection) url.openConnection();
			switch (request.getMethod()) {
			case GET:
				connection.setDoOutput(false);
				break;
			case POST:
				byte[] payload = request.getBody();
				connection.setDoOutput(true);
				connection.setFixedLengthStreamingMode(payload.length);
				connection.getOutputStream().write(payload);
			default:
				break;
			}

			statusCode = connection.getResponseCode();
            byte[] body;
			if (connection.getContentLength() > 0) {
				BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
				body = readStream(in);
			} else {
				body = new byte[] {};
			}
            response = new Response(statusCode, body);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (connection != null)
				connection.disconnect();
		}
		
		if (response == null) {
			response = new Response(statusCode, new byte[] {});
		}
		
		return response;
	}

	private static byte[] readStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[CHUNK_SIZE];
		int count;
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(CHUNK_SIZE);
		while ((count = inputStream.read(buffer)) != -1)
			byteArrayOutputStream.write(buffer, 0, count);
		return byteArrayOutputStream.toByteArray();
	}
}
