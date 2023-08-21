package com.example.bnn.kediri.models;

import com.google.gson.annotations.SerializedName;

public class ApiSingle<t> {
    @SerializedName("data")
    public t data;

    public t getData() {
        return data;
    }

    public void setData(t data) {
        this.data = data;
    }
}
