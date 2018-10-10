package com.wyj.mvp.view;

import com.wyj.mvp.entity.girl.User;

/**
 * Created by wangyujie
 * on 2017/9/22.17:37
 * TODO
 */

public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();
}
