package com.example.ruo.pojo.peta;

import com.google.gson.annotations.SerializedName;

public class PetaResponse{

	@SerializedName("data")
	private Data data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("location")
	private Location location;

	@SerializedName("message")
	private String message;

	public Data getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public Location getLocation(){
		return location;
	}

	public String getMessage(){
		return message;
	}
}