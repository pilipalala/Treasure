package com.wyj.dagger.component;

import android.app.Application;
import android.content.Context;

import com.wyj.dagger.DaggerUser;
import com.wyj.dagger.mode.ApiModule;
import com.wyj.dagger.mode.AppMode;
import com.wyj.dagger.qualifier.AppForApplication;
import com.wyj.dagger.qualifier.AppForContext;
import com.wyj.wanandroid.WanApi;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author yujie
 * @date on 2018/7/30
 * @describe
 **/
@Singleton
@Component(modules = {ApiModule.class, AppMode.class})
public interface AppComponent {

    @AppForApplication
    Application getApplication();

    @AppForContext
    Context getContext();

    WanApi getWanApi();

    OkHttpClient getClient();

    Retrofit getRetrofit();

    DaggerUser getUser();
}
