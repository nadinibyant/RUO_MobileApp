package com.example.ruo.pojo.message;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {

	@SerializedName("id_message")
	private int idMessage;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("isi_message")
	private String isiMessage;

	@SerializedName("isi_comment")
	private String isiComment;

	@SerializedName("id_hide")
	private int idHide;

	@SerializedName("created_at")
	private String createdAt;

	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}

	public String getIsiMessage() {
		return isiMessage;
	}

	public void setIsiMessage(String isiMessage) {
		this.isiMessage = isiMessage;
	}

	public int getIdHide() {
		return idHide;
	}

	public void setIdHide(int idHide) {
		this.idHide = idHide;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getIsiComment() {
		return isiComment;
	}
}