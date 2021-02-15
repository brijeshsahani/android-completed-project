package com.example.wallpaper.models;

import java.util.ArrayList;

public class categorypojo {
    String category,category_image,priority,is_new;
    ArrayList<imagepojo> images;

    public categorypojo(String category, String category_image, String priority, String is_new, ArrayList<imagepojo> images) {
        this.category = category;
        this.category_image = category_image;
        this.priority = priority;
        this.is_new = is_new;
        this.images = images;
    }

    public categorypojo() {
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory_image() {
        return category_image;
    }

    public void setCategory_image(String category_image) {
        this.category_image = category_image;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public ArrayList<imagepojo> getImages() {
        return images;
    }

    public void setImages(ArrayList<imagepojo> images) {
        this.images = images;
    }
}
