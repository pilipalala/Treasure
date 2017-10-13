package com.wyj.mvp.biz;

import com.wyj.mvp.bean.User;

/**
 * Created by wangyujie
 * on 2017/9/22.17:33
 * TODO
 */

public interface OnLoginListener {
    void loginSuccess(User user);

    void loginFailed();
}
