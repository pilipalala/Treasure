package com.wyj.mvp.presenter;


import com.wyj.mvp.contract.LoginContract;
import com.wyj.mvp.ui.activity.MVPLoginActivity;
import com.wyj.mvp.model.LoginModel;

/**
 * @author wangyujie
 *         on 2018/3/12.10:09
 *         TODO
 */

public class LoginPresenter extends BasePresenter<LoginModel,MVPLoginActivity> implements LoginContract.LoginPresenter {
    @Override
    public LoginModel loadMode() {
        return new LoginModel();
    }

    @Override
    public void login(String name, String pwd) {
        if (!getIView().checkNull()) {
            if (getIModel().login(name, pwd)) {
                getIView().loginSuccess();
            } else {
                getIView().loginFail("密码或者账号不对");
            }
        }
    }
}
