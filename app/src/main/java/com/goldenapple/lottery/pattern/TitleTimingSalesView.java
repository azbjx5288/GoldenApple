package com.goldenapple.lottery.pattern;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.GoldenAppleApp;
import com.goldenapple.lottery.base.net.RestCallback;
import com.goldenapple.lottery.base.net.RestRequest;
import com.goldenapple.lottery.base.net.RestRequestManager;
import com.goldenapple.lottery.base.net.RestResponse;
import com.goldenapple.lottery.component.CountdownView;
import com.goldenapple.lottery.component.CustomDialog;
import com.goldenapple.lottery.data.IssueInfo;
import com.goldenapple.lottery.data.IssueInfoCommand;
import com.goldenapple.lottery.data.Lottery;
import com.goldenapple.lottery.game.PromptManager;
import com.goldenapple.lottery.material.ConstantInformation;
import com.goldenapple.lottery.material.RecordTime;
import com.goldenapple.lottery.user.UserCentre;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.goldenapple.lottery.material.ConstantInformation.ONE_HOUR;
import static com.goldenapple.lottery.material.ConstantInformation.ONE_MINUTE;
import static com.goldenapple.lottery.material.ConstantInformation.ONE_SECOND;

/**
 * 销售倒计时
 * Created by ACE-PC on 2016/2/5.
 */
public class TitleTimingSalesView {

    private static final long TRACK_TIME_INTERVAL_FAST = 8;
    private static final long TRACK_TIME_INTERVAL_SLOW = 50;
    private static final int ISSUE_TRACE_ID = 2;

    private Activity activity;
    private View view;
    private TextView salesIssueView;
    private CountdownView salesTimeView;
    private boolean statusflag = true;
    private int n = 0, times = 0;
    private Lottery lottery;
    private String issue = "";
    private IssueInfo issueInfo;
    private OnSalesIssueListener onSalesIssueListener;
    private DateFormat df = new SimpleDateFormat("yyyyMMdd");

    public TitleTimingSalesView(Activity activity, View view, Lottery lottery) {
        this.view = view;
        this.activity = activity;
        this.lottery = lottery;
        create();
    }

    public TitleTimingSalesView(View view) {
        this.view = view;
        create();
    }

    private Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public IssueInfo getIssueInfo() {
        return issueInfo;
    }

    public void setIssueInfo(IssueInfo issueInfo) {
        this.issueInfo = issueInfo;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public void setOnSalesIssueListener(OnSalesIssueListener onSalesIssueListener) {
        this.onSalesIssueListener = onSalesIssueListener;
    }

    private void create() {
        salesIssueView = view.findViewById(R.id.timing_sales_issue_label);
        salesTimeView = view.findViewById(R.id.timing_sales_time);
        setListener();
    }

    /**
     * 设置监听
     */
    private void setListener() {
        salesTimeView.setOnCountdownEndListener((cv) -> {
            //当销售结束后 开启开奖倒计时
            if (statusflag) {
                setOpenLottery(Interval());
            } else {
                GetSales();
            }
        });
        salesTimeView.start(0);
        GetAwardPeriod();
    }

    public void stop() {
        if (salesTimeView != null)
            salesTimeView.stop();
        closeRequest();
    }

    /**
     * 初使化开奖与销售信息
     *
     * @param issueInfo
     */
    private void setUpdateSales(IssueInfo issueInfo) {
        int hourSales = 0, minuteSales = 0, secondSales = 0;
        RecordTime salesTime = ConstantInformation.getLasttime(issueInfo.getServer_time(), issueInfo.getEnd_time());
        if (salesTime != null) {
            statusflag = true;
            hourSales = salesTime.getHour() > 0 ? salesTime.getHour() : 0;
            minuteSales = salesTime.getMinute() > 0 ? salesTime.getMinute() : 0;
            secondSales = salesTime.getSecond() > 0 ? salesTime.getSecond() : 0;
        }
        setUpdateSalesLottery(issueInfo.getIssue(), hourSales * ONE_HOUR + minuteSales * ONE_MINUTE + secondSales * ONE_SECOND);
    }

    /**
     * 更新销售信息
     */
    private void setUpdateSalesLottery(String salesIssue, long time) {
        Date curDate = new Date(System.currentTimeMillis());
        statusflag = true;
        setIssue(salesIssue);
        salesTimeView.start(time);
        salesIssueView.setText(salesIssue != null && salesIssue.length() > 0 ? salesIssue : df.format(curDate) + "-" + "**** 期");
        if (onSalesIssueListener != null) {
            onSalesIssueListener.onSalesIssue(salesIssue != null && salesIssue.length() > 0 ? salesIssue : "", times > 0);
            times++;
        }
    }

    /**
     * 销售完成后 等待开奖倒计时触发
     */
    private void setOpenLottery(long second) {
        statusflag = false;
        salesTimeView.start(0 * ONE_HOUR + 0 * ONE_MINUTE + second * ONE_SECOND);
    }

    /**
     * 获取销售
     */
    private void GetSales() {
        GetAwardPeriod();
        if (getIssueInfo() != null) {
            n = 0;
            setUpdateSales(getIssueInfo());
        } else {
            if (n >= 3) {
                salesTimeView.stop();
                return;
            }
            setOpenLottery(Interval());
            n++;
            return;
        }
    }

    private long Interval() {
        long interval = 0;
        switch (getLottery().getId()) {
            case 13://苹果分分彩
            case 16://苹果三分彩
            case 28://苹果五分彩
            case 26://苹果秒秒彩
            case 14://苹果11选5
            case 17://苹果快三分分彩
                interval = TRACK_TIME_INTERVAL_FAST;
                break;
            default:
                interval = TRACK_TIME_INTERVAL_SLOW;
                break;
        }
        return interval;
    }

    /**
     * 加截开奖信息  @param lottery
     */
    private void GetAwardPeriod() {
        IssueInfoCommand command = new IssueInfoCommand();
        command.setLotteryId(lottery.getId());
        TypeToken typeToken = new TypeToken<RestResponse<IssueInfo>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(activity, command, typeToken, restCallback,
                ISSUE_TRACE_ID, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ISSUE_TRACE_ID) {
                setIssueInfo((IssueInfo) response.getData());
                setUpdateSales(getIssueInfo());
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004) {
                CustomDialog dialog = PromptManager.showCustomDialog(activity, "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            } else {
                Toast.makeText(activity, errDesc, Toast.LENGTH_SHORT).show();
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };

    private void closeRequest() {
        RestRequestManager.cancelAll(this);
    }

    public interface OnSalesIssueListener {
        void onSalesIssue(String issue, boolean status);
    }
}
