package com.ascendcorp.pushnotification;

public class RequestObject{
	
	@Override
	public String toString() {
		return "RequestObject [phone=" + phone + ", textMessage=" + textMessage + "]";
	}
	
	String phone;
	String textMessage;
	
	public RequestObject() {
	}
	
	public RequestObject(String phone, String textMessage) {
		this.phone = phone;
		this.textMessage = textMessage;
	}
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTextMessage() {
		return textMessage;
	}
	public void setTextMessage(String textMessage) {
		this.textMessage = textMessage;
	}
}