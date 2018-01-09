package com.wyj.navigationbar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by wangyujie
 * Date 2018/1/9
 * Time 22:46
 * TODO 头部的Builder基类
 */

public abstract class AbsNavigationBar implements INavigationBar {

    private Builder.AbsNavigationParams mPramas;

    public AbsNavigationBar(Builder.AbsNavigationParams mPramas) {
        this.mPramas = mPramas;
        createAndBindView();
    }

    /**
     * 绑定和创建view
     */
    private void createAndBindView() {
        //1、创建view
        View navigationView = LayoutInflater.from(mPramas.mContext).inflate(bindLayoutId(), mPramas.mParent, false);
        //2、添加
        mPramas.mParent.addView(navigationView, 0);
        applyView();

    }

    public static abstract class Builder {

        AbsNavigationParams P;

        public Builder(Context context, ViewGroup parent) {
            P = new AbsNavigationParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationParams {
            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;

            }
        }

    }
}
