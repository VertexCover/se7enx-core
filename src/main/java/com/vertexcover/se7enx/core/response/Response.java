package com.vertexcover.se7enx.core.response;

public class Response {

	private boolean status;
	private String response;
	private String responseType;
	private boolean responsePrettify;
	
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	
	public boolean isResponsePrettify() {
		return responsePrettify;
	}
	public void setResponsePrettify(boolean responsePrettify) {
		this.responsePrettify = responsePrettify;
	}
}
