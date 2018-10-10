package com.wyj.mvp.service;

import android.support.annotation.NonNull;

import com.wyj.treasure.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangyujie
 * @date 2018/9/17.15:51
 * @describe
 */
public class ZhiHuClient {
    private static ApiService SERVICE;
    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10000;
    //定义api路径
    static String url = "http://news-at.zhihu.com/api/4/news/";

    public static ApiService getApi() {
        if (SERVICE == null) {
            SERVICE = new Retrofit.Builder()
                    //放路径 这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                    .baseUrl(url)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //Gson解析
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getOkHttpClient())
                    .build().create(ApiService.class);
        }
        return SERVICE;
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.addInterceptor(getLogInterceptor());
        return builder.build();
    }

    @NonNull
    private static HttpLoggingInterceptor getLogInterceptor() {
        //日志显示级别
        HttpLoggingInterceptor.Level logLevel = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.d("请求链接" + message);
            }
        });
        loggingInterceptor.setLevel(logLevel);
        return loggingInterceptor;
    }
}
