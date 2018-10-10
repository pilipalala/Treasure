package com.wyj.mvp.presenter;

import com.wyj.mvp.view.IView;
import com.wyj.mvp.model.IMode;

import java.lang.ref.WeakReference;

/**
 * Created by wangyujie
 * on 2017/10/18.10:00
 * TODO
 * http://www.imooc.com/article/79004?block_id=tuijian_wz
 */

public abstract class BasePresenter<M extends IMode, V extends IView> implements IPresenter {
    protected WeakReference mViewRef;
//    protected V iVIew;
//    protected M iMode;

    //第一步 创建 presenter 对象
    public BasePresenter() {
    }

    /*内存泄漏*/
    //第二步 presenter 关联 view
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
        if (getIModel() != null) {
            getIModel().requestCancel();
        }
    }

    @Override
    public V getIView() {
        return isViewAttached() ? (V) mViewRef.get() : null;
    }

    @Override
    public M getIModel() {
        return loadMode();

    }

    public abstract M loadMode();

    /**
     * 判断是否与View建立了关联
     *
     * @return 建立则返回true
     */
    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

}
