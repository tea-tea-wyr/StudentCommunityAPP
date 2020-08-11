package com.example.studentcommunityapp.bean;

public class Audio {
    private int Audio_imageID;
    private String Audio_name;

    public Audio(int Audio_imageID,String Audio_name)
    {
        this.Audio_imageID=Audio_imageID;
        this.Audio_name=Audio_name;
    }

    public int getAudio_imageID()
    {
        return Audio_imageID;
    }

    public String getAudio_name()
    {
        return Audio_name;
    }
}

