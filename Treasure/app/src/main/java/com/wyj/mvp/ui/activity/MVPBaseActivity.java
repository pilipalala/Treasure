package com.wyj.mvp.ui.activity;

import com.wyj.mvp.presenter.BasePresenter;
import com.wyj.mvp.view.IView;
import com.wyj.treasure.activity.BaseActivity;


/**
 * Created by wangyujie
 * on 2017/10/18.9:56
 * TODO MVP 的 BaseActivity
 */


public abstract class MVPBaseActivity<P extends BasePresenter> extends BaseActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = createPresenter();
            attachView();
        }
    }


    /**
     * 第一步 创建Presenter
     */
    protected abstract P createPresenter();

    /**
     * 第二步
     */
    private void attachView() {
        /*关联view*/
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
