package com.wyj.treasure;

import android.app.Application;
import android.content.Context;

import cat.ereza.customactivityoncrash.config.CaocConfig;

/**
 * Created by wangyujie
 * Date 2017/7/30
 * Time 21:41
 * TODO
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        initError();
    }

    /*获取全局的 Context */
    public static Context getContext() {
        return context;
    }

    private void initError() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //default: CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM
                .enabled(true) //default: true
                .showErrorDetails(false) //default: true
                .showRestartButton(true) //default: true
                .trackActivities(true) //default: false
                .minTimeBetweenCrashesMs(2000) //default: 3000
                .errorDrawable(R.drawable.ic_dashboard_black_24dp) //default: bug image
//                .restartActivity(YourCustomActivity.class) //default: null (your app's launch activity)
//                .errorActivity(YourCustomErrorActivity.class) //default: null (default error activity)
//                .eventListener(new YourCustomEventListener()) //default: null
                .apply();
    }
}
