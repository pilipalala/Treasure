package com.wyj.treasure;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.StrictMode;
import android.support.multidex.MultiDex;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;
import com.wyj.dagger.component.AppComponent;
import com.wyj.dagger.component.DaggerAppComponent;
import com.wyj.dagger.mode.ApiModule;
import com.wyj.dagger.mode.AppMode;
import com.wyj.greendao.GreenDAOHelp;
import com.wyj.realm.AppRealmMigration;
import com.wyj.treasure.receiver.NetworkChangeReceiver;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;

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

    private AppComponent appComponent;

    /**
     * 获取全局的 Context
     */
    public static Context getContext() {
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        mContext = getApplicationContext();
        inject();
        initCrash();
//        initError();
        networkChanges();
        initDataBaseForGreenDAO();
        initDataBaseForRealm();
        initStetho();
        String processName = CommonUtils.getProcessName();
        if (getPackageName().equals(processName)) {
            LogUtil.d("AndroidApplication onCreate=" + processName);
        }
        LogUtil.d("MyApplication---onCreate");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            StrictMode.VmPolicy.Builder builde = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builde.build());
            builde.detectFileUriExposure();
        }
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
}
