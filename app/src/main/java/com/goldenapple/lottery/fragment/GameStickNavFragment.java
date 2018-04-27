package com.goldenapple.lottery.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.goldenapple.lottery.BuildConfig;
import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.app.FragmentLauncher;
import com.goldenapple.lottery.app.GoldenAppleApp;
import com.goldenapple.lottery.base.Preferences;
import com.goldenapple.lottery.base.net.GsonHelper;
import com.goldenapple.lottery.base.net.RestCallback;
import com.goldenapple.lottery.base.net.RestRequest;
import com.goldenapple.lottery.base.net.RestRequestManager;
import com.goldenapple.lottery.base.net.RestResponse;
import com.goldenapple.lottery.component.AdaptHighListView;
import com.goldenapple.lottery.component.ExpandableLayout;
import com.goldenapple.lottery.component.SmartScrollView;
import com.goldenapple.lottery.data.Lottery;
import com.goldenapple.lottery.data.LotteryCode;
import com.goldenapple.lottery.data.LotteryCodeCommand;
import com.goldenapple.lottery.data.Method;
import com.goldenapple.lottery.data.MethodList;
import com.goldenapple.lottery.data.MethodListCommand;
import com.goldenapple.lottery.data.MethodType;
import com.goldenapple.lottery.game.Game;
import com.goldenapple.lottery.game.GameConfig;
import com.goldenapple.lottery.game.MenuController;
import com.goldenapple.lottery.game.OnSelectedListener;
import com.goldenapple.lottery.material.ShoppingCart;
import com.goldenapple.lottery.material.Ticket;
import com.goldenapple.lottery.pattern.TitleTimingView;
import com.goldenapple.lottery.view.TableMenu;
import com.goldenapple.lottery.view.adapter.GameStickResultAdapter;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ACE-PC on 2018/4/13.
 * 重新游戏选号页面
 */

public class GameStickNavFragment extends BaseFragment implements OnSelectedListener, TableMenu.OnClickMethodListener {

    private static final String TAG = GameStickNavFragment.class.getSimpleName();

    private static final int ID_METHOD_LIST = 1;
    private static final int ID_RESULT_LIST = 2;

    @BindView(android.R.id.title)
    TextView titleView;
    @BindView(R.id.chart_layout)
    ExpandableLayout chartLayout;
    @BindView(R.id.game_timing_view)
    ViewGroup gameTimingView;
    @BindView(R.id.issueresult_list)
    AdaptHighListView issueResultList;
    @BindView(R.id.stick_scrollLayout)
    SmartScrollView stickScrollLayout;
    @BindView(R.id.pick_game_layout)
    LinearLayout pickGameLayout;
    @BindView(R.id.lottery_choose_bottom)
    RelativeLayout chooseBottomLayout;
    @BindView(R.id.pick_notice)
    TextView pickNoticeView;
    @BindView(R.id.pick_random)
    Button pickRandom;
    @BindView(R.id.choose_done_button)
    Button chooseDoneButton;

    WebView webView;
    private TitleTimingView timingView;
    private ArrayList<MethodList> methodListRecord;
    private MenuController menuController;
    private GameStickResultAdapter resultAdapter;
    private MethodType methodTypeRecord;
    private Game game;
    private Lottery lottery;
    private Method methodRecord;
    private ShoppingCart shoppingCart;
    private ArrayList<LotteryCode> resultList = new ArrayList<>();

