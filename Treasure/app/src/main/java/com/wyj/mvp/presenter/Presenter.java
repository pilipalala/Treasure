package com.wyj.mvp.presenter;

import android.content.Intent;

import com.wyj.mvp.view.IView;

/**
 * @author wangyujie
 * @date 2018/9/28.10:43
 * @describe 添加描述
 */
public interface Presenter {
    void onCreate();
    void onStart();

    void onPause();

    void onStop();

    void attachView(IView view);

    void attachIncomingIntent(Intent intent);


}
