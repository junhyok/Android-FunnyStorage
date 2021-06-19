package com.example.funnystorage;

public class ContentsStory {
    String image_title;
    String url_image;
    String image_image;


    public  ContentsStory(String image_title, String url_image) {
        this.image_title=image_title;
        this.url_image=url_image;
    }

    public String getImage_title() {
        return image_title;
    }

    public void setImage_title(String image_title) {
        this.image_title = image_title;
    }

    public String getUrl_image() {
        return url_image;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getImage_image() {
        return image_image;
    }

    public void setImage_image(String image_image) {
        this.image_image = image_image;
    }
}
