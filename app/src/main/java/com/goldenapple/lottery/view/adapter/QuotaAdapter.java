package com.goldenapple.lottery.view.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.data.QuotaBean;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2018/7/12.
 */

public class QuotaAdapter extends BaseAdapter
{
    private ArrayList<QuotaBean> data;
    private HashMap<String, String> resultMap = new HashMap<>();
    
    public void setData(ArrayList<QuotaBean> data)
    {
        this.data = data;
        notifyDataSetChanged();
    }
    
    public HashMap<String, String> getResultMap()
    {
        return resultMap;
    }

    public ArrayList<QuotaBean> getData() {
        return data;
    }

    @Override
    public int getCount()
    {
        return data == null ? 0 : data.size();
    }
    
    @Override
    public Object getItem(int position)
    {
        if (data == null)
            return null;
        if (position >= 0 && position < data.size())
            return data.get(position);
        return null;
    }
    
    @Override
    public long getItemId(int position)
    {
        return position;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        QuotaBean quotaBean=data.get(position);

        if (convertView == null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quota, parent, false);
            viewHolder = new ViewHolder(convertView);
            viewHolder.result.setTag(position);
            viewHolder.result.addTextChangedListener(new MyTextWatcher(viewHolder)
            {
                @Override
                public void afterTextChanged(Editable s, ViewHolder holder)
                {
                    int pos = (int) viewHolder.result.getTag();
                    resultMap.put(String.valueOf(data.get(pos).getQuota()), s.toString());

                    quotaBean.setResult(s.toString());

                    data.set(position,quotaBean);
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2){
                    //这里需要注意，必须先判断mEtEndTimeRepeatTimes.getText()是否为空才能使用Integer.parseInt，否则会报异常。
                    if((  viewHolder.result.getText()!=null) &&
                            !("".equals(  viewHolder.result.getText().toString()))){
                        if(Double.parseDouble(String.valueOf( viewHolder.result.getText())) > data.get(position).getMax()){
                            viewHolder.result.setText(String.valueOf(data.get(position).getMax()));
                        }
                    }
                }
            });
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.result.setTag(position);
        }
        
        viewHolder.quota.setText(data.get(position).getQuota() + "");
        viewHolder.max.setText("最大允许（" + data.get(position).getMax() + "）");
        
        return convertView;
    }
    
    class ViewHolder
    {
        @BindView(R.id.quota)
        TextView quota;
        @BindView(R.id.result)
        EditText result;
        @BindView(R.id.max)
        TextView max;
        
        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
            view.setTag(this);
            //result.setTag(position);
        }
    }
    
    private abstract class MyTextWatcher implements TextWatcher
    {
        private ViewHolder mHolder;
        
        public MyTextWatcher(ViewHolder holder)
        {
            this.mHolder = holder;
        }
        
        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {
        }
        
        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3)
        {
        }
        
        @Override
        public void afterTextChanged(Editable s)
        {
            afterTextChanged(s, mHolder);
        }
        
        public abstract void afterTextChanged(Editable s, ViewHolder holder);
    }
}
