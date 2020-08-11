package com.example.studentcommunityapp.bean;

public class Content {
    private int Content_id;
    private String Content_title;

    public Content(int Content_id,String Content_name)
    {
        this.Content_id=Content_id;
        this.Content_title=Content_name;
    }

    public int getContent_id() {
        return Content_id;
    }

    public String getContent_name()
    {
        return Content_title;
    }
}
