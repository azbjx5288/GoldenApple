package com.goldenapple.lottery.app;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.base.net.RestCallback;
import com.goldenapple.lottery.base.net.RestRequest;
import com.goldenapple.lottery.base.net.RestRequestManager;
import com.goldenapple.lottery.base.net.RestResponse;
import com.goldenapple.lottery.data.ReceiveBoxCommand;
import com.goldenapple.lottery.data.ReceiveBoxResponse;
import com.goldenapple.lottery.fragment.FragmentHistory;
import com.goldenapple.lottery.fragment.FragmentHome;
import com.goldenapple.lottery.fragment.FragmentLotteryTrend;
import com.goldenapple.lottery.fragment.FragmentUser;
import com.goldenapple.lottery.material.ConstantInformation;
import com.goldenapple.lottery.view.adapter.TabsPagerAdapter;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;

/**
 * Created on 2016/01/04.
 *
 * @author ACE
 * @功能描述: 界面容器
 */

public class ContainerActivity extends AppCompatActivity {
    private static final String TAG = ContainerActivity.class.getSimpleName();
    private int LIST = 0;
    private List<Integer> iconList = null;
    private List<Fragment> fragmentList = null;
    private List<String> textList = null;
    private FragmentTabHost tabHost;
    private ViewPager mVPager;
    //退出时的时间
    private long exitTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container_fragment);
        tabHost = findViewById(android.R.id.tabhost);
        mVPager = findViewById(R.id.tabpager);
        initView();
        if(ConstantInformation.MESSAGE_COUNT==-1){
            loadReceiveBox();
        }
    }

    private void loadReceiveBox() {
        ReceiveBoxCommand command = new ReceiveBoxCommand();
        command.setPage(1);

        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<ReceiveBoxResponse>>>() {
        };
        RestRequestManager.executeCommand(this, command, typeToken, restCallback, LIST, this);
    }

    private void initView() {
        fragmentList = new ArrayList<>(Arrays.asList(new FragmentHome(), new FragmentLotteryTrend(), /*new BetOrTraceListTagFragment()*/new FragmentHistory(), new FragmentUser()));
        iconList = new ArrayList<>(Arrays.asList(R.drawable.ic_tab_home, R.drawable.ic_tab_classify, R.drawable.ic_tab_discover, R.drawable.ic_tab_me));
        textList = new ArrayList<>(Arrays.asList("购彩大厅", "开奖走势", "游戏记录", "帐号中心"));

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i < fragmentList.size(); i++) {
            tabHost.addTab(tabSpec(i), Fragment.class, null);
        }

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                mVPager.setCurrentItem(Integer.parseInt(tabId));
            }
        });

        mVPager.setOffscreenPageLimit(4);

        mVPager.setAdapter(new TabsPagerAdapter(getSupportFragmentManager(), fragmentList));
        mVPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 创建标题tab 每个选项卡
     *
     * @param index
     * @return
     */
    private TabHost.TabSpec tabSpec(int index) {
        TabHost.TabSpec tabSpec = tabHost.newTabSpec(index + "");
        View tView = getLayoutInflater().inflate(R.layout.nav_tab_item, null);
        ImageView mImgView = tView.findViewById(R.id.tab_image);
        TextView textView = tView.findViewById(R.id.tab_text);
        mImgView.setBackgroundResource(iconList.get(index));
        textView.setText(textList.get(index));
        tabSpec.setIndicator(tView);
        if(index==3){
            mImgViewTag=mImgView;
        }
        return tabSpec;
    }
    ImageView mImgViewTag;
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //对返回键进行监听
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {

            if (request.getId() == LIST) {
                if (request.getId() == 0) {
//                    ReceiveBoxResponse receiveBoxResponse = (ReceiveBoxResponse) (response.getData());
                    int totalCount=1;// //=receiveBoxResponse.getList().size();// Integer.parseInt(receiveBoxResponse.getCount());// 解决服务端 返回数据 有缓存的 问题
                    ConstantInformation.MESSAGE_COUNT=totalCount;

                    if(totalCount>0){
                        totalCount =-1;
                    }else{
                        totalCount =0;
                    }


                    Object  tag=mImgViewTag.getTag();
                    if(tag==null){
                        QBadgeView qBadgeView=new QBadgeView(ContainerActivity.this);
                        qBadgeView.bindTarget(mImgViewTag);
                        qBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
                        qBadgeView.setBadgeNumber(totalCount);
                        mImgViewTag.setTag(qBadgeView);
                    }else{
                        QBadgeView qQBadgeView=(QBadgeView)tag;
                        qQBadgeView.setBadgeNumber(totalCount);
                    }

                }
            }

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };
}
