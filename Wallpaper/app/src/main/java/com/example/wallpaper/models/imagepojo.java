package com.example.wallpaper.models;

public class imagepojo {
    String name,original,thumbnail;

    public imagepojo(String name, String original, String thumbnail) {
        this.name = name;
        this.original = original;
        this.thumbnail = thumbnail;
    }

    public imagepojo() {
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
