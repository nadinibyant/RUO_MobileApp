package com.example.ruo.pojo.chatTerry;

import com.google.gson.annotations.SerializedName;

public class Answer1Response {

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}