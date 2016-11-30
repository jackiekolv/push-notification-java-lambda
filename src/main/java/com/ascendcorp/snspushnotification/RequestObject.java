package com.ascendcorp.snspushnotification;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestObject{
	
	@Override
	public String toString() {
		return "RequestObject [phone=" + phone + ", textMessage=" + textMessage + "]";
	}
	
	@JsonProperty("phone")
	String phone;
	@JsonProperty("textMessage")
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