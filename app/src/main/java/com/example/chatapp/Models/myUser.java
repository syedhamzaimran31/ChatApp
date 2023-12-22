package com.example.chatapp.Models;

public class myUser {
    private String uid,name,phoneNumber,profileImage;
        public myUser(){

        }
    public myUser(String uid, String name, String phoneNumber, String profileImage) {
        this.uid = uid;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
    }

    public String getUid() {
        return uid != null ? uid : "";
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name != null ? name : "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber != null ? phoneNumber : "";
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage != null ? profileImage : "";
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
