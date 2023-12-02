package com.example.ruo.pojo.Therapy;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MyTherapyResponse{

	@SerializedName("myTherapy")
	private List<MyTherapyItem> myTherapy;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<MyTherapyItem> getMyTherapy(){
		return myTherapy;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}