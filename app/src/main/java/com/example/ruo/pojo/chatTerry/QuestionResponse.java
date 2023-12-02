package com.example.ruo.pojo.chatTerry;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class QuestionResponse{

	@SerializedName("question")
	private List<QuestionItem> question;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<QuestionItem> getQuestion(){
		return question;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}