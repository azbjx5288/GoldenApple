package com.goldenasia.lottery.fragment;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.OutBoxAdapterBean;
import com.goldenasia.lottery.data.SendBoxCommand;
import com.goldenasia.lottery.data.SendBoxResponse;
import com.goldenasia.lottery.game.PromptManager;
import com.goldenasia.lottery.view.adapter.OutBoxAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2017/2/3.
 * 发件箱
 */

public class OutBoxFragment extends BaseFragment {
    private static final String TAG = OutBoxFragment.class.getSimpleName();
    private int LIST = 0;
    private int DELETE = 1;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.list)
    ListView listView;

    @BindView(R.id.ll_edit)
    RelativeLayout ll_edit;

    private List<OutBoxAdapterBean> list = new ArrayList<OutBoxAdapterBean>();

    private static final int FIRST_PAGE = 1;//服务器分页从1开始
    private int totalCount;
    private int page = FIRST_PAGE;
    private boolean isLoading;

    private OutBoxAdapter adapter;

    private boolean mStateIsEdit = false;
    private boolean mStateIsSelectAll = false;
    @BindView(R.id.select_all)
    Button select_all;
    @BindView(R.id.delete)
    Button delete;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.empty_show)
    RelativeLayout  empty_show;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outbox, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor
                ("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> {
            loadData(false, FIRST_PAGE);
        });
        final int endTrigger = 2; // load more content 2 items before the end
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
               /* if (scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_IDLE) {

                }*/
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getCount() != 0 && list.size() < totalCount && listView.getLastVisiblePosition() >=
                        (listView.getCount() - 1) - endTrigger) {
                    loadData(false, page + 1);
                }
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                OutBoxAdapterBean bean = list.get(position);

                if (mStateIsEdit) {

                    bean.setState(!bean.isState());
                    list.set(position, bean);
                    adapter.setList(list);

                    setSelectAllState();
                } else {
                    BoxDetailsFragment.launch(OutBoxFragment.this, bean.getMsg_id(), "send");
                }
            }
        });
        adapter = new OutBoxAdapter(mStateIsEdit);

        listView.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        page = FIRST_PAGE;
        if (page == FIRST_PAGE) {
            //默认不加载缓存的数据
            loadData(false, FIRST_PAGE);
        }
    }

    @OnClick({android.R.id.home, R.id.edit, R.id.select_all, R.id.delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.edit:
                mStateIsEdit = !mStateIsEdit;

                if(mStateIsEdit){
                    edit.setText("取消");
                    ll_edit.setVisibility(View.VISIBLE);
                }else{
                    edit.setText("编辑");
                    ll_edit.setVisibility(View.GONE);
                }
                adapter.setmStateIsEdit(mStateIsEdit);

                break;
            case R.id.select_all:

                if (mStateIsSelectAll) {
                    mStateIsSelectAll = false;
                    selectAll(mStateIsSelectAll);
                    select_all.setText("全选");
                } else {

                    mStateIsSelectAll = true;
                    selectAll(mStateIsSelectAll);
                    select_all.setText("取消全选");
                }

                break;
            case R.id.delete:
                new AlertDialog.Builder(getActivity()).setMessage("确定要删除邮件吗？").setNegativeButton("是", (dialog, which) -> deleteBox()).setPositiveButton
                        ("否", null).create().show();
                break;

        }
    }

    private void setSelectAllState() {
        int sum = list.size();

        int selectCount = 0;
        for (int i = 0; i < list.size(); i++) {
            OutBoxAdapterBean bean = list.get(i);
            if (bean.isState()) {
                selectCount++;
            }
        }

        if (sum == selectCount) {
            mStateIsSelectAll = true;
            select_all.setText("取消全选");
        } else {
            mStateIsSelectAll = false;
            select_all.setText("全选");
        }
    }

    private void deleteBox() {
        StringBuffer deleteBoxIds = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            OutBoxAdapterBean bean = list.get(i);
            if (bean.isState()) {
                deleteBoxIds.append(bean.getMsg_id()).append(",");
            }
        }

        if ("".equals(deleteBoxIds.toString())) {
            showToast("请选择要删除的选项");
            return;
        }else{
            deleteBoxIds.deleteCharAt(deleteBoxIds.length() - 1);
        }

        SendBoxCommand command = new SendBoxCommand();
        command.setOp("delete");
        command.setDeleteItems(deleteBoxIds.toString());

        executeCommand(command, restCallback, DELETE);
    }

    private void selectAll(boolean stateIsSelectAll) {
        List<OutBoxAdapterBean> newList = new ArrayList<OutBoxAdapterBean>();
        for (OutBoxAdapterBean bean : list) {
            bean.setState(stateIsSelectAll);
            newList.add(bean);
        }
        list = newList;
        adapter.setList(list);
    }

    private void loadData(boolean withCache, int page) {
        if (isLoading) {
            return;
        }
        this.page = page;
        SendBoxCommand command = new SendBoxCommand();
        command.setCurPage(page);

        TypeToken typeToken = new TypeToken<RestResponse<SendBoxResponse>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, LIST, this);
    }

    private void parseSendBoxToReceiveBox(SendBoxResponse sendBoxResponse) {

        List<OutBoxAdapterBean> newlist = new ArrayList<OutBoxAdapterBean>();
        for(int i=0;i<sendBoxResponse.getList().size();i++){
            SendBoxResponse.ListBean sendBoxListBean=sendBoxResponse.getList().get(i);

            OutBoxAdapterBean outBoxAdapterBean=new OutBoxAdapterBean();

            outBoxAdapterBean.setMsg_id(sendBoxListBean.getMsg_id());
            outBoxAdapterBean.setTitle(sendBoxListBean.getTitle());
            outBoxAdapterBean.setCreate_time(sendBoxListBean.getCreate_time());

            List<SendBoxResponse.ListBean.TargetsBean>  targetsBeanList=sendBoxListBean.getTargets();

            StringBuffer from_username=new StringBuffer();
            for(int j=0;j<targetsBeanList.size();j++){
                SendBoxResponse.ListBean.TargetsBean targetsBean=targetsBeanList.get(j);
                from_username.append(targetsBean.getUsername()).append(",");
            }
            if ("".equals(from_username.toString())) {
                return;
            }else{
                from_username.deleteCharAt(from_username.length() - 1);
            }

            outBoxAdapterBean.setFrom_username(from_username.toString());
            newlist.add(outBoxAdapterBean);
        }
        list.addAll(newlist);
    }

    private RestCallback restCallback = new RestCallback<SendBoxResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<SendBoxResponse> response) {

            if (request.getId() == LIST) {
                SendBoxResponse sendBoxResponse = (SendBoxResponse) (response.getData());

                totalCount =Integer.parseInt(sendBoxResponse.getCount());//sendBoxResponse.getList().size(); // Integer.parseInt(receiveBoxResponse.getCount());
                if (page == FIRST_PAGE) {
                    list.clear();
                }

                parseSendBoxToReceiveBox(sendBoxResponse);

                if(list.size()==0){
                    refreshLayout.setVisibility(View.GONE);
                    edit.setVisibility(View.GONE);
                    empty_show.setVisibility(View.VISIBLE);
                }else{
                    empty_show.setVisibility(View.GONE);
                    refreshLayout.setVisibility(View.VISIBLE);
                    edit.setVisibility(View.VISIBLE);
                }


                adapter.setList(list);

                if(mStateIsEdit){
                    setSelectAllState();
                }

            } else if (request.getId() == DELETE) {
                showToast(response.getErrStr());
                loadData(false, FIRST_PAGE);
            }

            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            refreshLayout.setRefreshing(state == RestRequest.RUNNING);
            isLoading = state == RestRequest.RUNNING;
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}