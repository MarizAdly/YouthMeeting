package com.example.mariz.youthmeeting.Models;

public class Post {

    private String  desc;
    private String imageUrl;

    public String getUserPhoto ( ) {
        return userPhoto;
    }

    public void setUserPhoto ( String userPhoto ) {
        this.userPhoto = userPhoto;
    }

    private String userPhoto;
    private String username;
    private String uid;

    public String getUid ( ) {
        return uid;
    }

    public void setUid ( String uid ) {
        this.uid = uid;
    }


    public Post ( String desc, String imageUrl, String username , String uid , String userPhoto) {

        this.desc = desc;
        this.imageUrl=imageUrl;
        this.username = username;
        this.uid = uid;
        this. userPhoto = userPhoto;
    }

    public String getDesc ( ) {
        return desc;
    }

    public void setDesc ( String desc ) {
        this.desc = desc;
    }

    public String getImageUrl ( ) {
        return imageUrl;
    }

    public void setImageUrl ( String imageUrl ) {
        this.imageUrl = imageUrl;
    }

    public String getUsername ( ) {
        return username;
    }

    public void setUsername ( String username ) {
        this.username = username;
    }

    public Post() {

    }
}
