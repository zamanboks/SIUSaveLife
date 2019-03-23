package com.example.siusavelife;

import android.content.Context;

class User {

    Context context;
    private  String name,phone,email,blood,city,address,password,status;

    public User() {
    }

    public User(Context context, String name, String phone, String email, String blood, String city, String address, String password, String status) {
        this.context = context;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.blood = blood;
        this.city = city;
        this.address = address;
        this.password = password;
        this.status = status;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
