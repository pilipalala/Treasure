package com.wyj.treasure.mvp.presenter;

import com.wyj.treasure.mvp.model.IMode;
import com.wyj.treasure.mvp.view.IView;

import java.lang.ref.WeakReference;

/**
 * Created by wangyujie
 * on 2017/10/18.10:00
 * TODO
 */

public abstract class BasePresenter<M extends IMode, V extends IView> implements IPresenter {
    protected WeakReference mViewRef;
    protected V iVIew;
    protected M iMode;


    @Override
    public void attachView(IView view) {
        mViewRef = new WeakReference(view);
    }

    /**
     * 解除管理
     */

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    @Override
    public V getIView() {
        return (V) mViewRef.get();
    }

    @Override
    public M getiModel() {
        return loadMode();

    }

    public abstract M loadMode();
}
