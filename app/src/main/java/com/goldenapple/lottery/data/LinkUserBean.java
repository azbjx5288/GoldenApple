package com.goldenapple.lottery.data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sakura on 2018/7/12.
 */

public class LinkUserBean implements Serializable
{
    private int id;
    private int user_id;
    private String username;
    private int valid_days;
    private String channel;
    private String url;
    private int creat_count;
    private Date created_at;
    
    public int getId()
    {
        return id;
    }
    
    public void setId(int id)
    {
        this.id = id;
    }
    
    public int getUser_id()
    {
        return user_id;
    }
    
    public void setUser_id(int user_id)
    {
        this.user_id = user_id;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public int getValid_days()
    {
        return valid_days;
    }
    
    public void setValid_days(int valid_days)
    {
        this.valid_days = valid_days;
    }
    
    public String getChannel()
    {
        return channel;
    }
    
    public void setChannel(String channel)
    {
        this.channel = channel;
    }
    
    public String getUrl()
    {
        return url;
    }
    
    public void setUrl(String url)
    {
        this.url = url;
    }
    
    public int getCreat_count()
    {
        return creat_count;
    }
    
    public void setCreat_count(int creat_count)
    {
        this.creat_count = creat_count;
    }
    
    public Date getCreated_at()
    {
        return created_at;
    }
    
    public void setCreated_at(Date created_at)
    {
        this.created_at = created_at;
    }
}
