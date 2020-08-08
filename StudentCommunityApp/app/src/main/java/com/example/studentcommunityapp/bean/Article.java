package com.example.studentcommunityapp.bean;

public class Article {
    private String imgUrl; //头像
    private String name; //用户昵称
    private String type; // 类型，文章/视频/音频
    private String title; //标题
    private String picture; //封面图
    private String content; //文章内容

    public Article(){}
    public Article(String imgUrl, String name, String type, String title, String picture, String content) {
        this.imgUrl = imgUrl;
        this.name = name;
        this.type = type;
        this.title = title;
        this.picture = picture;
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public String getPicture() {
        return picture;
    }

    public String getContent() {
        return content;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
