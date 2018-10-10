package com.wyj.mvp.model;

import com.wyj.mvp.entity.girl.User;

/**
 * Created by wangyujie
 * on 2017/9/22.17:31
 * TODO
 */

public interface IUserBiz {
    void login(String userName, String password, OnLoginListener onLoginListener);
    interface OnLoginListener {
        void loginSuccess(User user);
        void loginFailed();
    }
}
