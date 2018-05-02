package com.goldenapple.lottery.game;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.data.Lottery;
import com.goldenapple.lottery.data.Method;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * pk8 和值(和值 单双大小 810五行) 奇偶 和 上中下
 */

public class Kl8SpecialGame extends Game {

    private  final String TAG = Kl8SpecialGame.class.getSimpleName();

    private Map<String,Boolean> mPickMap=new HashMap<>();//当前选中文名字集合

    private  TextView tv_pick_column_title;
    private  TextView tv_sddds_0;
    private  TextView tv_sddds_1;
    private  TextView tv_sddds_2;
    private  TextView tv_sddds_3;
    private  TextView tv_sddds_4;

    public Kl8SpecialGame(Activity activity, Method method, Lottery lottery)
    {
        super(activity, method, lottery);
    }

    @Override
    public void onInflate()
    {
        if("danshuang".equals(method.getNameEn())){      //单双
            danshuang(this);
        }else if("daxiao810".equals(method.getNameEn())){ //daxiao810"://大小810
            daxiao810(this);
        }else{
            Log.e("ShanDongKuaiLePuKeGame", "onInflate: " + "//" + method.getNameCn() + " " + method.getNameEn() + " public static " +
                    "" + "void " + method.getNameCn() + "(Game game) {}");
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }


    //根据不同玩法返回不同的String数组
    private String[] getGameMethodStringArray() {
        switch (method.getNameEn()) {
            case "danshuang"://单双
                return new String[]{"单","双"};//包选 PKBX;
            case "daxiao810"://daxiao810"://大小810
                return new String[]{"大","810","小"};
            default:
                return new String[]{"单","双"};//包选 PKBX;
        }
    }

    @Override
    public String getWebViewCode()
    {
        StringBuilder stringBuilder = new StringBuilder();

        String[] methodStringArray = getGameMethodStringArray();

        for (int i = 0; i < methodStringArray.length; i++)
        {
            if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                stringBuilder.append("1");
            }
        }
        JsonArray jsonArray = new JsonArray();
        jsonArray.add(stringBuilder.toString());
        return jsonArray.toString();
    }

    @Override
    public String getSubmitCodes()
    {
        StringBuilder stringBuilder = new StringBuilder();
        String[] methodStringArray = getGameMethodStringArray();
        for (int i = 0; i < methodStringArray.length; i++)
        {
            if(mPickMap.containsKey(methodStringArray[i])&&mPickMap.get(methodStringArray[i])) {
                stringBuilder.append(methodStringArray[i]);
                stringBuilder.append("_");
            }
        }
        if(stringBuilder.length()>=2) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }


     class MyOnClickListener implements OnClickListener{

        @Override
        public void onClick(View view) {
            if (view.isSelected())
            {
                view.setSelected(false);
                mPickMap.put(view.getTag().toString(),false);
            } else
            {
                view.setSelected(true);
                mPickMap.put(view.getTag().toString(),true);
            }

            notifyListener();
        }
    }

    /*====================================具体玩法添加开始===========================================================================*/
    //"danshuang"://单双
    public  void danshuang(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("单双");
        tv_sddds_0.setText("单");
        tv_sddds_0.setTag("单");
        tv_sddds_1.setText("双");
        tv_sddds_1.setTag("双");
        tv_sddds_2.setVisibility(View.GONE);
        tv_sddds_3.setVisibility(View.GONE);
        tv_sddds_4.setVisibility(View.GONE);
        addTopLayout(game, view);
    }

    //daxiao810"://大小810
    public  void daxiao810(Game game) {
        View view = LayoutInflater.from(game.getTopLayout().getContext()).inflate(R.layout.pick_column_kl8_special_danshuang, null, false);
        initView(view);

        tv_pick_column_title.setText("大小810");
        tv_sddds_0.setText("大");
        tv_sddds_0.setTag("大");
        tv_sddds_1.setText("810");
        tv_sddds_1.setTag("810");
        tv_sddds_2.setText("小");
        tv_sddds_2.setTag("小");
        tv_sddds_3.setVisibility(View.GONE);
        tv_sddds_4.setVisibility(View.GONE);
        addTopLayout(game, view);
    }

    private void initView(View view) {
        tv_pick_column_title=view.findViewById(R.id.pick_column_title);
        tv_sddds_0=view.findViewById(R.id.sddds_0);
        tv_sddds_1=view.findViewById(R.id.sddds_1);
        tv_sddds_2=view.findViewById(R.id.sddds_2);
        tv_sddds_3=view.findViewById(R.id.sddds_3);
        tv_sddds_4=view.findViewById(R.id.sddds_4);

        tv_sddds_0.setOnClickListener(new MyOnClickListener());
        tv_sddds_1.setOnClickListener(new MyOnClickListener());
        tv_sddds_2.setOnClickListener(new MyOnClickListener());
        tv_sddds_3.setOnClickListener(new MyOnClickListener());
        tv_sddds_4.setOnClickListener(new MyOnClickListener());
    }

    private  void addTopLayout(Game game, View view) {
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
    }


}