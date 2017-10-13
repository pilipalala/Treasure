package com.wyj.mvp.biz;

/**
 * Created by wangyujie
 * on 2017/9/22.17:31
 * TODO
 */

public interface IUserBiz {
    public void login(String userName, String password, OnLoginListener onLoginListener);
}
