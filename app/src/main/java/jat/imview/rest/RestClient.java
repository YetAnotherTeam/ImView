package jat.imview.rest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

public class RestClient {
    private static final int CHUNK_SIZE = 10;

    public Response execute(Request request) {
		HttpURLConnection connection = null;
		Response response = null;
		int statusCode = -1;
		try {
			URL url = request.getRequestUri().toURL();
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

	private static byte[] readStream(InputStream in) throws IOException {
		byte[] buf = new byte[CHUNK_SIZE];
		int count;
		ByteArrayOutputStream out = new ByteArrayOutputStream(CHUNK_SIZE);
		while ((count = in.read(buf)) != -1)
			out.write(buf, 0, count);
		return out.toByteArray();
	}
}
