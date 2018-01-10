package com.wyj.navigationbar;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by wangyujie
 * Date 2018/1/9
 * Time 22:46
 * TODO 头部的Builder基类
 */

public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationParams> implements INavigationBar {


    private P mPramas;
    private View navigationView;

    public AbsNavigationBar(P mPramas) {
        this.mPramas = mPramas;
        createAndBindView();
    }

    public P getPramas() {
        return mPramas;
    }

    public void setText(int viewId, String mTitle) {
        TextView textView = findViewById(viewId);
        if (!TextUtils.isEmpty(mTitle)) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(mTitle);
        }
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        if (listener != null) {
            findViewById(viewId).setOnClickListener(listener);
        }
    }

    public <T extends View> T findViewById(int viewId) {
        View view = navigationView.findViewById(viewId);
        return (T) view;
    }

    /**
     * 绑定和创建view
     */
    private void createAndBindView() {
        //1、创建view
        navigationView = LayoutInflater.from(mPramas.mContext).inflate(bindLayoutId(), mPramas.mParent, false);
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
