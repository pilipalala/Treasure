package com.wyj.mvp.service;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wyj.treasure.utils.LogUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author wangyujie
 * @date 2018/9/10.17:07
 * @describe https://www.jianshu.com/p/bd758f51742e
 */
public class Api {
    private static ApiService SERVICE;
    /**
     * 请求超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10000;

    public static ApiService getDefault(String url) {
        if (SERVICE == null) {
            SERVICE = new Retrofit.Builder()
                    .client(getOkHttpClient())
                    .baseUrl(url)
                    //指定使用RxJava 作为CallAdapter
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    //指定 Gson 作为解析Json数据的Converter
//                  .addConverterFactory(GsonConverterFactory.create(buildGson()))
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiService.class);
        }
        return SERVICE;
    }

    @NonNull
    private static OkHttpClient getOkHttpClient() {


        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor());
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        /**
         * 对所有请求添加请求头
         * 这里是为所有的请求添加了请求头
         */
        builder.addInterceptor(getLogInterceptor());
//        builder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                HttpUrl.Builder authorizedUrlBuilder = request.url()
//                        .newBuilder()
//                        //添加统一参数 如手机唯一标识符,token等
//                        .addQueryParameter("key1", "value1")
//                        .addQueryParameter("key2", "value2");
//
//                Request nweRequest = request.newBuilder().header("key1", "value1")
//                        .addHeader("key2", "value2")
//                        .method(request.method(), request.body())
//                        .url(authorizedUrlBuilder.build())
//                        .build();
//
//                return chain.proceed(nweRequest);
//            }
//        });
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
                LogUtil.d("请求链接----->"+message);
            }
        });
        loggingInterceptor.setLevel(logLevel);
        return loggingInterceptor;
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
                .create();
    }
}
