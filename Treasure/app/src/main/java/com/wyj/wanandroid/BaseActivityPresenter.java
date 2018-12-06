package com.wyj.wanandroid;

import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author wangyujie
 * @date 2018/11/26.15:24
 * @describe BasePresenter
 */
public class BaseActivityPresenter<V extends MVPBaseActivity> {
    protected V mActivity;


    public <E> LifecycleTransformer<E> bindUntilDestroy() {
        return mActivity.bindUntilEvent(ActivityEvent.DESTROY);
    }

    public void attachView(V view) {
        mActivity = view;
    }

    public void detachView() {
        if (mActivity != null) {
            mActivity = null;
        }
    }
}
