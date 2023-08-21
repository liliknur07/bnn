package com.example.bnn.kediri.models;

import com.google.gson.annotations.SerializedName;

public class Counseling {
    @SerializedName("id")
    int id;
    @SerializedName("nama")
    String fullName;
    @SerializedName("alamat")
    String address;
    @SerializedName("jenis_kelamin")
    String gender;
    @SerializedName("tanggal_lahir")
    String dateBirth;
    @SerializedName("zat")
    String zat;
    @SerializedName("durasi")
    String usageTime;
    @SerializedName("phone")
    String phoneNumber;
    @SerializedName("status")
    String status;
    @SerializedName("user")
    String user;


    public Counseling(int id, String fullName, String address, String gender, String dateBirth, String zat, String usageTime, String status, String user, String phoneNumber) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.zat = zat;
        this.usageTime = usageTime;
        this.status = status;
        this.user = user;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public String getZat() {
        return zat;
    }

    public String getUsageTime() {
        return usageTime;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
