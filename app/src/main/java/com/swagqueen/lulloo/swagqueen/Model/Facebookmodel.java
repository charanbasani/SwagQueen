package com.swagqueen.lulloo.swagqueen.Model;

public class Facebookmodel {
    String name,id,full_picture,message,story;

    public Facebookmodel() {
    }

    public Facebookmodel(String name, String id, String full_picture, String message, String story) {
        this.name = name;
        this.id = id;
        this.full_picture = full_picture;
        this.message = message;
        this.story = story;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFull_picture() {
        return full_picture;
    }

    public void setFull_picture(String full_picture) {
        this.full_picture = full_picture;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }
}
