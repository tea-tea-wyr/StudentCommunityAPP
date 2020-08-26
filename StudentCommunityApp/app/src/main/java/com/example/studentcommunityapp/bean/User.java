package com.example.studentcommunityapp.bean;

import androidx.annotation.NonNull;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.ObjectInputStream;

public class User {
    private String imgUrl; //头像
    private String name; //用户昵称
    private String level; // 年级
    private String university; //学校
    private String college; //学院
    private String Class; //班级
    private String password;
    private String wechat;
    private String phone;
    private String email;

    public User(){}
    public User(JSONObject object){
        this.imgUrl = object.optString("picture","");
        this.name = object.optString("name", "");
        this.level = object.optString("yaer","");
        this.university = object.optString("school","");
        this.college = object.optString("collage","");
        this.Class = object.optString("class","");
        this.wechat = object.optString("wechat","");
        this.phone = object.optString("phone","");
        this.email = object.optString("email","");
    }


    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setClass(String aClass) {
        Class = aClass;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public String getUniversity() {
        return university;
    }

    public String getCollege() {
        return college;
    }


    public String getaClass() {
        return Class;
    }

    public String getPassword() {
        return password;
    }

    public String getWechat() {
        return wechat;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
