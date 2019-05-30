package com.wyj.treasure;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.wyj.dagger.component.AppComponent;
import com.wyj.dagger.component.DaggerAppComponent;
import com.wyj.dagger.mode.ApiModule;
import com.wyj.dagger.mode.AppMode;
import com.wyj.greendao.GreenDAOHelp;
import com.wyj.network.NetWorkService;
import com.wyj.realm.AppRealmMigration;
import com.wyj.treasure.receiver.NetworkChangeReceiver;
import com.wyj.treasure.utils.ActivityCollector;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;

import java.util.logging.LogManager;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by wangyujie
 * Date 2017/7/30
 * Time 21:41
 */

public class MyApplication extends Application {
    private static Context mContext;
    private static final String TAG = "MyApplication";
    private static MyApplication instance;

    private AppComponent appComponent;
    private PackageInfo packInfo;
    private String processName;
    private Activity currentActivity;

    /**
     * 获取全局的 Context
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 应用程序创调
     * 创和实例化任何应用程序状态变量或共享资源变量，方法内获Application单例。
     */
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        instance = this;
        mContext = getApplicationContext();
        inject();
        initCrash();
//        initError();
        networkChanges();
        initDataBaseForGreenDAO();
        initDataBaseForRealm();

        initStetho();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            processName = getProcessName();
        } else {
            processName = CommonUtils.getProcessName();
        }
        if (getPackageName().equals(processName)) {
            LogUtil.d("AndroidApplication onCreate=" + processName);
        }
        LogUtil.d("MyApplication---onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builde = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builde.build());
            builde.detectFileUriExposure();
        }
        // 全局监听Activity生命周期
        registerActivityListener();
    }


    private void initDataBaseForRealm() {
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(AppConfig.REALM_NAME)
                .schemaVersion(3)
//                .inMemory()//创建非持久化的Realm，也就是保持在内存中，应用关闭后就清除了。
                .migration(new AppRealmMigration())
                .build();
        Realm.setDefaultConfiguration(configuration);
    }

    private void initDataBaseForGreenDAO() {
        //Hawk.init(this).build();
        //创建GreenDAO 数据库
        GreenDAOHelp.create(this, AppConfig.GREEMDAO_NAME);
    }

    private void networkChanges() {
        /*
         * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
         * <uses-permission android:name="android.permission.WRITE_SETTINGS" />
         */


        IntentFilter intentFilter = new IntentFilter();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.requestNetwork(new NetworkRequest.Builder().build(),
                    new ConnectivityManager.NetworkCallback() {
                        @Override
                        public void onLost(Network network) {
                            super.onLost(network);
                            sendBroadcast(new Intent(Constants.ACTION_NETWORK_CHANGE_OFF));
                        }

                        @Override
                        public void onAvailable(Network network) {
                            super.onAvailable(network);
                            sendBroadcast(new Intent(Constants.ACTION_NETWORK_CHANGE_ON));
                        }
                    });
        } else {
            /*动态注册*/
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        }
        intentFilter.addAction(Constants.ACTION_NETWORK_CHANGE_OFF);
        intentFilter.addAction(Constants.ACTION_NETWORK_CHANGE_ON);
        registerReceiver(new NetworkChangeReceiver(), intentFilter);
    }

    /**
     * Android调试工具Stetho
     * Stetho初始化
     */
    private void initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }

    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appMode(new AppMode(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * 错误统计
     */
    private void initCrash() {
        CrashHandler.getInstance().init(this);
//        CrashReport.initCrashReport(getApplicationContext(), "42788188ed", BuildConfig.DEBUG);
//        CrashReport.setUserSceneTag(mContext, 20170811); // 上报后的Crash会显示该标签
    }

    /**
     * 拦截app闪退
     */
    private void initError() {
//        CaocConfig.Builder.create()
//                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
//                .enabled(true) //default: true
//                .showErrorDetails(false) //default: true
//                .showRestartButton(true) //default: true
//                .trackActivities(true) //default: false
//                .minTimeBetweenCrashesMs(2000) //default: 3000
//                .errorDrawable(R.drawable.ic_dashboard_black_24dp) //default: bug image
////                .restartActivity(YourCustomActivity.class) //default: null (your app's launch activity)
////                .errorActivity(YourCustomErrorActivity.class) //default: null (default onError activity)
////                .eventListener(new YourCustomEventListener()) //default: null
//                .apply();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        return instance;
    }

    /**
     * 获取apk包名路径
     */
    @SuppressLint("Override")
    public String getApkDataDir() {
        if (packInfo == null)
            getAppInfo();
        return packInfo != null && packInfo.applicationInfo != null ? packInfo.applicationInfo.dataDir : "";
    }

    private void getAppInfo() {
        // 获取packageManager的实例
        PackageManager packageManager = getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        try {

            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 应用程序对象终止调
     * 不定调。当应用程序被内核终止为别应用程序释放资源，将不提醒且不调用应用程序对象onTerminate()而直接终止进程。
     */
    @Override
    public void onTerminate() {
        LogUtil.e("onTerminate");
        super.onTerminate();
    }

    /**
     * 系统资源匮乏调
     * 通在后台进程已结束且前台应用程序仍缺内存时调，重写该方法清空缓存或释放非必要资源。
     */
    @Override
    public void onLowMemory() {
        LogUtil.e("onLowMemory");
        super.onLowMemory();
    }

    /**
     * 运行时决定当前应用程序应减内存开销时（通常进入后台运行）调，含一level参数，用于提供请求的上下文。
     *
     * @param level 级别
     */
    @Override
    public void onTrimMemory(int level) {
        LogUtil.e("onTrimMemory");
        super.onTrimMemory(level);
    }

    /**
     * 与Activity不同，配置改变时应用程序对象不终止和重启。若应用程序用值依赖特定配置，则重写该方法加载这些值或于应用程序级处理配置值改变。
     *
     * @param newConfig 配置
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtil.e("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Activity全局监听
     */
    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                currentActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                // 将监听到销事件Activity移除集合
            }
        });
    }

    public Activity getCurrentActivity() {
        return currentActivity;
    }
}
