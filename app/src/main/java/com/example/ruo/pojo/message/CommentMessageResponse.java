package com.example.ruo.pojo.message;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CommentMessageResponse {

    @SerializedName("dataComment")
    private List<MessageResponse> dataComment;

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public List<MessageResponse> getDataComment() {
        return dataComment;
    }
}