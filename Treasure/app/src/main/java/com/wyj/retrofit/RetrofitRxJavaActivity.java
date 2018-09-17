package com.wyj.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.wyj.treasure.R;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRxJavaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit_rxjava);
        getNews();
    }

    public void getNews() {
        //定义路径
        String url = "http://news-at.zhihu.com/api/4/news/";

        Retrofit retrofit = new Retrofit.Builder()
                //放路径 这里建议：- Base URL: 总是以/结尾；- @Url: 不要以/开头
                .baseUrl(url)
                //Gson解析
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        //RxJava 三部曲 第二步 动态代理
        RetrofitUtils retrofitUtils = retrofit.create(RetrofitUtils.class);
        //RxJava 三部曲 第三步 动态代理
        retrofitUtils.getObserverNews().subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<NewBean>() {
            @Override
            public void onSubscribe(Disposable d) {
                //解除订阅
            }

            @Override
            public void onNext(NewBean value) {
                //向下执行

            }

            @Override
            public void onError(Throwable e) {

                //异常时
            }

            @Override
            public void onComplete() {
                //完成时

            }
        });
    }
}
