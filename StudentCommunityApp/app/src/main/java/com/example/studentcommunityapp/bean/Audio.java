package com.example.studentcommunityapp.bean;

import org.json.JSONException;
import org.json.JSONObject;

public class Audio {
    private String Audio_ID;
    private String Audio_picture;
    private String Audio_title;

    public Audio(JSONObject object) throws JSONException
    {
        this.Audio_ID=object.optString("dataid","");
        this.Audio_picture=object.optString("picture","");
        this.Audio_title=object.optString("title","");
    }

    public Audio(String Audio_ID,String Audio_picture,String Audio_title)
    {
        this.Audio_ID=Audio_ID;
        this.Audio_picture=Audio_picture;
        this.Audio_title=Audio_title;
    }
    public String getAudio_ID() {return Audio_ID;}

    public String getAudio_picture()
    {
        return Audio_picture;
    }

    public String getAudio_title()
    {
        return Audio_title;
    }
}

