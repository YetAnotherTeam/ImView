package jat.imview.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private URI requestUri;
    private byte[] body;
    private HTTPMethod method;

    public Request(HTTPMethod method, URI requestUri) {
        this.method = method;
        this.requestUri = requestUri;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public URI getRequestUri() {
        return requestUri;
    }

    public byte[] getBody() {
        return body;
    }
}