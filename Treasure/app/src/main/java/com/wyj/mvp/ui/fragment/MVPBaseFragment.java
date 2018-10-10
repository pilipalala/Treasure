package com.wyj.mvp.ui.fragment;


import com.wyj.mvp.presenter.BasePresenter;
import com.wyj.mvp.view.IView;
import com.wyj.treasure.fragment.CustomerBaseFragment;

/**
 * @author wangyujie
 * @date 2018/10/8.14:25
 * @describe 添加描述
 */
public abstract class MVPBaseFragment<P extends BasePresenter> extends CustomerBaseFragment implements IView{
    protected P mPresenter;

    @Override
    public void onStart() {
        super.onStart();
        if (mPresenter == null) {
            mPresenter = createPresenter();
            attachView();
        }
    }

    @Override
    protected void initData() {

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
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
