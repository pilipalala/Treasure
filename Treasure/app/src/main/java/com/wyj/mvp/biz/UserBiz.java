package com.wyj.mvp.biz;

import com.wyj.mvp.bean.User;

/**
 * Created by wangyujie
 * on 2017/9/22.17:33
 * TODO
 */

public class UserBiz implements IUserBiz {
    @Override
    public void login(String userName, String password, OnLoginListener onLoginListener) {
        new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if ("wyj".equals(userName) && "123".equals(password)) {
                    User user = new User();
                    user.setUsername(userName);
                    user.setPassword(password);
                    onLoginListener.loginSuccess(user);
                } else {
                    onLoginListener.loginFailed();
                }

            }
        }.start();

    }
}
