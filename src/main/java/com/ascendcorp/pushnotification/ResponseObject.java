package com.ascendcorp.pushnotification;

public class ResponseObject {

	RequestObject input;
	String status;
	public ResponseObject(RequestObject input, String status) {
		this.input = input;
		this.status = status;
	}
	public RequestObject getInput() {
		return input;
	}
	public void setInput(RequestObject input) {
		this.input = input;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}