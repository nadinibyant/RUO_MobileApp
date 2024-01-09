package com.example.ruo.pojo.Profile;

import com.google.gson.annotations.SerializedName;

public class DetailMyMessage{

	@SerializedName("isi_message")
	private String isiMessage;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_message")
	private int idMessage;

	@SerializedName("id_hide")
	private int idHide;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_user")
	private int idUser;

	public String getIsiMessage(){
		return isiMessage;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getIdMessage(){
		return idMessage;
	}

	public int getIdHide(){
		return idHide;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getIdUser(){
		return idUser;
	}
}