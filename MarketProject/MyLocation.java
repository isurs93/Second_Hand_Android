package com.androidlec.marketproject;

public class MyLocation {

    private double myLatitude;
    private double myLongitude;
    private String myAddress;

    public MyLocation(double myLatitude, double myLongitude, String myAddress) {
        this.myLatitude = myLatitude;
        this.myLongitude = myLongitude;
        this.myAddress = myAddress;
    }

    public double getMyLatitude() {
        return myLatitude;
    }

    public void setMyLatitude(double myLatitude) {
        this.myLatitude = myLatitude;
    }

    public double getMyLongitude() {
        return myLongitude;
    }

    public void setMyLongitude(double myLongitude) {
        this.myLongitude = myLongitude;
    }

    public String getMyAddress() {
        return myAddress;
    }

    public void setMyAddress(String myAddress) {
        this.myAddress = myAddress;
    }

}
