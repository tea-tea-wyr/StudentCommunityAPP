package com.example.studentcommunityapp.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Video {
    private String Video_picture;
    private String Video_ID;

    public Video(JSONObject object) throws JSONException
    {
        this.Video_ID=object.optString("dataid","");
        this.Video_picture=object.optString("picture","");
    }

    public Video(String Video_picture,String Video_ID)
    {
        this.Video_ID=Video_ID;
        this.Video_picture=Video_picture;
    }
    public String getVideo_ID() {return Video_ID;}

    public String getVideo_picture()
    {
        return Video_picture;
    }

}
