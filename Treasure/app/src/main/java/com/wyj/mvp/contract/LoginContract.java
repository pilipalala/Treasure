package com.wyj.mvp.contract;

/**
 * @author wangyujie
 *         on 2018/3/12.10:11
 *         TODO
 */

public class LoginContract {
    public interface LoginView {
        String getUserName();

        String getPwd();

        void loginSuccess();

        void loginFail(String failMsg);
    }


    public interface LoginPresenter {
        void login(String name, String pwd);
    }
}
