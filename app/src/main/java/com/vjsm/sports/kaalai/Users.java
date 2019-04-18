package com.vjsm.sports.kaalai;

import android.support.annotation.NonNull;

import java.util.Date;

import static android.text.format.DateUtils.formatDateTime;

public class Users implements Comparable {
    String Name;
    String Place;
    String District;
    String Startdate;
    String Madtdate;
    String Mantdate;
    String Phone;
    String Imageurl;
    String MapLocationLa;
    String MapLocationLo;
    String UserId;
    String Id;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCurrentDateWithTime() {
        return CurrentDateWithTime;
    }

    public void setCurrentDateWithTime(String currentDateWithTime) {
        CurrentDateWithTime = currentDateWithTime;
    }

    public String getDateFormat() {
        return DateFormat;
    }

    public void setDateFormat(String dateFormat) {
        DateFormat = dateFormat;
    }

    String CurrentDateWithTime;

    public Users(String name, String place, String district, String startdate, String madtdate, String mantdate, String phone, String imageurl, String mapLocationLa, String mapLocationLo, String userId, String id, String currentDateWithTime, String dateFormat) {
        Name = name;
        Place = place;
        District = district;
        Startdate = startdate;
        Madtdate = madtdate;
        Mantdate = mantdate;
        Phone = phone;
        Imageurl = imageurl;
        MapLocationLa = mapLocationLa;
        MapLocationLo = mapLocationLo;
        UserId = userId;
        Id = id;
        CurrentDateWithTime = currentDateWithTime;
        DateFormat = dateFormat;
    }

    String DateFormat;

    public String getMapLocationLa() {
        return MapLocationLa;
    }

    public void setMapLocationLa(String mapLocationLa) {
        MapLocationLa = mapLocationLa;
    }

    public String getMapLocationLo() {
        return MapLocationLo;
    }

    public void setMapLocationLo(String mapLocationLo) {
        MapLocationLo = mapLocationLo;
    }

    public Users() {

    }

    public Users(String name, String place, String district, String startdate, String madtdate, String mantdate, String phone, String imageurl) {
        Name = name;
        Place = place;
        District = district;
        Startdate = startdate;
        Madtdate = madtdate;
        Mantdate = mantdate;
        Phone = phone;
        Imageurl = imageurl;
    }
    public Users(String mapLocationLa, String mapLocationLo) {
        MapLocationLa = mapLocationLa;
        MapLocationLo = mapLocationLo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlace() {
        return Place;
    }

    public void setPlace(String place) {
        Place = place;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getStartdate() {
        return Startdate;
    }

    public void setStartdate(String startdate) {
        Startdate = startdate;
    }

    public String getMadtdate() {
        return Madtdate;
    }

    public void setMadtdate(String madtdate) {
        Madtdate = madtdate;
    }

    public String getMantdate() {
        return Mantdate;
    }

    public void setMantdate(String mantdate) {
        Mantdate = mantdate;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    @Override
    public int compareTo(@NonNull Object o) {
o.equals(getStartdate());
        return 0;
    }
}
