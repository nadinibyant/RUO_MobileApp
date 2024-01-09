package com.example.ruo.pojo.chatTerry;

import com.google.gson.annotations.SerializedName;

public class QuestionItem{

	@SerializedName("id_chat")
	private int idChat;

	@SerializedName("updated_at")
	private String updatedAt;

	@SerializedName("created_at")
	private String createdAt;

	@SerializedName("pertanyaan")
	private String pertanyaan;

	public int getIdChat(){
		return idChat;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPertanyaan(){
		return pertanyaan;
	}
}