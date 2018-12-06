package com.wyj.dagger.mode;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.wyj.dagger.DaggerUser;
import com.wyj.treasure.BuildConfig;
import com.wyj.wanandroid.WanApi;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author yujie
 * @date on 2018/7/30
 * @describe @SingleTon 注解，是全局单例的对象
 * Module里面的方法基本都是创建类实例的方法。
 **/
@Module
public class ApiModule {
    public static final String END_POINT = "http://www.baidu.com";

    @Provides
    @Singleton
    public WanApi provideWanApi(Retrofit retrofit) {
        return retrofit.create(WanApi.class);
    }
    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        return new OkHttpClient.Builder()
//                .addInterceptor(authTokenInterceptor())
                .addInterceptor(loggingInterceptor) // 添加日志拦截器
//                .addInterceptor(buildCacheInterceptor())
//                .cache(cache) // 设置缓存文件
                .retryOnConnectionFailure(true) // 自动重连
                .connectTimeout(15, TimeUnit.SECONDS) // 15秒连接超时
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
//                .cookieJar(new CookieJarImpl(new PersistentCookieStore(application.getApplicationContext())))
                .build();
//        OkHttpClient httpClient = new OkHttpClient.Builder()
//                .addNetworkInterceptor(new StethoInterceptor())
//                .connectTimeout(60 * 1000, TimeUnit.SECONDS)
//                .readTimeout(60 * 1000, TimeUnit.SECONDS)
//                .build();
//        return httpClient;
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient client) {
        Gson gson = new GsonBuilder()
//                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
                    @Override
                    public Date deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                        String date = element.getAsString();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        format.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
                        try {
                            Date date1 = format.parse(date);
//                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//                            dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
//                            Timber.e("JsonDeserializer<Date> before %s after %s %s", date, TimeUtils.date2String(date1, dateFormat), TimeZone.getDefault().getDisplayName());
                            return date1;
                        } catch (ParseException exp) {
                            exp.printStackTrace();
                            return null;
                        }
                    }
                })
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                // 集成RxJava处理
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 集成Gson转换器
                .addConverterFactory(GsonConverterFactory.create(gson))
                // 使用OkHttp Client
                .client(client)
                // baseUrl总是以/结束，@URL不要以/开头
                .baseUrl(END_POINT)
                .build();
        return retrofit;
    }

    @Provides
    @Singleton
    DaggerUser getUser() {
        return new DaggerUser("name form ApiModule");
    }
}
