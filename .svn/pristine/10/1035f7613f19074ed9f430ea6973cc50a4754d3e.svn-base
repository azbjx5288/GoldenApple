package com.goldenapple.lottery.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.data.Lottery;
import com.goldenapple.lottery.util.UiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Sakura on 2018/6/14.
 */

public class HistoryBetPopupWindowAdapter extends BaseAdapter
{
    private static final String TAG = HistoryBetPopupWindowAdapter.class.getSimpleName();
    
    private int currentItem = -1;
    private Context context;
    private List<Lottery> datalist;
    private OnTransferClickListener onTransferClickListener;
    
    public HistoryBetPopupWindowAdapter(Context context, List<Lottery> datalist) {
        this.context = context;
        this.datalist = datalist;
    }
    public HistoryBetPopupWindowAdapter(Context context) {
        this.context = context;
    }
    
    public void setOnIssueNoClickListener(OnTransferClickListener onTransferClickListener) {
        this.onTransferClickListener = onTransferClickListener;
    }
    
    public void setData( List<Lottery> datalist,int lowerMemberUserId) {
        this.datalist = datalist;
        this.currentItem=lowerMemberUserId;
        //        notifyDataSetChanged();
    }
    
    @Override
    public int getCount() {
        return datalist != null || datalist.size() >= 0 ? datalist.size() : 0;
    }
    
    @Override
    public Object getItem(int position) {
        return null;
    }
    
    @Override
    public long getItemId(int position) {
        return 0;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holderView;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.spiner_item_layout, null);
            holderView = new ViewHolder(convertView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        // 记录的点击位置来设置"对应Item"的可见性（在list依次加载列表数据时，每加载一个时都看一下是不是需改变可见性的那一条）
        Lottery lottery = datalist.get(position);
        if (currentItem == lottery.getId()) {
            holderView.layout.setBackgroundDrawable(UiUtils.getDrawable(context,R.drawable.shape_fit_button));//Color.parseColor("#16A085")  //shape_history_popup_menu_checked
            holderView.textView.setTextColor(UiUtils.getColor(context,R.color.white));
        } else {
            holderView.layout.setBackgroundDrawable(UiUtils.getDrawable(context,R.drawable.shape_fit_button_unchecked));//Color.parseColor("#e2e2e2")
            holderView.textView.setTextColor(Color.parseColor("#808080"));
        }
        holderView.textView.setTag(position);
        holderView.textView.setText(lottery.getName() + "");
        holderView.textView.setPadding(0, 15, 0, 15);
        
        holderView.textView.setOnClickListener((View view) -> {
            // 用 currentItem 记录点击位置
            int tag = (Integer) view.getTag();
            //            if (tag != currentItem) {
            //                currentItem = tag;
            if (onTransferClickListener != null) {
                onTransferClickListener.onTransferListener(datalist.get(tag));
            }
            notifyDataSetChanged(); // 必须有的一步
            //            }
        });
        
        return convertView;
    }
    
    static class ViewHolder {
        
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.textView)
        TextView textView;
        
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
            view.setTag(this);
        }
    }
    
    /**
     * 选中监听器
     */
    public interface OnTransferClickListener {
        void onTransferListener( Lottery lottery);
    }
}
