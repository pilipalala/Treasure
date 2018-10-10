package com.wyj.treasure;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.orhanobut.hawk.Hawk;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.wyj.dagger.ApiModule;
import com.wyj.dagger.AppComponent;
import com.wyj.dagger.DaggerAppComponent;
import com.wyj.greendao.GreenDAOHelp;
import com.wyj.treasure.utils.CommonUtils;
import com.wyj.treasure.utils.LogUtil;

/**
 * Created by wangyujie
 * Date 2017/7/30
 * Time 21:41
 * TODO
 */

public class MyApplication extends Application {
    private static Context context;
    private ApplicationLike tinkerApplicationLike;
    private static final String TAG = "MyApplication";

    private AppComponent appComponent;

    /**
     * 获取全局的 Context
     */
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        inject();
        initTinkerPatch();
        context = getApplicationContext();
        initCrash();
//        initError();
        String processName = CommonUtils.getProcessName();
        if (getPackageName().equals(processName)) {
            LogUtil.d("AndroidApplication onCreate=" + processName);
        }

        LogUtil.d("MyApplication---onCreate");

        Hawk.init(this).build();
        //创建GreenDAO 数据库
        GreenDAOHelp.create(this);

        initStetho();
    }

    /**
     * Android调试工具Stetho
     */
    private void initStetho() {
//        Stetho.initialize(
//                Stetho.newInitializerBuilder(this)
//                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
//                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
//                        .build());
    }

    private void inject() {
        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * 初始化微信Tinker热修复
     */
    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
        // 初始化TinkerPatch SDK, 更多配置可参照API章节中的,初始化SDK
        if (null != tinkerApplicationLike) {
            TinkerPatch.init(tinkerApplicationLike)
                    .reflectPatchLibrary()
                    .setPatchRollbackOnScreenOff(true)
                    .setPatchRestartOnSrceenOff(true)
                    .setFetchPatchIntervalByHours(3);
            // 每隔3个小时(通过setFetchPatchIntervalByHours设置)去访问后台时候有更新,通过handler实现轮训的效果
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }

    }

    /**
     * 错误统计
     */
    private void initCrash() {
//        CrashHandler.getInstance().init(this);
//        CrashReport.initCrashReport(getApplicationContext(), "42788188ed", BuildConfig.DEBUG);
//        CrashReport.setUserSceneTag(context, 20170811); // 上报后的Crash会显示该标签
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
