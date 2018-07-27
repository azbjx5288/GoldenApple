package com.goldenapple.lottery.game;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.goldenapple.lottery.R;
import com.goldenapple.lottery.data.Lottery;
import com.goldenapple.lottery.data.Method;
import com.goldenapple.lottery.material.Calculation;
import com.goldenapple.lottery.material.ConstantInformation;
import com.goldenapple.lottery.util.NumbericUtils;
import com.goldenapple.lottery.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.goldenapple.lottery.game.GameConfig.DS_TYPE_KL10;
import static com.goldenapple.lottery.game.GameConfig.DS_TYPE_KL12;
import static com.goldenapple.lottery.game.GameConfig.DS_TYPE_PK10;
import static com.goldenapple.lottery.game.GameConfig.DS_TYPE_SSC;
import static com.goldenapple.lottery.game.GameConfig.DS_TYPE_SYXW;

/**
 * Created by Ace on 2018/7/03.
 * 任选 单式玩法
 */
public class RxDsGame extends Game {
    private static final String TAG = RxDsGame.class.getSimpleName();
    private static final int FLAG_CAL_DONE = 1;
    private int basic, digit = 0;
    private boolean hasIllegal;
    private boolean showOrHide = false;
    private ArrayList<String[]> codeList;

    private Button clear;
    private ImageButton submit;
    private EditText codesInput;
    private LinearLayout mainLayout;
    private LinearLayout loadingLayout;
    private LinearLayout digitMode;
    private TextView pickNoticeView;
    private ViewGroup parentLayout;

    private SparseArray<Boolean> checkArray;
    private ExecutorService executorService;
    private Runnable delayRun;
    private Handler handler;

