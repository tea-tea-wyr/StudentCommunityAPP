package com.example.studentcommunityapp.bean;

public class Essay {
    private int Essay_imageID;
    private String Essay_name;
    private String Essay_description;

    public Essay(int Essay_imageID,String Essay_name,String Essay_description)
    {
        this.Essay_imageID=Essay_imageID;
        this.Essay_name=Essay_name;
        this.Essay_description=Essay_description;
    }

    public int getEssay_imageID()
    {
        return Essay_imageID;
    }

    public String getEssay_name()
    {
        return Essay_name;
    }

    public String getEssay_description()
    {
        return Essay_description;
    }

}
