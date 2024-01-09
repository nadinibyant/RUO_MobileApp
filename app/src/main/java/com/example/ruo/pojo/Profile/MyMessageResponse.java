package com.example.ruo.pojo.Profile;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyMessageResponse {

	@SerializedName("success")
	private boolean success;

	@SerializedName("dataMyMessage")
	private List<DataMyMessageItem> dataMyMessage;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public List<DataMyMessageItem> getDataMyMessage(){
		return dataMyMessage;
	}

	public String getMessage(){
		return message;
	}
}