package jat.imview.rest.http;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import jat.imview.rest.Utils;

public class HTTPClient {
    private static final int CHUNK_SIZE = 1024;
    private static final String LOG_TAG = "MyRequest";
    static final String COOKIES_HEADER = "Set-Cookie";
    static java.net.CookieManager msCookieManager = new java.net.CookieManager();

    public Response execute(Request request) {
        HttpURLConnection connection = null;
        Response response = null;
        int statusCode = -1;
        try {
            URL url = request.getRequestUri().toURL();
            Log.d(LOG_TAG, url.toString());
            connection = (HttpURLConnection) url.openConnection();
            if (msCookieManager.getCookieStore().getCookies().size() > 0) {
                //While joining the Cookies, use ',' or ';' as needed. Most of the server are using ';'
                connection.setRequestProperty("Cookie", TextUtils.join(";", msCookieManager.getCookieStore().getCookies()));
            }
            switch (request.getMethod()) {
                case GET:
                    connection.setDoOutput(false);
                    break;
                case POST:
                    connection.setDoOutput(true);
                    OutputStream os = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(Utils.getPostDataString(request.getPostDataParams()));
                    writer.flush();
                    writer.close();
                    os.close();
                default:
                    break;
            }
            statusCode = connection.getResponseCode();

            // Записываем cookie из заголовков ответа
            Map<String, List<String>> headerFields = connection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);
            if (cookiesHeader != null) {
                for (String cookie : cookiesHeader) {
                    msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                }
            }

            byte[] body;
            if (connection.getContentLength() > 0) {
                BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
                body = readStream(in);
            } else {
                body = new byte[]{};
            }
            response = new Response(statusCode, body);

        } catch (IOException e) {
            /* Бага, связанная с тем, что сервер посылает ошибку 401 (Unauthorized), но не отдает
            заголовок "WWW-Authenticate". Подробнее по ссылке: http://stackoverflow.com/a/21534175
            */
            if (e.getMessage().contains("authentication challenge")) {
                statusCode = HttpURLConnection.HTTP_UNAUTHORIZED;
            } else {
                e.printStackTrace();
            }
        } finally {
            if (connection != null)
                connection.disconnect();
        }

        if (response == null) {
            response = new Response(statusCode, new byte[]{});
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

    public static void updateCookies() {

    }
}
