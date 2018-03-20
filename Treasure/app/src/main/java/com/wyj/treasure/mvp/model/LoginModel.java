package com.wyj.treasure.mvp.model;

/**
 * @author wangyujie
 *         on 2018/3/12.10:09
 *         TODO
 */

public class LoginModel implements IMode {
    public boolean login(String username, String pwd) {
        boolean isLogin = false;
        isLogin = "wyj".equals(username) && "123".equals(pwd);
        return isLogin;
    }
}
