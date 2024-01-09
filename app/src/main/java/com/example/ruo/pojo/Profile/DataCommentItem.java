package com.example.ruo.pojo.Profile;

import com.google.gson.annotations.SerializedName;

public class DataCommentItem{

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("id_message")
	private int idMessage;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("id_comment")
	private int idComment;

	@SerializedName("id_user")
	private int idUser;

	@SerializedName("isi_comment")
	private String isiComment;

	public DataCommentItem(String isiComment,  int idComment) {
		this.isiComment= isiComment;
		this.idComment = idComment;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public int getIdMessage(){
		return idMessage;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getIdComment(){
		return idComment;
	}

	public int getIdUser(){
		return idUser;
	}

	public String getIsiComment(){
		return isiComment;
	}
}