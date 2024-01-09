package com.example.ruo.pojo.Profile;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CommentMessageResponse{

	@SerializedName("success")
	private boolean success;

	@SerializedName("dataComment")
	private List<DataCommentItem> dataComment;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public List<DataCommentItem> getDataComment(){
		return dataComment;
	}

	public String getMessage(){
		return message;
	}
}