package com.example.ruo.pojo.message;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GetAllMessageResponse {

	@SerializedName("dataMessage")
	private List<MessageResponse> allMessageItems;

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

	public List<MessageResponse> getAllMessageItems() {
		return allMessageItems;
	}
}