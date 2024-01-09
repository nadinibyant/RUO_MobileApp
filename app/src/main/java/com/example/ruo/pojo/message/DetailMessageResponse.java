package com.example.ruo.pojo.message;

import com.google.gson.annotations.SerializedName;

public class DetailMessageResponse {

    @SerializedName("detailMyMessage")
    private MessageResponse detailMessage;

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

    public MessageResponse getDetailMessage() {
        return detailMessage;
    }
}