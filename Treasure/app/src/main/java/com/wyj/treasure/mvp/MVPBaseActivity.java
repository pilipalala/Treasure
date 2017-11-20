package com.wyj.treasure.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.mvp.presenter.BasePresenter;

/**
 * Created by wangyujie
 * on 2017/10/18.9:56
 * TODO
 */

public abstract class MVPBaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*创建Presenter*/
        mPresenter = createPresenter();
        /*内存泄漏*/
        /*关联view*/
        mPresenter.attachView((V)this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*解除关联*/
        mPresenter.detachView();
    }

    /**
     * @return
     */
    protected abstract T createPresenter();
}
