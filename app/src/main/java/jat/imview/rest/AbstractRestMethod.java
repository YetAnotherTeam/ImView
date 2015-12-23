package jat.imview.rest;

import android.content.Context;

import java.util.List;
import java.util.Map;

public abstract class AbstractRestMethod<T> implements RestMethod<T> {
    private static final String DEFAULT_ENCODING = "UTF-8";

    public RestMethodResult<T> execute() {
        Request request = buildRequest();
        Response response = doRequest(request);
        return buildResult(response);
    }

    protected abstract Context getContext();

    protected RestMethodResult<T> buildResult(Response response) {
        int status = response.status;
        String statusMessage = "";
        T resource = null;
        try {
            String responseBody = new String(response.body, getCharacterEncoding(response.headers));
            resource = parseResponseBody(responseBody);
        } catch (Exception ex) {
            status = 500;
            statusMessage = ex.getMessage();
        }
        return new RestMethodResult<>(status, statusMessage, resource);
    }

    protected abstract Request buildRequest();

    protected abstract T parseResponseBody(String responseBody) throws Exception;

    private Response doRequest(Request request) {
        RestClient client = new RestClient();
        return client.execute(request);
    }

    private String getCharacterEncoding(Map<String, List<String>> headers) {
        return DEFAULT_ENCODING;
    }
}