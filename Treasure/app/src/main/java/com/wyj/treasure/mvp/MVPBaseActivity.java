package com.wyj.treasure.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.mvp.presenter.BasePresenter;
import com.wyj.treasure.mvp.view.IView;

/**
 * Created by wangyujie
 * on 2017/10/18.9:56
 * TODO
 */

public abstract class MVPBaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getView();

        mPresenter = createPresenter();
        initCommonData();


    }

    /**
     * 创建Presenter
     */
    protected abstract P createPresenter();

    protected void initCommonData() {
        /*内存泄漏*/
        /*关联view*/
        if (mPresenter != null) {

            mPresenter.attachView(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*解除关联*/
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    public abstract void getView();

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
