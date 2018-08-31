package com.goldenapple.lottery.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.base.net.JsonString;
import com.goldenapple.lottery.base.net.RestCallback;
import com.goldenapple.lottery.base.net.RestRequest;
import com.goldenapple.lottery.base.net.RestRequestManager;
import com.goldenapple.lottery.base.net.RestResponse;
import com.goldenapple.lottery.data.GetUserLetterInfoCommand;
import com.goldenapple.lottery.data.SendMsgCommand;
import com.goldenapple.lottery.data.UserListBean;
import com.goldenapple.lottery.data.UserListCommand;
import com.goldenapple.lottery.view.CustomScrollView;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class WriteEmailFragment extends BaseFragment implements RadioGroup.OnCheckedChangeListener{

    private  final int TRACE_SENDMSG_COMMAND = 1;
    private  final int TRACE_LOWER_ID = 2;
    private  final int GETUSERLETTER_INFO_COMMAND=3;

    @BindView(R.id.manner_radiogroup)
    RadioGroup mannerRadiogroup;
    @BindView(R.id.ownership)
    RadioButton ownership;
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
    private  boolean  mIsTopAgent=false;//是否是总代

    private  int  mUserType=1;//1:上级2:所有下级3:单一下级
    private  int  mReceiver;//1:上级2:所有下级3:单一下级

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflateView(inflater, container, "发信息", R.layout.fragment_writeemail,true,true);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mannerRadiogroup.setOnCheckedChangeListener(this);

        UserListCommand command = new UserListCommand();
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<UserListBean>>>() {};
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, TRACE_LOWER_ID, this);
        restRequest.execute();

        GetUserLetterInfoCommand command2 = new GetUserLetterInfoCommand();
        executeCommand(command2, restCallback, GETUSERLETTER_INFO_COMMAND);;
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
                mUserType=2;
                send_member.setVisibility(View.GONE);
                break;
            case R.id.follower:
                mUserType=3;
                send_member.setVisibility(View.VISIBLE);
                break;
            case R.id.ownership:
                mUserType=1;
                send_member.setVisibility(View.GONE);
                break;
        }
    }
    @OnClick(R.id.add_user)
    public void addUser() {
        spinner.performClick();
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
                if (title.length()>20) {
                    showToast("标题最多20个字符", Toast.LENGTH_SHORT);
                    return;
                }
                if (multiline.isEmpty()) {
                    showToast("请输入邮件内容", Toast.LENGTH_SHORT);
                    return;
                }

                if(mLowerList.size()==0){
                    if(mUserType==2||mUserType==3){//1:上级2:所有下级3:单一下级)
                        showToast("没有收信人", Toast.LENGTH_SHORT);
                        return;
                    }
                }

                sendMsgCommand.setTitle(title);
                sendMsgCommand.setContent(multiline);
                sendMsgCommand.setUser_type(mUserType);
                if(mUserType==3){
                    sendMsgCommand.setReceiver(mReceiver);
                }
//                submit.setEnabled(false);
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
//                        lowerNameList.add("请选择某一个下级");
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
//                    showToast(response.getError());
                    tipDialog(response.getError());
                    break;
                case GETUSERLETTER_INFO_COMMAND:
                    String jsonString= ((JsonString) response.getData()).getJson();
                    try {
                        JSONObject jsonObject = new JSONObject(jsonString);
                        mIsTopAgent=jsonObject.getBoolean("is_top_agent");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(mIsTopAgent){
                        ownership.setVisibility(View.GONE);
                    }else {
                        ownership.setVisibility(View.VISIBLE);
                    }
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