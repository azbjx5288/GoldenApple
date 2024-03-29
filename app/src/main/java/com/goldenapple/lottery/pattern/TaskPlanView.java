package com.goldenapple.lottery.pattern;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.GoldenAppleApp;
import com.goldenapple.lottery.component.CustomDialog;
import com.goldenapple.lottery.component.DialogLayout;
import com.goldenapple.lottery.component.LastInputEditText;
import com.goldenapple.lottery.component.LimitTextWatcher;
import com.goldenapple.lottery.component.SpinnerEditText;
import com.goldenapple.lottery.data.SpinnerEditBean;
import com.goldenapple.lottery.material.ChaseRuleData;
import com.goldenapple.lottery.material.ShoppingCart;

import java.util.ArrayList;
import java.util.List;

import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

/**
 * Created by ACE-PC on 2016/3/28.
 */
public class TaskPlanView implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private static final String TAG = TaskPlanView.class.getSimpleName();
    private Activity activity;
    //    private RadioRealButtonGroup groupButton;
    private LastInputEditText multiple;
    private LastInputEditText issueNo;
    private LastInputEditText divideIssue;
    private LastInputEditText divideMultiple;
    private LastInputEditText planWay;
    private CheckBox addStop;
    private OnArrangeChangedListener onArrangeChangedListener;
    private int index = 0;
    private ChaseRuleData chaserule;
    //    private Button planSetting;
    private Button confirmPlan;

    public TaskPlanView(Activity activity, View view, int position) {
        this.activity = activity;
        this.chaserule = new ChaseRuleData(0, getMultiple(), getIssueNo(), 10, 5, 10, 5);

        multiple = view.findViewById(R.id.plan_multiple);
        issueNo = view.findViewById(R.id.plan_issueno);
        addStop = view.findViewById(R.id.add_stop);
        addStop.setOnCheckedChangeListener(this);

        multiple.addTextChangedListener(new LimitTextWatcher(multiple, 4));

        issueNo.addTextChangedListener(new LimitTextWatcher(issueNo, 4));

        switch (position) {
            case 0:
                /*groupButton = view.findViewById(R.id.reserveRadioGroup);
                groupButton.setOnPositionChangedListener((RadioRealButton button, int currentPosition, int lastPosition) -> {
                    updateReserveRadio(currentPosition);
                });*/
                break;
            case 1:
                planWay = view.findViewById(R.id.plan_way);
//              planSetting = view.findViewById(R.id.plan_setting);
//              planSetting.setOnClickListener(this);
//                planWay.setRightCompoundDrawable(R.drawable.vector_drawable_arrowdown);
                planWay.addTextChangedListener(new LimitTextWatcher(planWay, 5));

                break;
            case 2:
                divideIssue = view.findViewById(R.id.divide_issue);
                divideMultiple = view.findViewById(R.id.divide_multiple);

                divideIssue.addTextChangedListener(new LimitTextWatcher(divideIssue, 4));
                divideMultiple.addTextChangedListener(new LimitTextWatcher(divideMultiple, 4));
                break;
        }

        GoldenAppleApp.getUserCentre().setStopOnWin(addStop.isChecked());
        confirmPlan = view.findViewById(R.id.confirm_plan);
        confirmPlan.setOnClickListener(this);
    }

    public boolean isStopOnWin() {
        return addStop.isChecked();
    }

    private void setMultiple(String multiple) {
        this.multiple.setText(multiple);
    }

    public Integer getMultiple() {
        if (multiple == null) {
            return 1;
        }
        if (TextUtils.isEmpty(multiple.getText().toString())) {
            setMultiple("1");
            return 1;
        } else {
            if (multiple.getText().toString().matches("^(0+)")) {
                multiple.setText(multiple.getText().toString().replaceAll("^(0+)", "0"));
            }
            return Integer.parseInt(multiple.getText().toString());
        }
    }

    public void setIssueNo(String issueNo) {
        this.issueNo.setText(issueNo);
    }

    public Integer getIssueNo() {
        if (issueNo == null) {
            return 5;
        }
        if (TextUtils.isEmpty(issueNo.getText().toString())) {
            setIssueNo("5");
            return 5;
        } else {
            if (issueNo.getText().toString().matches("^(0+)")) {
                issueNo.setText(issueNo.getText().toString().replaceAll("^(0+)", "0"));
            }
            return Integer.parseInt(issueNo.getText().toString().replaceAll("^(0+)", "0"));
        }
    }

    private void setDivideIssue(String divideIssue) {
        this.divideIssue.setText(divideIssue);
    }

    public Integer getDivideIssue() {
        if (divideIssue == null) {
            return 1;
        }
        if (TextUtils.isEmpty(divideIssue.getText().toString())) {
            setDivideIssue("1");
            return 1;
        } else {
            if (divideIssue.getText().toString().matches("^(0+)")) {
                divideIssue.setText(divideIssue.getText().toString().replaceAll("^(0+)", "0"));
            }
            return Integer.parseInt(divideIssue.getText().toString().replaceAll("^(0+)", "0"));
        }
    }

    private void setDivideMultiple(String divideMultiple) {
        this.divideMultiple.setText(divideMultiple);
    }

    public Integer getDivideMultiple() {
        if (divideMultiple == null) {
            return 1;
        }
        if (TextUtils.isEmpty(divideMultiple.getText().toString())) {
            setDivideMultiple("1");
            return 1;
        } else {
            if (divideMultiple.getText().toString().matches("^(0+)")) {
                divideMultiple.setText(divideMultiple.getText().toString().replaceAll("^(0+)", "0"));
            }
            return Integer.parseInt(divideMultiple.getText().toString().replaceAll("^(0+)", "0"));
        }
    }

    public Integer getPlanWay() {
        if (planWay == null) {
            return 1;
        }
        if (TextUtils.isEmpty(planWay.getText().toString())) {
            setPlanWay("1");
            return 1;
        } else {
            if (planWay.getText().toString().matches("^(0+)")) {
                setPlanWay(planWay.getText().toString().replaceAll("^(0+)", "0"));
            }
            return Integer.parseInt(planWay.getText().toString().replaceAll("^(0+)", "0"));
        }
    }

    public void setPlanWay(String  planWay) {
        this.planWay.setText(planWay);
    }

    public void setOnArrangeChangedListener(OnArrangeChangedListener onArrangeChangedListener) {
        this.onArrangeChangedListener = onArrangeChangedListener;
    }

    private void updateReserveRadio(int position) {
        switch (position) {
            case 0:
                issueNo.setText("" + 5);
                break;
            case 1:
                issueNo.setText("" + 10);
                break;
            case 2:
                issueNo.setText("" + 15);
                break;
            case 3:
                issueNo.setText("" + 20);
                break;
        }
        if (onArrangeChangedListener != null) {
            chaserule.setMultiple(getMultiple());
            onArrangeChangedListener.newArrange(chaserule, addStop.isChecked(), true);
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            /*case R.id.plan_setting:
                PlanSetting planSetView = new PlanSetting(activity);
                planSetView.setOnChaseModeListener((int mode) -> index = mode);
                View view = planSetView.getPlanSetView();
                CustomDialog.Builder builder = new CustomDialog.Builder(activity);
                builder.setContentView(view);
                builder.setTitle("利润方案设置");
                builder.setLayoutSet(DialogLayout.UP_AND_DOWN);
                builder.setPositiveButton("确定", (dialog, which) -> {
                    hideSoftKeyBoard(view);
                    getChaseSetting(view);
                    dialog.dismiss();
                });
                builder.create().show();
                break;*/
            case R.id.confirm_plan:
                if (TextUtils.isEmpty(issueNo.getText().toString()) || issueNo.getText().toString().matches("^(0+)")) {
                    tipDialog("温馨提示", "追号期数不能为空或0", issueNo);
                    return;
                }

                if (TextUtils.isEmpty(multiple.getText().toString()) || multiple.getText().toString().matches("^(0+)")) {
                    tipDialog("温馨提示", "倍数不能为空或0", multiple);
                    return;
                }

                if (onArrangeChangedListener != null) {
                    chaserule.setMultiple(getMultiple());
                    onArrangeChangedListener.newArrange(chaserule, addStop.isChecked(), true);
                }
                break;
        }
    }

    private void getChaseSetting(View view) {
        if (TextUtils.isEmpty(issueNo.getText())) {
            Toast.makeText(activity, "请追号期数", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Integer.parseInt(issueNo.getText().toString()) == 0) {
            Toast.makeText(activity, "追号至少为1期", Toast.LENGTH_SHORT).show();
            return;
        }
        chaserule.setMultiple(Integer.parseInt(multiple.getText().toString()));
        chaserule.setType(index);

        int issue = 0, ratio = 0, rateAgo = 0, rateLater = 0;
        if (index == 0) {
            EditText gainModeText = view.findViewById(R.id.plan_way_0);
            if (TextUtils.isEmpty(gainModeText.getText())) {
                ratio = 0;
            } else {
                ratio = Integer.parseInt(gainModeText.getText().toString());
            }
            chaserule.setGainMode(ratio);
        } else if (index == 1) {
            EditText period = view.findViewById(R.id.plan_way_1_0);
            EditText elementAgo = view.findViewById(R.id.plan_way_1_1);
            EditText elementLater = view.findViewById(R.id.plan_way_1_2);
            if (TextUtils.isEmpty(period.getText())) {
                issue = 0;
            } else {
                issue = Integer.parseInt(period.getText().toString());
            }

            if (TextUtils.isEmpty(elementAgo.getText())) {
                rateAgo = 0;
            } else {
                rateAgo = Integer.parseInt(elementAgo.getText().toString());
            }

            if (TextUtils.isEmpty(elementLater.getText())) {
                rateLater = 0;
            } else {
                rateLater = Integer.parseInt(elementLater.getText().toString());
            }

            chaserule.setIssueGap(issue);
            chaserule.setAgoValue(rateAgo);
            chaserule.setLaterValue(rateLater);

        } else if (index == 2) {//全程累计利润率
            EditText gainModeText = view.findViewById(R.id.plan_way_2);
            if (TextUtils.isEmpty(gainModeText.getText())) {
                ratio = 0;
            } else {
                ratio = Integer.parseInt(gainModeText.getText().toString());
            }
            chaserule.setGainMode(ratio);
        } else if (index == 3) {//计划利润率
            EditText period = view.findViewById(R.id.plan_way_3_0);
            EditText elementAgo = view.findViewById(R.id.plan_way_3_1);
            EditText elementLater = view.findViewById(R.id.plan_way_3_2);

            if (TextUtils.isEmpty(period.getText())) {
                issue = 0;
            } else {
                issue = Integer.parseInt(period.getText().toString());
            }

            if (TextUtils.isEmpty(elementAgo.getText())) {
                rateAgo = 0;
            } else {
                rateAgo = Integer.parseInt(elementAgo.getText().toString());
            }

            if (TextUtils.isEmpty(elementLater.getText())) {
                rateLater = 0;
            } else {
                rateLater = Integer.parseInt(elementLater.getText().toString());
            }
            chaserule.setIssueGap(issue);
            chaserule.setAgoValue(rateAgo);
            chaserule.setLaterValue(rateLater);
        }
    }

    private void tipDialog(String title, String msg, LastInputEditText textView) {
        CustomDialog.Builder builder = new CustomDialog.Builder(activity);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setLayoutSet(DialogLayout.SINGLE);
        builder.setPositiveButton("知道了", (dialog, which) ->
        {
            textView.setText("1");
            dialog.dismiss();
        });
        builder.create().show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        GoldenAppleApp.getUserCentre().setStopOnWin(isChecked);
    }

    public interface OnArrangeChangedListener {
        void newArrange(ChaseRuleData chaserule, boolean status, boolean dupe);
    }

    public void hideSoftKeyBoard(View v) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
