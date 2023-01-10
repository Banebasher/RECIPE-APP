package com.example.recipe;

import android.graphics.Bitmap;

public class ModelClass {
    String username,email,password,address;
    private Bitmap profileImage;




    public ModelClass(String username, String email, String password, Bitmap profileImage,String address) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.address = address;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Bitmap getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        this.profileImage = profileImage;
    }

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}
}
