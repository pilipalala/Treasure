package com.wyj.treasure.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

<<<<<<< HEAD
import com.wyj.treasure.mvp.presenter.BasePresenter;
import com.wyj.treasure.mvp.view.IView;
=======
import com.wyj.treasure.mvp.presenter.GetPhoneInfoPresenter;
>>>>>>> 217013f2994725881ac553c0bd8558de83922090

/**
 * Created by wangyujie
 * on 2017/10/18.9:56
 * TODO
 */

<<<<<<< HEAD
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
=======
public abstract class MVPBaseActivity extends AppCompatActivity implements GetPhoneInfoContract.view {

    private GetPhoneInfoContract.Presenter mPresenter;

    @Override
    public void setTime(String time) {

    }

    @Override
    public void showLoading() {
>>>>>>> 217013f2994725881ac553c0bd8558de83922090

            mPresenter.attachView(this);
        }
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
<<<<<<< HEAD
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    public abstract void getView();

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

=======
    }

    @Override
    public void setPresenter(GetPhoneInfoContract.Presenter presenter) {
        mPresenter = presenter;
>>>>>>> 217013f2994725881ac553c0bd8558de83922090
    }
}
