package com.example.labourarportal.Pojo;

import androidx.annotation.NonNull;

public class CustomerInfo {
    @NonNull
    public String name,address,contact,password,latitude,longitude;

    @NonNull
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(@NonNull String latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(@NonNull String longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    @NonNull
    public String getContact() {
        return contact;
    }

    public void setContact(@NonNull String contact) {
        this.contact = contact;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }}
