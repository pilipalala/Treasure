package com.wyj.dagger;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author yujie
 * @date on 2018/7/30
 * @describe @SingleTon 注解，是全局单例的对象
 **/
@Module
public class ApiModule {
    public static final String END_POINT = "http://www.baidu.com";

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(60 * 1000, TimeUnit.SECONDS)
                .readTimeout(60 * 1000, TimeUnit.SECONDS)
                .build();
        return httpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(END_POINT)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    DaggerUser getUser() {
        return new DaggerUser("name form ApiProvide");
    }
}
