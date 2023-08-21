package com.example.bnn.kediri.models;

import com.google.gson.annotations.SerializedName;

public class SocializationP4GN {
    @SerializedName("id")
    int id;
    @SerializedName("nama")
    String fullName;
    @SerializedName("instansi")
    String company;
    @SerializedName("keperluan")
    String requested;
    @SerializedName("tanggal")
    String date;
    @SerializedName("waktu")
    String time;
    @SerializedName("status")
    String status;
    @SerializedName("user")
    String user;

    public SocializationP4GN(int id, String fullName, String company, String requested, String date, String time, String status, String user) {
        this.id = id;
        this.fullName = fullName;
        this.company = company;
        this.requested = requested;
        this.date = date;
        this.time = time;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getCompany() {
        return company;
    }

    public String getRequested() {
        return requested;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }
}
