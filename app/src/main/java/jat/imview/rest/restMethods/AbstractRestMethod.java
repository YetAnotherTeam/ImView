package jat.imview.rest.restMethods;

import jat.imview.rest.Request;
import jat.imview.rest.Response;
import jat.imview.rest.RestClient;
import jat.imview.rest.RestMethod;

public abstract class AbstractRestMethod<T> implements RestMethod<T> {
    public Response execute() {
        Request request = buildRequest();
        Response response = doRequest(request);
        return response;
    }

    protected abstract Request buildRequest();

    private Response doRequest(Request request) {
        RestClient client = new RestClient();
        return client.execute(request);
    }
}