package com.swagqueen.lulloo.swagqueen.Model;

import com.google.firebase.database.Exclude;

public class Gallerymodel {
    String image;
    String mKey;

    public Gallerymodel() {
    }

    public Gallerymodel(String image) {
        this.image = image;
    }

    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
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
