package com.goldenasia.lottery.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.data.OutBoxAdapterBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Gan on 2017/11/21.
 *  发件箱  Adapter
 */

public class OutBoxAdapter extends BaseAdapter {

    private List<OutBoxAdapterBean> list;
    private boolean mStateIsEdit=false;

    public OutBoxAdapter(boolean stateIsEdit) {
        mStateIsEdit=stateIsEdit;
    }

    public void setList(List list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setmStateIsEdit(boolean mStateIsEdit) {
        this.mStateIsEdit = mStateIsEdit;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.inbox_list_item, parent, false);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OutBoxAdapterBean bean = list.get(position);
        holder.from_username.setText(bean.getFrom_username());
        holder.content.setText(bean.getTitle());
        holder.time.setText(bean.getCreate_time());

        if(mStateIsEdit){
            holder.check_box.setVisibility(View.VISIBLE);
            if(bean.isState()){
                holder.check_box.setChecked(true);
            }else{
                holder.check_box.setChecked(false);
            }
        }else{
            holder.check_box.setVisibility(View.GONE);
        }
        return convertView;
    }


    static class ViewHolder {
        @BindView(R.id.from_username)
        TextView from_username;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.check_box)
        CheckBox check_box;

        public ViewHolder(View convertView) {
            ButterKnife.bind(this, convertView);
            convertView.setTag(this);
        }
    }

}
