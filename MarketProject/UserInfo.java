package com.androidlec.marketproject;

public class UserInfo {
    int seqno;
    String id;
    String password;
    String name;
    String telno;
    String email;
    String address;

    public UserInfo(int seqno, String id, String password, String name, String telno, String email) {
        this.seqno = seqno;
        this.id = id;
        this.password = password;
        this.name = name;
        this.telno = telno;
        this.email = email;
    }

    public UserInfo(String id){
        this.id = id;
    }

    public int getSeqno() {
        return seqno;
    }

    public void setSeqno(int seqno) {
        this.seqno = seqno;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelno() {
        return telno;
    }

    public void setTelno(String telno) {
        this.telno = telno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
