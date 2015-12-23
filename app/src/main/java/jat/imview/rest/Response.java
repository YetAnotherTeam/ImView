package jat.imview.rest;

import java.util.List;
import java.util.Map;

public class Response {
    public int status;
    public  Map<String, List<String>> headers;
    public byte[] body;
    protected Response(int status,  Map<String, List<String>> headers, byte[] body) {
        this.status = status; 
        this.headers = headers; 
        this.body = body;
    }
}