    public static void launch(BaseFragment fragment, Lottery lottery) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        FragmentLauncher.launch(fragment.getActivity(), GameStickNavFragment.class, bundle);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_sticknav_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyArguments();
        initMenu();
        initView();
        loadMethodFromXml();
        loadMenu();
    }

    //初始化彩种信息
    private void applyArguments() {
        lottery = (Lottery) getArguments().getSerializable("lottery");
        shoppingCart = ShoppingCart.getInstance();
    }

    private void initView() {
        resultAdapter = new GameStickResultAdapter(lottery, getContext());
        issueResultList.setAdapter(resultAdapter);
        resultAdapter = new GameStickResultAdapter(lottery, getContext());
        issueResultList.setAdapter(resultAdapter);

        chartLayout.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);
            }
        });
        stickScrollLayout.setHeadView(chartLayout);
        /*if (chartLayout.isExpanded()) {
            chartLayout.collapse();
        } else{
            chartLayout.expand();
        }*/
    }

    private void initMenu() {
        menuController = new MenuController(getActivity(), lottery);
        menuController.setOnClickMethodListener(this);
    }

    private void loadTimingView(int methodID) {
        timingView = new TitleTimingView(getActivity(), findViewById(R.id.game_timing_view), lottery, methodID);
    }

    @OnClick(R.id.game_timing_view)
    public void chartExpansion() {
        if (chartLayout.isExpanded()) {
            chartLayout.collapse();
        } else {
            chartLayout.expand();
        }
    }

    @OnClick(android.R.id.home)
    public void finishFragment() {
        getActivity().finish();
    }

    @OnClick({R.id.title_text_layout, android.R.id.title, R.id.title_image})
    public void showOrHideMenu(View v) {
        hideSoftKeyBoard();
        Log.d(TAG, "showOrHideMenu: ");
        if (menuController.isShowing()) {
            menuController.hide();
        } else {
            menuController.show(titleView);
        }
    }

    @OnClick(R.id.help)
    public void showHelp() {
        //TODO:点击“帮助”按钮，显示帮助信息
    }

    @OnClick(R.id.pick_random)
    public void onRandom() {
        if (game != null) {
            game.onRandomCodes();
        }
    }

    @OnClick(R.id.pick_notice)
    public void calculate() {
        if (game == null) {
            return;
        }
        pickNoticeView.setText(String.format("选择%d注", game.getSingleNum()));
        if (game.getSingleNum() > 0) {
            chooseDoneButton.setEnabled(true);
        } else {
            chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
        }
    }

    @OnClick(R.id.choose_done_button)
    public void onChooseDone() {
        if (game == null) {
            return;
        }
        if (game.getSingleNum() > 0) {
            String codes = game.getSubmitCodes();
            Ticket ticket = new Ticket();
            ticket.setMethodType(methodTypeRecord);
            ticket.setChooseMethod(game.getMethod());
            ticket.setChooseNotes(game.getSingleNum());
            ticket.setCodes(codes);

            shoppingCart.init(lottery);
            shoppingCart.addTicket(ticket);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("lottery", lottery);
        ShoppingFragment.launch(GameStickNavFragment.this, lottery);
    }

    private void saveMethod2Xml(MethodType methodType, Method method) {
        String methodTypeKey = "game_config_methodtype_" + GoldenAppleApp.getUserCentre().getUserID() + "_" + lottery.getId();
        Preferences.saveString(getContext(), methodTypeKey, GsonHelper.toJson(methodType));

        String methodKey = "game_config_method_" + GoldenAppleApp.getUserCentre().getUserID() + "_" + lottery.getId();
        Preferences.saveString(getContext(), methodKey, GsonHelper.toJson(method));
    }

    private void loadMethodFromXml() {
        String methodTypeKey = "game_config_methodtype_" + GoldenAppleApp.getUserCentre().getUserID() + "_" + lottery.getId();
        String methodTypeJson = Preferences.getString(getContext(), methodTypeKey, null);

        String methodKey = "game_config_method_" + GoldenAppleApp.getUserCentre().getUserID() + "_" + lottery.getId();
        String methodJson = Preferences.getString(getContext(), methodKey, null);

        if (!TextUtils.isEmpty(methodJson) && !TextUtils.isEmpty(methodTypeJson)) {
            methodTypeRecord = GsonHelper.fromJson(methodTypeJson, MethodType.class);
            methodRecord = GsonHelper.fromJson(methodJson, Method.class);
            if (methodTypeRecord != null && methodRecord != null) {
                changeGameMethod(methodTypeRecord, methodRecord);
            }
        }
    }

    private void loadWebViewIfNeed() {
        if (webView != null) {
            return;
        }
        if (chooseBottomLayout != null) {
            chooseBottomLayout.postDelayed(() -> {
                synchronized (chooseBottomLayout) {
                    if (isFinishing()) {
                        return;
                    }
                    if (webView != null) {
                        update2WebView();
                        return;
                    }
                    webView = new WebView(getActivity());
                    chooseBottomLayout.addView(webView, 1, 1);
                    initWebView();
                    webView.loadUrl("file:///android_asset/web/game.html");
                }
            }, 400);
        }
    }

    private void initWebView() {
        webView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSettings.setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new JsInterface(), "androidJs");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (game != null) {
            game.reset();
        }
    }

    private void changeGameMethod(MethodType methodType, Method method) {
        if (method == null) {
            return;
        }

        if (game == null) {
            pickGameLayout.removeAllViews();
        } else {
            if (methodType.getId() == game.getMethod().getPid() && method.getId() == game.getMethod().getId()) {
                //同一个玩法，不用切换
                return;
            }
            game.destroy();
            menuController.addPreference(method);
            saveMethod2Xml(methodType, method);
            methodTypeRecord = methodType;
            methodRecord = method;
            if (chartLayout.isExpanded()) {
                chartLayout.collapse();
            }
        }
        if (method.getId() == 178)
            loadTimingView(178);
        else
            loadTimingView(0);
        resultAdapter.setData(resultList, method);
        menuController.setCurrentMethod(method);
        titleView.setText(methodType.getNameCn() + "-" + method.getNameCn());
        pickNoticeView.setText("选择0注");
        chooseDoneButton.setEnabled(!shoppingCart.isEmpty());
        game = GameConfig.createGame(getActivity(), method, lottery);
        game.inflate(pickGameLayout);
        game.setOnSelectedListener(this);
        if (game.isInputStatus())
            pickRandom.setVisibility(View.GONE);
        else
            pickRandom.setVisibility(View.VISIBLE);

        loadWebViewIfNeed();
    }

    private void updateMenu(ArrayList<MethodList> methodList) {
        menuController.setMethodList(methodList);
    }

    @Override
    public void onClickMethod(Method method) {
        menuController.hide();
        MethodType methodType = selectMethodType(method);
        changeGameMethod(methodType, method);
    }

    private class JsInterface {
        @JavascriptInterface
        public String getData() {
            if (game == null) {
                return "";
            }
            return game.getWebViewCode();
        }

        @JavascriptInterface
        public String getSubmitCodes() {
            if (game == null) {
                return "";
            }
            return game.getSubmitCodes();
        }

        @JavascriptInterface
        public String getMethodName() {
            if (game == null) {
                return "";
            }
            return game.getMethod().getNameEn();
        }

        @JavascriptInterface
        public int getLotteryId() {
            if (lottery == null) {
                return -1;
            }
            return lottery.getId();
        }

        @JavascriptInterface
        public int getSeriesId() {
            if (lottery == null) {
                return -1;
            }
            return lottery.getSeriesId();
        }

        @JavascriptInterface
        public int getMethodId() {
            if (game == null) {
                return -1;
            }
            return game.getMethod().getId();
        }

        @JavascriptInterface
        public void result(int singleNum) {
            Log.d(TAG, "result() called with: " + "singleNum = [" + singleNum + "]");
            if (game == null) {
                return;
            }
            game.setSingleNum(singleNum);
            webView.post(updatePickNoticeRunnable);
        }
    }

    private void update2WebView() {
        if (webView == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript("calculate();", null);
        } else {
            webView.loadUrl("javascript:calculate();");
        }
    }

    private Runnable updatePickNoticeRunnable = new Runnable() {
        @Override
        public void run() {
            calculate();
        }
    };

    //game.setOnSelectedListener(this)的回调
    @Override
    public void onChanged(Game game) {
        loadWebViewIfNeed();
        update2WebView();
    }

    @Override
    public void onPause() {
        if (webView != null) {
            webView.onPause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (webView != null) {
            webView.onResume();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (timingView != null) {
            timingView.stop();
        }
        if (game != null) {
            game.destroy();
        }
        if (webView != null) {
            webView.destroy();
        }
    }

    private Method defaultGameMethod(ArrayList<MethodList> methodList) {
        int id = 0;
        switch (lottery.getSeriesId()) {
            case 2://11选5
                id = 112;
                break;
            case 1://重庆时时彩
                if (lottery.getId() == 4) {
                    id = 70;
                } else {
                    id = 65;
                }
                break;
            case 4://苹果快三分分彩
                id = 160;
                break;
            case 5://苹果极速PK10
                id = 175;
                break;
            case 3://苹果极速3D
                id = 136;
                break;
            case 9://快乐十分
                id = 456;
            default:
                break;
        }

        if (id == 0) {
            return methodList.get(0).getChildren().get(0).getChildren().get(0);
        }

        Method defaultMethod = null;
        for (MethodList methods : methodList) {
            for (MethodType methodGroup : methods.getChildren()) {
                for (Method method : methodGroup.getChildren()) {
                    if (id == method.getId()) {
                        defaultMethod = method;
                    }
                }
            }
        }
        return defaultMethod;
    }

    private MethodType selectMethodType(Method methodChoose) {
        MethodType methodType = null;
        if (methodChoose != null) {
            for (MethodList methods : methodListRecord) {
                for (MethodType methodGroup : methods.getChildren()) {
                    for (Method method : methodGroup.getChildren()) {
                        if (methodChoose.getId() == method.getId() && methodChoose.getPid() == methodGroup.getId()) {
                            methodType = methodGroup;
                        }
                    }
                }
            }
        } else {
            methodType = methodListRecord.get(0).getChildren().get(0);
        }
        return methodType;
    }

    //加载玩法菜单
    private void loadMenu() {
        MethodListCommand methodListCommand = new MethodListCommand();
        methodListCommand.setLotteryId(lottery.getId());
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<MethodList>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), methodListCommand, typeToken, restCallback, ID_METHOD_LIST, this);
        restRequest.execute();
    }

    //加载开奖走势
    private void loadIssueResult() {
        LotteryCodeCommand command = new LotteryCodeCommand();
        command.setLotteryId(lottery.getId());
        command.setCount(5);
        TypeToken typeToken = new TypeToken<RestResponse<ArrayList<LotteryCode>>>() {
        };
        RestRequest restRequest = RestRequestManager.createRequest(getActivity(), command, typeToken, restCallback, ID_RESULT_LIST, this);
        restRequest.execute();
    }

    private RestCallback restCallback = new RestCallback() {
        @Override
        public boolean onRestComplete(RestRequest request, RestResponse response) {
            switch (request.getId()) {
                case ID_METHOD_LIST:
                    methodListRecord = (ArrayList<MethodList>) response.getData();
                    loadIssueResult();
                    if (methodListRecord != null && methodListRecord.size() > 0) {
                        Method method = defaultGameMethod(methodListRecord);
                        MethodType methodType = selectMethodType(method);
                        saveMethod2Xml(methodType, method);
                        menuController.addPreference(method);
                        changeGameMethod(methodType, method);
                    }
                    updateMenu(methodListRecord);
                    break;
                case ID_RESULT_LIST:
                    resultList = (ArrayList<LotteryCode>) response.getData();
                    resultAdapter.setData(resultList, methodRecord);
                    break;
            }
            return true;
        }

        @Override
        public boolean onRestError(RestRequest request, int errCode, String errDesc) {
            if (errCode == 3004 || errCode == 2016) {
                signOutDialog(getActivity(), errCode);
                return true;
            } else {
                showToast(errDesc);
            }
            return false;
        }

        @Override
        public void onRestStateChanged(RestRequest request, @RestRequest.RestState int state) {
        }
    };
}
