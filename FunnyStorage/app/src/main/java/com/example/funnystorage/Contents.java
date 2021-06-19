package com.example.funnystorage;


//json 형태의 데이터를 저장할 객체의 클래스
public class Contents  {

    String video_title;
    String url_video;
    String video_image;

    //수정
    public Contents (String video_title, String url_video) {
        this.video_title=video_title;
        this.url_video=url_video;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getUrl_video() {
        return url_video;
    }

    public void setUrl_video(String url_video) {

        this.url_video = url_video;
    }

    public String getVideo_image() {
        return video_image;
    }

    public void setVideo_image(String video_image) {
        this.video_image = video_image;
    }

}

