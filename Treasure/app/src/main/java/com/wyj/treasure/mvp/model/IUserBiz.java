package com.wyj.treasure.mvp.model;

import com.wyj.treasure.mvp.bean.User;

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
