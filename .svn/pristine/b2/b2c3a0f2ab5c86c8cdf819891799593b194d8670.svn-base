package com.goldenapple.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.app.GoldenAppleApp;
import com.goldenapple.lottery.data.UserInfo;

import butterknife.OnClick;

/**
 * Created by Sakura on 2018/6/15.
 */

public class FragmentHistory extends BaseFragment
{
    private static final String TAG = FragmentHistory.class.getSimpleName();
    
    /*@BindView(R.id.game_reports)
    RelativeLayout game_reports;
    
    @BindView(R.id.game_reports_view)
    View game_reports_view;*/
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "游戏记录", R.layout.fragment_history,false,true);
    }
    
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        UserInfo userInfo = GoldenAppleApp.getUserCentre().getUserInfo();
        
        /*if (userInfo.getLevel() == 10) {  //非代理账号显示
            game_reports.setVisibility(View.VISIBLE);
            game_reports_view.setVisibility(View.VISIBLE);
        } else {
            game_reports.setVisibility(View.GONE);
            game_reports_view.setVisibility(View.GONE);
        }*/
    }
    
    @OnClick({R.id.bet_history, R.id.trace_history/*, R.id.balance_details,R.id.ga_history,R.id.game_reports*/})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bet_history:
                BetOrTraceListTagFragment.launchBet(this);
                break;
            case R.id.trace_history:
                BetOrTraceListTagFragment.launchTrace(this);
                break;
            /*case R.id.balance_details://资金明细
                launchFragment(BalanceTableFragment.class);
                break;
            
            case R.id.ga_history://GA记录
                launchFragment(FragmentHistoryGA.class);
                break;
            case R.id.game_reports://游戏报表
                GameReportMainFragment.launch(this);
                break;*/
            default:
                break;
        }
    }
    
}
