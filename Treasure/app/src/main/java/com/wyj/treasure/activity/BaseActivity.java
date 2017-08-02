package com.wyj.treasure.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wangyujie
 * Date 2017/7/31
 * Time 21:08
 * TODO
 */

public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }


    /**
     * 布局
     *
     * @return
     */
    protected abstract int initView();


    /**
     * 加载数据
     */
    protected abstract void initData();
}
