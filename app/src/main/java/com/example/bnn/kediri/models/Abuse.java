package com.example.bnn.kediri.models;

import com.google.gson.annotations.SerializedName;

public class Abuse {
    @SerializedName("id")
    int id;
    @SerializedName("kecamatan")
    String district;
    @SerializedName("phone")
    String phoneNumber;
    @SerializedName("alamat")
    String address;
    @SerializedName("long")
    String longitude;
    @SerializedName("lat")
    String latitude;
    @SerializedName("foto")
    String photo;
    @SerializedName("status")
    String status;
    @SerializedName("user")
    String user;

    public Abuse(int id, String district, String phoneNumber, String address, String longitude, String latitude, String photo, String status, String user) {
        this.id = id;
        this.district = district;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.photo = photo;
        this.status = status;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getDistrict() {
        return district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getPhoto() {
        return photo;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }
}
