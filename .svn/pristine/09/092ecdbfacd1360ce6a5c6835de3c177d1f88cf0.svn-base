package com.goldenasia.lottery.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenasia.lottery.R;
import com.goldenasia.lottery.app.BaseFragment;
import com.goldenasia.lottery.app.FragmentLauncher;
import com.goldenasia.lottery.base.net.RestCallback;
import com.goldenasia.lottery.base.net.RestRequest;
import com.goldenasia.lottery.base.net.RestRequestManager;
import com.goldenasia.lottery.base.net.RestResponse;
import com.goldenasia.lottery.component.CustomDialog;
import com.goldenasia.lottery.data.ReceiveBoxDetailCommand;
import com.goldenasia.lottery.data.ReceiveBoxDetailResponse;
import com.goldenasia.lottery.game.PromptManager;
import com.google.gson.reflect.TypeToken;

import butterknife.BindView;

/**
 * 收件箱  发件箱的详情页
 * Created by gan on 2017/11/20.
 */
public class BoxDetailsFragment extends BaseFragment {

    @BindView(R.id.user_name)
    TextView user_name;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.content)
    TextView content;
    private String msg_id;
    private String msgType;

    public static void launch(BaseFragment fragment, String msg_id, String msgType) {
        Bundle bundle = new Bundle();
        bundle.putString("msg_id", msg_id);
        bundle.putString("msgType", msgType);
        FragmentLauncher.launch(fragment.getActivity(), BoxDetailsFragment.class, bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        msg_id = getArguments().getString("msg_id");
        msgType = getArguments().getString("msgType");//receive:收件箱，send:发件箱
        if ("receive".equals(msgType)) {
            return inflateView(inflater, container, "收件箱", R.layout.box_details);
        } else {
            return inflateView(inflater, container, "发件箱", R.layout.box_details);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loadDate();
    }

    private void loadDate() {
        ReceiveBoxDetailCommand command = new ReceiveBoxDetailCommand();
        command.setMsg_id(Integer.parseInt(msg_id));
        command.setMsgType(msgType);
        TypeToken typeToken = new TypeToken<RestResponse<ReceiveBoxDetailResponse>>() {
        };
        RestRequestManager.executeCommand(getActivity(), command, typeToken, restCallback, 0, this);

    }

    private RestCallback restCallback = new RestCallback<ReceiveBoxDetailResponse>() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse<ReceiveBoxDetailResponse> response) {
            ReceiveBoxDetailResponse detail = response.getData();

            ReceiveBoxDetailResponse.MessageBean messageBean = detail.getMessage();

            user_name.setText(detail.getUsername());
            title.setText(messageBean.getTitle());
            time.setText(messageBean.getCreate_time());
            content.setText(messageBean.getContent());
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 7003) {
                Toast.makeText(getActivity(), "正在更新服务器请稍等", Toast.LENGTH_LONG).show();
                return true;
            } else if (errCode == 7006) {
                CustomDialog dialog = PromptManager.showCustomDialog(getActivity(), "重新登录", errDesc, "重新登录", errCode);
                dialog.setCancelable(false);
                dialog.show();
                return true;
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
            if (state == RestRequest.RUNNING) {
                showProgress("加载中...");
            } else {
                hideProgress();
            }
        }
    };
}
