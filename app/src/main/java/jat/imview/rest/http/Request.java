package jat.imview.rest.http;

import java.net.URI;
import java.util.HashMap;

public class Request {
    private URI requestUri;
    private HTTPMethod method;
    private HashMap<String, String> postDataParams;

    public Request(HTTPMethod method, URI requestUri, HashMap<String, String> postDataParams) {
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

    public HashMap<String, String> getPostDataParams() {
        return postDataParams;
    }
}