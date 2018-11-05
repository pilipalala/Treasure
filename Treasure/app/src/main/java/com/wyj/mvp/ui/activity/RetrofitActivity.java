package com.wyj.mvp.ui.activity;


import android.util.Log;

import com.wyj.mvp.entity.MoveBean;
import com.wyj.mvp.manager.HttpUtils;
import com.wyj.mvp.service.retrofit.BaseObserver;
import com.wyj.mvp.service.retrofit.BaseSubscriber;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import java.util.List;


public class RetrofitActivity extends BaseActivity {
    private static final String TAG = "RetrofitActivity";

    @Override
    protected int getContentViewID() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void initData() {

        String url = "http://api.douban.com/v2/movie/";
//        String url = "http://www.weather.com.cn/";
        HttpUtils.getTopMovie(url, 0, 20, new BaseSubscriber<List<MoveBean.SubjectsBean>>() {
            @Override
            protected void _onNext(List<MoveBean.SubjectsBean> subjectsBeans) {
                Log.d(TAG, "RetrofitActivity_27-->_onNext: " + subjectsBeans.size());
            }

            @Override
            protected void _onComplete() {

            }


            @Override
            protected void _onError(String message) {
                Log.e(TAG, "RetrofitActivity_34-->_onError: " + message);
            }
        });
//        weather = apiService.getWeather("101010100");
    }

}
