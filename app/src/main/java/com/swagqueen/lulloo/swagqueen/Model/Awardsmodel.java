package com.swagqueen.lulloo.swagqueen.Model;

import com.google.firebase.database.Exclude;

public class Awardsmodel {
    String forr;
    String image;
    String year;
    String mKey;

    public Awardsmodel() {
    }

    public Awardsmodel(String forr, String image, String year) {
        this.forr = forr;
        this.image = image;
        this.year = year;
    }

    public String getForr() {
        return forr;
    }

    public void setForr(String forr) {
        this.forr = forr;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
    @Exclude
    public String getKey() {
        return mKey;
    }

    @Exclude
    public void setKey(String key) {
        mKey = key;
    }
}
