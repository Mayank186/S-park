package com.example.spark;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class UserProfile {
    public String account;
    public String name;
    public String email;
    public String phone;
    public String url;

    public UserProfile() {
    }

    public UserProfile(String account, String name, String email, String phone, String url) {
        this.account = account;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.url = url;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
