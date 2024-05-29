package com.example.labourarportal.Pojo;

import androidx.annotation.NonNull;

public class WorkerInfo {
    @NonNull
    public String name,address,contact,password,status,latitude,longitude,visit_charges,worker_skill,profile,url;

    @NonNull
    public String getProfile() {
        return profile;
    }

    public void setProfile(@NonNull String profile) {
        this.profile = profile;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getVisit_charges() {
        return visit_charges;
    }

    public void setVisit_charges(@NonNull String visit_charges) {
        this.visit_charges = visit_charges;
    }

    @NonNull
    public String getWorker_skill() {
        return worker_skill;
    }

    public void setWorker_skill(@NonNull String worker_skill) {
        this.worker_skill = worker_skill;
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
    }

    @NonNull
    public String getStatus() {return status;}

    public void setStatus(@NonNull String status) {this.status = status;}

    @NonNull
    public String getLatitude() {return latitude;}

    public void setLatitude(@NonNull String latitude) {this.latitude = latitude;}

    @NonNull
    public String getLongitude() {return longitude;}

    public void setLongitude(@NonNull String longitude) {this.longitude = longitude;}
}
