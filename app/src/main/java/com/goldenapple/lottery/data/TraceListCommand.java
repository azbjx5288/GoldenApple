package com.goldenapple.lottery.data;

import com.goldenapple.lottery.base.net.RequestConfig;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * 追号订单列表
 * Created by Alashi on 2016/1/21.
 */
@RequestConfig(api = "service?packet=Game&action=getTraceList")
public class TraceListCommand extends CommonAttribute{
    @SerializedName("lottery_id")
    private int lotteryId;
    private int page;
    private int pagesize = 20;
    private int status;
    private Date start_time;
    private Date end_time;

    public void setLotteryId(int lotteryId) {
        this.lotteryId = lotteryId;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public Date getStart_time()
    {
        return start_time;
    }
    
    public void setStart_time(Date start_time)
    {
        this.start_time = start_time;
    }
    
    public Date getEnd_time()
    {
        return end_time;
    }
    
    public void setEnd_time(Date end_time)
    {
        this.end_time = end_time;
    }
}
