package jat.imview.rest;

import android.content.Context;

import java.util.List;
import java.util.Map;

public abstract class AbstractRestMethod<T> implements RestMethod<T> {
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
            String responseBody = new String(response.body);
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
}