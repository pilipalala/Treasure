package com.wyj.treasure.mvp.presenter;

import com.wyj.treasure.mvp.LoginContract;
import com.wyj.treasure.mvp.MVPActivity;
import com.wyj.treasure.mvp.model.LoginModel;

/**
 * @author wangyujie
 *         on 2018/3/12.10:09
 *         TODO
 */

public class LoginPresenter extends BasePresenter<LoginModel,MVPActivity> implements LoginContract.LoginPresenter {
    @Override
    public LoginModel loadMode() {

        return new LoginModel();
    }

    @Override
    public void login(String name, String pwd) {
        if (!getIView().checkNull()) {
            if (getiModel().login(name, pwd)) {
                getIView().loginSuccess();
            } else {
                getIView().loginFail("密码或者账号不对");
            }
        }
    }
}
