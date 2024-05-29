package com.example.labourarportal.Pojo;

import androidx.annotation.NonNull;

public class ContractorInfo {
    @NonNull
    public String name,address,contact,password,latitude,longitude,visitingCharges,contractor_skills,status,url;

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public String getStatus() {
        return status;
    }

    public void setStatus(@NonNull String status) {
        this.status = status;
    }

    @NonNull
    public String getContractor_skills() {
        return contractor_skills;
    }

    public void setContractor_skills(@NonNull String contractor_skills) {
        this.contractor_skills = contractor_skills;
    }

    @NonNull
    public String getVisitingCharges() {
        return visitingCharges;
    }

    public void setVisitingCharges(@NonNull String visitingCharges) {
        this.visitingCharges = visitingCharges;
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
    public String getLatitude() {return latitude;}

    public void setLatitude(@NonNull String latitude) {this.latitude = latitude;}

    @NonNull
    public String getLongitude() {return longitude;}

    public void setLongitude(@NonNull String longitude) {this.longitude = longitude;}
}

