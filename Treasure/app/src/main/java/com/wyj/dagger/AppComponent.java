package com.wyj.dagger;

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
@Component(modules = {ApiModule.class})
public interface AppComponent {
    OkHttpClient getClient();

    Retrofit getRetrofit();

    DaggerUser getUser();
}