    public RxDsGame(Activity activity, Method method, Lottery lottery) {
        super(activity, method, lottery);
        checkArray = new SparseArray();
        checkArray.put(0, false);
        checkArray.put(1, false);
        checkArray.put(2, false);
        checkArray.put(3, false);
        checkArray.put(4, false);
        switch (method.getId()) {
            case 200:   //时时彩 任二直选单式 By Ace
            case 201:   //时时彩 任二组选单式 By Ace
                checkArray.put(3, true);
                checkArray.put(4, true);
                showOrHide = true;
                basic = 2;
                break;
            case 186:   //任选三直选单式 By Ace
            case 189:   //任选三组六单式 By Ace
            case 188:   //任选三组三单式 By Ace
                checkArray.put(2, true);
                checkArray.put(3, true);
                checkArray.put(4, true);
                showOrHide = true;
                basic = 3;
                break;
            case 187: //任选四直选单式
                checkArray.put(1, true);
                checkArray.put(2, true);
                checkArray.put(3, true);
                checkArray.put(4, true);
                showOrHide = true;
                basic = 4;
                break;
            default:
                Log.w(TAG, "DsGame: 不支持的method类型：" + method.getId());
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
        }
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
            case DS_TYPE_SYXW:
            case DS_TYPE_PK10:
            case DS_TYPE_KL10:
            case DS_TYPE_KL12:
                break;
            default:
                Log.w(TAG, "DsGame: 不支持的类型：method:" + method.getId() + ", lottery:" + lottery.getId());
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
                break;
        }
        codeList = new ArrayList<>();
        setSingleNum(0);
        initThread();
    }

    @Override
    public void onInflate() {
        try {
            createPicklayout(this);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(topLayout.getContext(), "不支持的类型", Toast.LENGTH_LONG).show();
        }
    }

    private void initThread() {
        executorService = Executors.newSingleThreadExecutor();
        delayRun = new Runnable() {
            @Override
            public void run() {
                //在这里调用服务器的接口，获取数据
                doCalculation();
            }
        };
        handler = new Handler();
    }

    //计算
    private void noteCount() {
        codesInput.setText(getSubmitCodes());
        digit = 0;
        List<String> array = new ArrayList<>();
        if (codeList.size() > 0) {
            for (int i = 0, size = checkArray.size(); i < size; i++) {
                Boolean check = checkArray.get(i);
                if (check) {
                    array.add("" + i);
                    digit += 1;
                }
            }
        }
        int chance = 0;
        switch (method.getId()) {
            case 200:   //时时彩 任二直选单式 By Ace
            case 186:   //时时彩 任选三直选单式 By Ace
            case 187:   //时时彩 任选四直选单式 By Ace
            case 201:   //时时彩 任二组选单式 By Ace
                String[] combineData = array.toArray(new String[array.size()]);
                chance = Calculation.getInstance().C(combineData.length, basic);
                break;
            case 189:   //任选三组六单式 By Ace
            case 188:   //任选三组三单式 By Ace
                chance = (digit > basic ? digit : 1) * (digit > basic ? digit - basic : 1);
                break;
            default:
                Log.w(TAG, "DsRxGame: 不支持的method类型：" + method.getId());
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
        }
        setSingleNum(codeList.size() > 0 ? codeList.size() * chance : 0);

    }

    public void calculate() {
        if (!isCalculating()) {
            loadingLayout.setVisibility(View.VISIBLE);
            submit.setEnabled(false);
            executorService.execute(delayRun);
        }
    }

    private void doCalculation() {
        setCalculating(true);
        verify();
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
                codeList = NumbericUtils.delDupWithOrder(codeList);
                break;
            case DS_TYPE_SYXW:
            case DS_TYPE_PK10:
            case DS_TYPE_KL12:
            case DS_TYPE_KL10:
                codeList = NumbericUtils.delDup(codeList);
            default:
                break;
        }
        if (!activity.isFinishing()) {
            setCalculating(false);
            loadingLayout.setVisibility(View.GONE);
            submit.setEnabled(true);
            noteCount();
            pickNoticeView.callOnClick();
            if (hasIllegal && !executorService.isShutdown())
                ToastUtils.showShortToastLocation(activity, "您的注单存在错误/重复项，已为您优化注单。", Gravity.CENTER, 0, -300);
        }
    }

    private void verify() {
        if (codesInput == null) {
            return;
        }
        hasIllegal = false;
        codeList.clear();
        if (TextUtils.isEmpty(codesInput.getText()))
            return;
        String[] codes;
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
                codes = codesInput.getText().toString().split("[,，;；：:|｜.\n ]");
                break;
            default:
                codes = codesInput.getText().toString().split("[,，;；：:|｜.\n]");
                break;
        }

        for (String code : codes) {
            String[] strs;
            switch (type) {
                case DS_TYPE_SSC:
                    int length = code.length();
                    strs = new String[length];
                    for (int i = 0; i < length; i++)
                        strs[i] = String.valueOf(code.charAt(i));
                    break;
                default:
                    strs = code.split(" ");
                    if (NumbericUtils.hasDupString(strs)) {
                        hasIllegal = true;
                        continue;
                    }
                    break;
            }
            if (strs.length != basic) {
                hasIllegal = true;
                continue;
            }

            switch (type) {
                case DS_TYPE_SSC:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SSC);
                    break;
                case DS_TYPE_SYXW:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_SYXW);
                    break;
                case DS_TYPE_PK10:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_PK10);
                    break;
                case DS_TYPE_KL12:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_KL12);
                    break;
                case DS_TYPE_KL10:
                    verifyNumber(strs, ConstantInformation.LEGAL_NUMBER_KL10);
                    break;
                default:
                    Log.w(TAG, "verify: 不支持的类型：" + type);
                    ToastUtils.showShortToast(getActivity(), "不支持的类型");
                    break;
            }
        }
        if (NumbericUtils.hasDupArray(codeList))
            hasIllegal = true;
    }

    private void verifyNumber(String[] strs, ArrayList<String> legals) {
        for (String str : strs) {
            if (!legals.contains(str)) {
                hasIllegal = true;
                return;
            }
        }
        switch (method.getId()) {
            //组三
            case 188: //任选三组三单式
                if (!NumbericUtils.isDupStrCountUnique(strs, 2)) {
                    hasIllegal = true;
                    return;
                }
                break;
            //组六
            case 189:
                if (NumbericUtils.hasDupString(strs)) {
                    hasIllegal = true;
                    return;
                }
                break;
            case 201: //任二 组选
                if (!NumbericUtils.isDupStrCountRepeat(strs)) {
                    hasIllegal = true;
                    return;
                }
                break;
        }
        codeList.add(strs);
    }

    public String getSubmitCodes() {
        StringBuilder builder = new StringBuilder();
        int type = GameConfig.getDsType(method, lottery);
        switch (type) {
            case DS_TYPE_SSC:
                for (int i = 0, size = codeList.size(); i < size; i++) {
                    for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                        builder.append(codeList.get(i)[j]);
                    }
                    if (i < size - 1)
                        builder.append("|");
                }
                break;
            case DS_TYPE_SYXW:
            case DS_TYPE_PK10:
            case DS_TYPE_KL10:
            case DS_TYPE_KL12:
                for (int i = 0, size = codeList.size(); i < size; i++) {
                    for (int j = 0, length = codeList.get(i).length; j < length; j++) {
                        builder.append(codeList.get(i)[j]);
                        if (j < length - 1)
                            builder.append(" ");
                    }
                    if (i < size - 1)
                        builder.append("|");
                }
                break;
            default:
                Log.w(TAG, "getSubmitCodes: 不支持的类型：" + type);
                ToastUtils.showShortToast(getActivity(), "不支持的类型");
                break;
        }

        return builder.toString();
    }

    private View createDefaultPickLayout(ViewGroup container) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.single_rx_layout, null, false);
    }

    private void createPicklayout(Game game) {
        /*对GameFragment的View进行操作*/
        parentLayout = activity.getWindow().getDecorView().findViewById(R.id.parent_layout);
        parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codesInput.clearFocus();
            }
        });
        pickNoticeView = activity.getWindow().getDecorView().findViewById(R.id.pick_notice);
        submit = activity.getWindow().getDecorView().findViewById(R.id.choose_done_button);
        View view = createDefaultPickLayout(game.getTopLayout());
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        ViewGroup topLayout = game.getTopLayout();
        topLayout.addView(view);
        game.setInputStatus(true);
        loadingLayout = view.findViewById(R.id.loading_layout);
        loadingLayout.setVisibility(View.GONE);
        mainLayout = view.findViewById(R.id.main_layout);
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codesInput != null) {
                    codesInput.clearFocus();
                }
            }
        });

        digitMode = view.findViewById(R.id.digit_mode);
        digitMode.setVisibility(showOrHide ? View.VISIBLE : View.GONE);
        checkSettings(checkArray);

        codesInput = view.findViewById(R.id.input_multiline_text);
        codesInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                runThread();
                codesInput.setSelection(s.length());
            }
        });
        codesInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(codesInput.getWindowToken(), 0);
                    if (!isCalculating()) {
                        runThread();
                    }
                }
            }
        });
        codesInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codesInput.setFocusable(true);
            }
        });

        clear = view.findViewById(R.id.clear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codesInput.setText("");
                if (!isCalculating()) {
                    runThread();
                }
            }
        });
    }

    private void checkSettings(SparseArray checkArray) {
        for (int i = 0; i < digitMode.getChildCount(); i++) {
            AppCompatCheckBox checkBox = (AppCompatCheckBox) digitMode.getChildAt(i);
            checkBox.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
                checkChange(buttonView, isChecked);
            });
            Boolean pickStatus = (Boolean) checkArray.get(i);
            if (pickStatus) {
                checkBox.setChecked(pickStatus);
            } else {
                checkBox.setChecked(pickStatus);
            }
        }
    }

    private void checkChange(View view, boolean change) {
        switch (String.valueOf(view.getTag())) {
            case "wan":
                checkArray.put(0, change);
                break;
            case "qian":
                checkArray.put(1, change);
                break;
            case "bai":
                checkArray.put(2, change);
                break;
            case "shi":
                checkArray.put(3, change);
                break;
            case "ge":
                checkArray.put(4, change);
                break;
        }
        runThread();
    }

    private void runThread(){
        if (delayRun != null) {
            handler.removeCallbacks(delayRun);
        }
        //延迟800ms，如果不再输入字符，则执行该线程的run方法
        if(codesInput!=null) {
            if (!TextUtils.isEmpty(codesInput.getText())) {
                handler.postDelayed(delayRun, 800);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown())
            executorService.shutdownNow();
    }
}
