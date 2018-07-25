package com.goldenapple.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.base.net.RestCallback;
import com.goldenapple.lottery.base.net.RestRequest;
import com.goldenapple.lottery.base.net.RestRequestManager;
import com.goldenapple.lottery.base.net.RestResponse;
import com.goldenapple.lottery.data.SendMsgCommand;
import com.goldenapple.lottery.data.UserListBean;
import com.goldenapple.lottery.data.UserListCommand;
import com.goldenapple.lottery.view.CustomScrollView;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteEmailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    private static final int TRACE_SENDMSG_COMMAND = 1;
    private static final int TRACE_LOWER_ID = 2;

    @BindView(R.id.manner_radiogroup)
    RadioGroup mannerRadiogroup;
    @BindView(R.id.addressee)
    Spinner spinner ;
    @BindView(R.id.add_user)
    ImageView addUser;
    @BindView(R.id.title_text)
    EditText titleText;
    @BindView(R.id.multiline_text)
    EditText multilineText;
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.send_member)
    LinearLayout send_member;
    @BindView(R.id.submit)
    Button submit;

    private List<UserListBean> mLowerList = new ArrayList<>();
    private  ArrayList<String> lowerNameList;


    private  int  mUserType=1;//1:上级2:所有下级3:单一下级
    private  int  mReceiver;//1:上级2:所有下级3:单一下级

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "写邮件", R.layout.fragment_writeemail,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mannerRadiogroup.setOnCheckedChangeListener(this);


        UserListCommand command = new UserListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<UserListBean>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, TRACE_LOWER_ID, this);
        restRequest.execute();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        getActivity().finish();
        super.onDestroyView();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.prefect:
                mUserType=1;
                send_member.setVisibility(View.GONE);
                break;
            case R.id.follower:
                mUserType=2;
                send_member.setVisibility(View.VISIBLE);
                break;
            case R.id.ownership:
                mUserType=3;
                send_member.setVisibility(View.GONE);
                break;
        }
    }
    @OnClick(R.id.add_user)
    public void addUser() {
//        if(lowerList.size()==0){
//            showToast("请稍等,正在加载数据");
//            return;
//        }
//        hasAddUser=true;
//
//        View view=LayoutInflater.from(getContext()).inflate(R.layout.user_cloud_choose, null, false);
//        userTagCloud=(TagCloudViewEdit)view.findViewById(R.id.tag_cloud_view);
//        userTagCloud.setTags(tags);
//        userTagCloud.setOnTagClickListener(this);
//        userTagCloud.setOnTagClickListener(new TagCloudViewEdit.OnTagClickListener() {
//            @Override
//            public void onTagClick(int position) {
//                bindPositionView(position);
//                LowerTips lowerTips=lowerList.get(position);
//                List<String> textSpan = addresseeText.getAllReturnStringList();
//                int index=textSpan.indexOf(lowerTips.getUsername());
//                if(index!=-1){
//                    showToast("该用户已经被选择");
//                }else{
//                    textSpan.add(textSpan.size(),lowerTips.getUsername());
//                    if (textSpan.size()>0) {
//                        addresseeText.setText("");
//                        for (String str : textSpan) {
//                            addresseeText.addSpan(str, str);
//                        }
//                    }
//                }
//            }
//        });

//        tipDialog("选择用户",view);
    }

    @OnClick({R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                SendMsgCommand sendMsgCommand = new SendMsgCommand();
                String title = titleText.getText().toString();
                String multiline = multilineText.getText().toString();
                if (title.isEmpty()) {
                    showToast("请输入标题", Toast.LENGTH_SHORT);
                    return;
                }
                if (multiline.isEmpty()) {
                    showToast("请输入邮件内容", Toast.LENGTH_SHORT);
                    return;
                }

                sendMsgCommand.setTitle(title);
                sendMsgCommand.setContent(multiline);
                sendMsgCommand.setUser_type(mUserType);
                if(mUserType==3){
                    sendMsgCommand.setReceiver(mReceiver);
                }
                submit.setEnabled(false);
                executeCommand(sendMsgCommand, restCallback, TRACE_SENDMSG_COMMAND);
                break;
        }
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case TRACE_LOWER_ID:
                    if (response != null && response.getData() instanceof ArrayList) {
                        mLowerList=(ArrayList<UserListBean>)  response.getData();

                        lowerNameList= new ArrayList<String>();
                        for (int i = 0; i < mLowerList.size(); i++) {
                            lowerNameList.add(mLowerList.get(i).getUsername());
                        }
                        MyAdapter adapter =  new MyAdapter();
                        spinner.setAdapter(adapter);
                        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view,
                                                       int position, long id) {
                                mReceiver=mLowerList.get(position).getId();
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                            }

                        });

                    }
                    break;
                case TRACE_SENDMSG_COMMAND:
                    submit.setEnabled(true);
                    showToast(response.getError());
                    break;
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

        }
    };

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lowerNameList.size();
        }

        @Override
        public Object getItem(int position) {
            return lowerNameList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view =  LayoutInflater.from(getActivity()).inflate(R.layout.writeemail_spinner_item, null);
            if (mLowerList.get(position).getId() == mReceiver){
                //选中条目的背景色
                view.setBackgroundColor(Color.rgb(26,208,189));
            }
            final TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_name.setText(lowerNameList.get(position));
            //设置按钮的监听事件
            view.setTag(tv_name);
            return view;
        }

    }

}