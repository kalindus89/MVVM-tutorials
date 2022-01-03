package com.mvvm_tutorials.mvvm_lesson_1.model;

public class PlacesModel {

    private String title;
    private String imageUrl;

    public PlacesModel() {

    }

    public PlacesModel(String title, String imageUrl) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
