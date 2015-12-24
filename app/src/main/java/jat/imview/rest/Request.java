package jat.imview.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private URI requestUri;
    private Map<String, List<String>> headers;
    private byte[] body;
    private HTTPMethod method;

    public Request(HTTPMethod method, URI requestUri, Map<String, List<String>> headers,
                   byte[] body) {
        this.method = method;
        this.requestUri = requestUri;
        this.headers = headers;
        this.body = body;
    }

    public HTTPMethod getMethod() {
        return method;
    }

    public URI getRequestUri() {
        return requestUri;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void addHeader(String key, List<String> value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
    }
}