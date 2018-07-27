package com.goldenapple.lottery.fragment;

import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.goldenapple.lottery.BuildConfig;
import com.goldenapple.lottery.R;
import com.goldenapple.lottery.app.BaseFragment;
import com.goldenapple.lottery.app.GoldenAppleApp;
import com.goldenapple.lottery.data.Lottery;
import com.gyf.barlibrary.BarHide;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ACE-PC on 2016/3/7.
 */
public class ChartTrendWebviewFragment extends BaseFragment {
    private static final String TAG = ChartTrendWebviewFragment.class.getSimpleName();

    private Lottery lottery;

    private String url;

    @BindView(R.id.gagame_WebView)
    WebView appView;
    @BindView(R.id.loading_layout)
    LinearLayout loading_layout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_charttrend_webview, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lottery = (Lottery) getArguments().getSerializable("lottery");

        url = GoldenAppleApp.getInstance().getBaseUrl() +"/trend?lottery_id=" + lottery.getId();
//        url="https://www.baidu.com/";
        loading_layout.setVisibility(View.VISIBLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        WebSettings settings = appView.getSettings();
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setSupportMultipleWindows(true);
        settings.setAppCacheEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setJavaScriptEnabled(true);
//        appView.addJavascriptInterface(new ChartTrendWebviewFragment.JsInterface(), "androidJs");
        appView.setOverScrollMode(WebView.OVER_SCROLL_ALWAYS);
        appView.getSettings().setBlockNetworkImage(true);
        appView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if ( loading_layout!= null)
                    loading_layout .setVisibility(View.GONE);
                appView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                //注意：super句话一定要删除，或者注释掉，否则又走handler.cancel()默认的不支持https的了。
                //super.onReceivedSslError(view, handler, error);
                //handler.cancel(); // Android默认的处理方式
                //handler.handleMessage(Message msg); // 进行其他处理

                handler.proceed(); // 接受所有网站的证书
            }
        });
        appView.loadUrl(url);
//        appView.setVisibility(View.VISIBLE);
    }
//
//    private class JsInterface {
//        @JavascriptInterface
//        public void finishGame() {
//            getActivity().finish();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        mImmersionBar.hideBar(BarHide.FLAG_HIDE_BAR).init();
        if (appView != null) {
            appView.onResume();
        }
    }

    @Override
    public void onDestroy() {
        if (appView != null) {
            appView.destroy();
        }
        super.onDestroy();
    }

}
