package com.wyj.treasure.mvp;

import com.wyj.treasure.mvp.presenter.BasePresenter;
import com.wyj.treasure.mvp.view.BaseView;

/**
 * Created by wangyujie
 * Date 2018/3/10
 * Time 21:40
 * TODO
 */

public class GetPhoneInfoContract {
    public interface view extends BaseView<Presenter> {
        void setTime(String time);

        void showLoading();

        void hideLoading();
    }

    public interface Presenter extends BasePresenter {

        void getTime();
    }
}
