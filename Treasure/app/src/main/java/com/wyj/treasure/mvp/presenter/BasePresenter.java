package com.wyj.treasure.mvp.presenter;

/**
 * Created by wangyujie
 * on 2017/10/18.10:00
 * TODO
 */

//public class BasePresenter<T> {
//    protected WeakReference<T> mViewRef;
//    public void attachView(T view) {
//        mViewRef = new WeakReference<T>(view);
//    }
//
//    /**
//     * 解除管理
//     */
//    public void detachView() {
//        if (mViewRef != null) {
//            mViewRef.clear();
//            mViewRef = null;
//        }
//    }
//
//    protected T getView() {
//        return mViewRef.get();
//    }
//}
public interface BasePresenter {
    void start();
}