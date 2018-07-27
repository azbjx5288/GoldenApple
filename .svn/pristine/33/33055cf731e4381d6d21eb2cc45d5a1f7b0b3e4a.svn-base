package com.goldenasia.lottery.data;

import com.android.volley.Request;
import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by Gan on 2017/11/17.
 * 4.5.1	发件箱
 */
@RequestConfig(api = "?c=user&a=sendBox", method = Request.Method.GET)
public class SendBoxCommand {

    private  int curPage=1;//当前页码，默认=1
    private  int pageSize=10;//每页记录数，默认=10
    private String op;//删除标志，删除时=delete
    private String deleteItems;//示例值	1,2,3	删除记录id，多条记录用英文逗号分隔

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }


    public String getDeleteItems() {
        return deleteItems;
    }

    public void setDeleteItems(String deleteItems) {
        this.deleteItems = deleteItems;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }
}
