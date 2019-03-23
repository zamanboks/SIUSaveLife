package com.example.siusavelife;

public class Request {

    int id;
    public String fullname, number, date, hospital, why, city, blood, bag;

    public Request() {
    }

    public Request(int id, String fullname, String number, String date, String hospital, String why, String city, String blood, String bag) {
        this.id = id;
        this.fullname = fullname;
        this.number = number;
        this.date = date;
        this.hospital = hospital;
        this.why = why;
        this.city = city;
        this.blood = blood;
        this.bag = bag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getBag() {
        return bag;
    }

    public void setBag(String bag) {
        this.bag = bag;
    }
}