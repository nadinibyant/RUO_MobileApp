package com.example.ruo.pojo.message;

import com.google.gson.annotations.SerializedName;

public class AccountResponse {

    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("dataUser")
    private AccountItem dataUser;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AccountItem getDataUser() {
        return dataUser;
    }

    public void setDataUser(AccountItem dataUser) {
        this.dataUser = dataUser;
    }
}