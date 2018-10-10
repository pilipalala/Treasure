package com.wyj.mvp.service;

import android.content.Context;

import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangyujie
 * @date 2018/9/28.10:21
 * @describe 主要用于Retrofit的初始化：
 */
public class DouBanClient {
    private final Context mContext;
    OkHttpClient client = new OkHttpClient();
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static DouBanClient instance = null;

    private Retrofit mRetrofit;

    public DouBanClient(Context context) {
        mContext = context;
        init();
    }

    private void init() {
        resetApp();
    }

    public ApiService getServer() {
        return mRetrofit.create(ApiService.class);
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static DouBanClient getInstance(Context context) {
        if (instance == null) {
            instance = new DouBanClient(context);
        }
        return instance;
    }

}
