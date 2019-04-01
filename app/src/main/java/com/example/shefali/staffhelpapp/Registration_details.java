package com.example.shefali.staffhelpapp;

public class Registration_details {
    String a,b,c,d,e,x;
    public Registration_details(){

    }

    public Registration_details(String x, String name, String des, String head, String nu,String email) {
        this.x = x;
        this.a = name;
        this.b = des;
        this.d = head;
        this.c = nu;
        this.e=email;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }
}
