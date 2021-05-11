package com.proximity.myapplication.home.model;

import java.io.Serializable;

public class AqiData implements Serializable {
    String city,aqi,time;

    public AqiData(String city, String aqi, String time) {
        this.city = city;
        this.aqi = aqi;
        this.time = time;
    }

    public AqiData() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "AqiData{" +
                "city='" + city + '\'' +
                ", aqi='" + aqi + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
