package com.goldenapple.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.app.FragmentLauncher;
import com.goldenapple.lottery.app.LazyBaseFragment;
import com.goldenapple.lottery.base.net.RestCallback;
import com.goldenapple.lottery.base.net.RestRequest;
import com.goldenapple.lottery.base.net.RestRequestManager;
import com.goldenapple.lottery.base.net.RestResponse;
import com.goldenapple.lottery.data.UserListBean;
import com.goldenapple.lottery.data.UserListCommand;
import com.goldenapple.lottery.pattern.VersionChecker;
import com.goldenapple.lottery.view.adapter.UserListAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 用户列表
 */

public class UserListFragment extends LazyBaseFragment implements UserListAdapter.OnManageListner{
    private static final String TAG = UserListFragment.class.getSimpleName();

    private static final int ID_USER_LIST = 1;

    @BindView(R.id.edit_name)
    EditText edit_name;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.btn_search)
    Button btn_search;

    @BindView(R.id.no_lowermember)
    RelativeLayout noLowermember;

    private UserListAdapter adapter;
    private List<UserListBean> mItems;

    private boolean isLoading;

    public static void launch(BaseFragment fragment) {
        FragmentLauncher.launch(fragment.getActivity(), UserListFragment.class);
    }

    public static void launch(BaseFragment fragment, Boolean isHiddenEditImage) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isHiddenEditImage",isHiddenEditImage);
        FragmentLauncher.launch(fragment.getActivity(), UserListFragment.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, false, "用户列表", R.layout.fragment_user_list, true, true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        boolean isHiddenEditImage=getArguments().getBoolean("isHiddenEditImage");
        adapter = new UserListAdapter(isHiddenEditImage);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ff0000"), Color.parseColor("#00ff00"), Color.parseColor("#0000ff"), Color.parseColor("#f234ab"));
        refreshLayout.setOnRefreshListener(() -> loadUserList());

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView arg0, int arg1) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        adapter.setOnManageListner(this);
        listView.setAdapter(adapter);
        refreshLayout.setRefreshing(false);
        isLoading = false;
        loadUserList();
    }
    private void loadUserList() {
        if (isLoading) {
            return;
        }
        UserListCommand command = new UserListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<UserListBean>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, ID_USER_LIST, this);
        restRequest.execute();
    }
    @OnClick({R.id.edit_name, R.id.btn_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                searchLower();
                break;
            default:
                break;
        }
    }

    private void searchLower() {
        String searchInfo = edit_name.getText().toString();
        if (TextUtils.isEmpty(searchInfo)) {
            adapter.setData(mItems);
            return;
        }

        List<UserListBean> findList = new ArrayList<UserListBean>();
        for (Iterator<UserListBean> iterator = mItems.iterator(); iterator.hasNext(); ) {
            UserListBean nextMember = iterator.next();
            if (nextMember.getUsername().contains(searchInfo))
                findList.add(nextMember);
        }
        adapter.setData(findList);
    }

    //没有下级的情况
    private void noLowerMemberShowHints() {
        refreshLayout.setVisibility(View.GONE);
        noLowermember.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEdit(int position)
    {
//        if (items != null && items.size() > position&&items.get(position) instanceof LowerMember)
//        {
//            Bundle bundle = new Bundle();
//            bundle.putString("openType","edit");
//            bundle.putInt("userID",((LowerMember) items.get(position)).getUserId());
//            launchFragmentForResult(LowerRebateSetting.class, bundle, 1);
//        }
        new VersionChecker(this).startCheck(true);
    }

    @Override
    public void onDetal(int position)
    {
        //该用户下的下级
        UserListFragment.launch(UserListFragment.this,true);
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            if (request.getId() == ID_USER_LIST) {
                mItems=(ArrayList<UserListBean>)  response.getData();
                if(mItems.size()>0){
                    adapter.setData(mItems);
                }else{
                    noLowerMemberShowHints();
                }
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if(errCode == 3004 || errCode == 2016){
                signOutDialog(getActivity(),errCode);
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (request.getId() == ID_USER_LIST){
                refreshLayout.setRefreshing(state == RestRequest.RUNNING);
                isLoading = state == RestRequest.RUNNING;
            }
        }
    };
}