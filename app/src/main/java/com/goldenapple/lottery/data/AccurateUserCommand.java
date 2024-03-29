package com.goldenapple.lottery.data;

import com.android.volley.Request;
import com.goldenapple.lottery.base.net.RequestConfig;

@RequestConfig(api = "service?packet=User&action=AccurateUser", method = Request.Method.POST)
public class AccurateUserCommand extends CommonAttribute
{
    private float fb_single;
    private float fb_all;
    private float ag_percent;
    private float ga_percent;
    private int is_agent;
    private String username;
    private String password;
    private String nickname;
    private String series_prize_group_json;
    private String agent_prize_set_quota;
    
    public float getFb_single()
    {
        return fb_single;
    }
    
    public void setFb_single(float fb_single)
    {
        this.fb_single = fb_single;
    }
    
    public float getFb_all()
    {
        return fb_all;
    }
    
    public void setFb_all(float fb_all)
    {
        this.fb_all = fb_all;
    }
    
    public float getAg_percent()
    {
        return ag_percent;
    }
    
    public void setAg_percent(float ag_percent)
    {
        this.ag_percent = ag_percent;
    }
    
    public float getGa_percent()
    {
        return ga_percent;
    }
    
    public void setGa_percent(float ga_percent)
    {
        this.ga_percent = ga_percent;
    }
    
    public int getIs_agent()
    {
        return is_agent;
    }
    
    public void setIs_agent(int is_agent)
    {
        this.is_agent = is_agent;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getNickname()
    {
        return nickname;
    }
    
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }
    
    public String getSeries_prize_group_json()
    {
        return series_prize_group_json;
    }
    
    public void setSeries_prize_group_json(String series_prize_group_json)
    {
        this.series_prize_group_json = series_prize_group_json;
    }
    
    public String getAgent_prize_set_quota()
    {
        return agent_prize_set_quota;
    }
    
    public void setAgent_prize_set_quota(String agent_prize_set_quota)
    {
        this.agent_prize_set_quota = agent_prize_set_quota;
    }
}
