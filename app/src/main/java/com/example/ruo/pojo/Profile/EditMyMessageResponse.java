package com.example.ruo.pojo.Profile;

import com.google.gson.annotations.SerializedName;

public class EditMyMessageResponse{

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