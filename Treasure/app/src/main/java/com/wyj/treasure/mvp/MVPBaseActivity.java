package com.wyj.treasure.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.mvp.presenter.GetPhoneInfoPresenter;

/**
 * Created by wangyujie
 * on 2017/10/18.9:56
 * TODO
 */

public abstract class MVPBaseActivity extends AppCompatActivity implements GetPhoneInfoContract.view {

    private GetPhoneInfoContract.Presenter mPresenter;

    @Override
    public void setTime(String time) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetPhoneInfoPresenter(this);

    }
    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*解除关联*/
    }

    @Override
    public void setPresenter(GetPhoneInfoContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
