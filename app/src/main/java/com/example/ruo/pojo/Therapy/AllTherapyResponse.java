package com.example.ruo.pojo.Therapy;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AllTherapyResponse{

	@SerializedName("allTherapy")
	private List<AllTherapyItem> allTherapy;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<AllTherapyItem> getAllTherapy(){
		return allTherapy;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}