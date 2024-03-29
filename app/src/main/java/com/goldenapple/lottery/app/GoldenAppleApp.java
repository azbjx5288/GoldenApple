package com.goldenapple.lottery.app;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.goldenapple.lottery.BuildConfig;
import com.goldenapple.lottery.R;
import com.goldenapple.lottery.base.CrashHandler;
import com.goldenapple.lottery.base.net.NetStateHelper;
import com.goldenapple.lottery.base.thread.ThreadPool;
import com.goldenapple.lottery.user.UserCentre;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Alashi on 2015/12/22.
 */
public class GoldenAppleApp extends Application {
    public static final boolean SERVER_TYPE = true;//true:正式服；false：测试服
    private static GoldenAppleApp sApp;
    private String baseUrl = "";
    private ThreadPool threadPool;
    private NetStateHelper netStateHelper;
    private UserCentre userCentre;
    public static RequestQueue mQueues;

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
        //运行时，出现Crash，将log写到sd卡；
        CrashHandler.getInstance().init(this);

        if (BuildConfig.DEBUG) {
            MobclickAgent.setDebugMode(true);
        }
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //禁止默认的页面统计方式，以便能统计到Fragment的使用情况
        MobclickAgent.openActivityDurationTrack(false);

        threadPool = new ThreadPool();
        netStateHelper = new NetStateHelper(this);
        netStateHelper.resume();

        if (SERVER_TYPE) {
            baseUrl = "http://jpg888.org";  // 正式服
        } else {
            //   baseUrl = "http://api.jpg888.net";//测试服 jpgapi.4385nt.com
//            baseUrl = "http://jpgapi.4385nt.com";
            baseUrl = "http://jpgapi4.4385nt.com";
//            baseUrl = "http://jpgapi2.4385nt.com";
//           baseUrl = "http://jpgapi.4385nt.com";



        }
        userCentre = new UserCentre(this, baseUrl);
        mQueues = Volley.newRequestQueue(getApplicationContext());
        configImageLoader();
    }

    /**
     * 配置ImageLoader
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation") DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions
                (options).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    public static GoldenAppleApp getInstance() {
        return sApp;
    }

    public static ThreadPool getThreadPool() {
        return getInstance().threadPool;
    }

    public static NetStateHelper getNetStateHelper() {
        return getInstance().netStateHelper;
    }

    public static UserCentre getUserCentre() {
        return getInstance().userCentre;
    }

    public static RequestQueue getHttpQueue() {
        return mQueues;
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
