package com.example.bnn.kediri.models;

import com.google.gson.annotations.SerializedName;

public class ApiStatus {
    @SerializedName("status")
    private String status;
    @SerializedName("message")
    private String message;

    public ApiStatus(){}

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
