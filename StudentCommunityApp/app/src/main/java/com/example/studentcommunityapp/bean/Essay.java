package com.example.studentcommunityapp.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Essay {
    private String Essay_ID;
    private String Picture;
    private String Essay_title;
    private String Essay_description;

    public Essay(JSONObject object) throws JSONException
    {
        this.Essay_ID=object.optString("dataid","");
        this.Picture= object.optString("picture","");
        this.Essay_title=object.optString("title","");
        this.Essay_description=object.optString("introduction","");
    }

    public Essay(String Essay_ID,String Picture,String Essay_title,String Essay_description)
    {
        this.Essay_ID=Essay_ID;
        this.Picture=Picture;
        this.Essay_title=Essay_title;
        this.Essay_description=Essay_description;
    }

    public String getEssay_ID()  {return Essay_ID;}

    public String getPicture()
    {
        return Picture;
    }

    public String getEssay_title()
    {
        return Essay_title;
    }

    public String getEssay_description()
    {
        return Essay_description;
    }

}
