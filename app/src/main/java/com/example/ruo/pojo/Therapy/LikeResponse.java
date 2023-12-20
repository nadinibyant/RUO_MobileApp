package com.example.ruo.pojo.Therapy;

import com.google.gson.annotations.SerializedName;

public class LikeResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("id_user_therapy")
	private int idUserTherapy;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public int getIdUserTherapy(){
		return idUserTherapy;
	}

	public String getMessage(){
		return message;
	}
}