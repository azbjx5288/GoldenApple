package com.goldenasia.lottery.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import com.goldenasia.lottery.data.LowerTips;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ACE-PC on 2017/2/14.
 */

public class ContactCloudEditTextImpl extends CloudEditText {
    private List<LowerTips> lowerList=new ArrayList<>();

    public ContactCloudEditTextImpl(Context context) {
        super(context);
    }

    public ContactCloudEditTextImpl(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ContactCloudEditTextImpl(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean checkInputSpan(String showText, String returnText) {
        boolean result =true;// isUserName(returnText);
        /* if (!result) {
            Toast.makeText(getContext(), "请输入用户名(多个用户用回车分隔)", Toast.LENGTH_SHORT).show();
        }*/

        LowerTips lowerTips=null;
        for(LowerTips lower:lowerList){
            if(lower.getUsername().equals(returnText)){
                lowerTips=lower;
            }
        }
        if(lowerTips!=null){
            int indexExist=getAllReturnStringList().indexOf(lowerTips);
            if(indexExist!=-1){
                Toast.makeText(getContext(), "该用户已经被选择", Toast.LENGTH_SHORT).show();
            }else{
                int index = lowerList.indexOf(lowerTips);
                if (index != -1) {
                    return result;
                }else{
                    Toast.makeText(getContext(), "该用户不存在", Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            Toast.makeText(getContext(), "该用户不存在", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * 校验指定数据
     *
     * @return
     */
    public void setFindInput(List<LowerTips> lowerList) {
        this.lowerList = lowerList;
    }

   /* private boolean isUserName(String text) {
        String str = "^[a-z]\\w{4,9}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(text);
        return m.matches();
    }*/
}