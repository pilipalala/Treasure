package com.wyj.treasure;

import android.app.Application;
import android.content.Context;

import com.mob.MobSDK;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;

/**
 * Created by wangyujie
 * Date 2017/7/30
 * Time 21:41
 * TODO
 */

public class MyApplication extends Application {
    private static Context context;
    private ApplicationLike tinkerApplicationLike;

    @Override
    public void onCreate() {
        super.onCreate();
        initTinkerPatch();
        context = getApplicationContext();
        initCrash();
//        initError();
        MobSDK.init(context, "1a29c1dc37d04", "12f463dd9226ddfa631d9199cd230b15");



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
        CrashHandler.getInstance().init(this);
//        CrashReport.initCrashReport(getApplicationContext(), "42788188ed", BuildConfig.DEBUG);
//        CrashReport.setUserSceneTag(context, 20170811); // 上报后的Crash会显示该标签
    }

    /**
     * 获取全局的 Context
     */
    public static Context getContext() {
        return context;
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
////                .errorActivity(YourCustomErrorActivity.class) //default: null (default error activity)
////                .eventListener(new YourCustomEventListener()) //default: null
//                .apply();
    }
}
