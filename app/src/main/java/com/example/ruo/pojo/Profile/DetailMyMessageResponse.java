package com.example.ruo.pojo.Profile;

import com.google.gson.annotations.SerializedName;

public class DetailMyMessageResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("detailMyMessage")
	private DetailMyMessage detailMyMessage;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public DetailMyMessage getDetailMyMessage(){
		return detailMyMessage;
	}
}