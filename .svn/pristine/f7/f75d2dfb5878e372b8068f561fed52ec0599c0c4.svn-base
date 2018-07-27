package com.goldenasia.lottery.data;

import com.goldenasia.lottery.base.net.RequestConfig;

/**
 * Created by ACE-PC on 2017/2/7.
 */
@RequestConfig(api = "?c=user&a=sendMsg", response = String.class)
public class SendMsgCommand {

    private String target="child";
    private String title;
    private String content;
    private String selectChild;
    private int submit=1;

    private String msg_id;

    public void setTarget(String target) {
        this.target = target;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSelectChild(String selectChild) {
        this.selectChild = selectChild;
    }

    public String getTarget() {
        return target;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSelectChild() {
        return selectChild;
    }

    public int getSubmit() {
        return submit;
    }

    public String getMsg_id() {
        return msg_id;
    }

    public void setMsg_id(String msg_id) {
        this.msg_id = msg_id;
    }
}
