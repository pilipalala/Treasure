package com.wyj.treasure.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @author wangyujie
 * @date 2018/9/17.17:29
 * @describe 懒加载
 */
public abstract  class CustomerBaseFragment extends Fragment {

    public Context mContext;

    /**
     * 得到上下文
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return initView(inflater);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 强制子类重写，实现子类特有的ui
     */

    protected abstract View initView(LayoutInflater inflater);
    /*当子类需要初始化数据，或者联网请求绑定数据，展示数据的 */
    protected void initData() {
    }
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }

    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {

    }

    /**
     * 延迟加载
     * 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

