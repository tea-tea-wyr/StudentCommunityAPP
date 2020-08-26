package com.example.studentcommunityapp.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Course {
    private String week_course; //课程
    private String week_day; //周几
    private String time; //具体时间
    private String place; // 地点


    public Course(){}
    public Course(String week_course, String week_day, String time, String place) {
        this.week_course = week_course;
        this.week_day = week_day;
        this.time = time;
        this.place = place;

    }

    public Course(JSONObject object) throws JSONException {
        this.week_course = object.optString("name", "");
        this.week_day = object.optString("time", "");
        this.time = object.optString("number", "");
        this.place = object.optString("place", "");
    }


    public String getWeek_course() {
        return week_course;
    }

    public void setWeek_course(String week_course) {
        this.week_course = week_course;
    }

    public String getWeek_day() {
        return week_day;
    }

    public void setWeek_day(String week_day) {
        this.week_day = week_day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
