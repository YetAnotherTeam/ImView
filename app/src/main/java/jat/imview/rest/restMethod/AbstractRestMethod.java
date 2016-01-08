package jat.imview.rest.restMethod;

import jat.imview.rest.Request;
import jat.imview.rest.Response;
import jat.imview.rest.RestClient;

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