package com.example.shefali.staffhelpapp;

public class DataProvider {
    String name,date,type,contact_no;

    public DataProvider(String name, String date, String type, String contact_no) {
        this.name = name;
        this.date = date;
        this.type = type;
        this.contact_no = contact_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }
}
