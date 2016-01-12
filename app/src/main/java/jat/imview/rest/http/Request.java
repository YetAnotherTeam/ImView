package jat.imview.rest.http;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class Request {
    private URI requestUri;
    private HTTPMethod method;
    private Map<String, String> postDataParams;

    public Request(HTTPMethod method, URI requestUri, Map<String, String> postDataParams) {
        this.method = method;
        this.requestUri = requestUri;
        this.postDataParams = postDataParams;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public URI getRequestUri() {
        return requestUri;
    }

    public Map<String, String> getPostDataParams() {
        return postDataParams;
    }
}