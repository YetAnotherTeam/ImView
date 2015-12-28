package jat.imview.rest;


public class RestMethodResult<T> {
	private int statusCode = 0;
	private String statusMessage;
	private T resource;
	
	public RestMethodResult(int statusCode, String statusMessage, T resource) {
		super();
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		this.resource = resource;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public T getResource() {
		return resource;
	}
	
}
