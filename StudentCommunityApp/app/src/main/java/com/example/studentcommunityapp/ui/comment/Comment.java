package com.example.studentcommunityapp.ui.comment;

public class Comment {
    private String comment_id;
    private String comment_time;
    private String comment_content;

    public Comment(String comment_id,String comment_time ,String comment_content)
    {
        setComment_id(comment_id);
        setComment_time(comment_time);
        setComment_content(comment_content);

    }

    public String getComment_id()
    {
        return comment_id;
    }

    public String getComment_time()
    {
        return comment_time;
    }

    public String getComment_content()
    {
        return comment_content;
    }

    public void setComment_id(String comment_id)
    {
        this.comment_id=comment_id;
    }

    public void setComment_time(String comment_time)
    {
        this.comment_time=comment_time;
    }

    public void setComment_content(String comment_content)
    {
        this.comment_content=comment_content;
    }
}
