package com.vjsm.sports.kaalai;

import android.support.annotation.NonNull;

public class YUsers  {
    public   YUsers(){

    }

    String VideoUrl;
    String Dec;

    public String getDec() {
        return Dec;
    }

    public void setDec(String dec) {
        Dec = dec;
    }

    public YUsers(String videoUrl, String dec) {
        VideoUrl = videoUrl;
        Dec = dec;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public YUsers(String videoUrl) {
        VideoUrl = videoUrl;
    }




}
