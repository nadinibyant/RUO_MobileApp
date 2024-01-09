package com.example.ruo.pojo;

import com.google.gson.annotations.SerializedName;

public class LoginResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("message")
	private String message;

	@SerializedName("token")
	private String token;

	public boolean isSuccess(){
		return success;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getMessage(){
		return message;
	}

	public String getToken(){
		return token;
	}
}