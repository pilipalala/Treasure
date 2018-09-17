package com.wyj.retrofit;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends BaseActivity {
    private static final String TAG = "RetrofitActivity";

    @Override
    protected int initView() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                //这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl("http://www.weather.com.cn/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ApiService apiService = retrofit.create(ApiService.class);
        Call<ResponseBody> weather = apiService.getWeather("101010100");
        try {
            Response<ResponseBody> response = weather.execute();
            String s = response.body().toString();
            Log.d(TAG, "RetrofitActivity_36-->initData: " + s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
