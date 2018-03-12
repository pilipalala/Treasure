package com.wyj.treasure.mvp;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.mvp.presenter.LoginPresenter;
import com.wyj.treasure.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MVPActivity extends MVPBaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.input_email)
    EditText inputEmail;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;
    @BindView(R.id.link_signup)
    TextView linkSignup;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void getView() {
        setContentView(R.layout.activity_mvp);
        ButterKnife.bind(this);
    }

    @Override
    public String getUserName() {
        return inputEmail.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return inputPassword.getText().toString().trim();
    }

    @Override
    public void loginSuccess() {
        ToastUtil.show("成功");
        startActivity(new Intent(this, GirlListActivity.class));
    }

    @Override
    public void loginFail(String failMsg) {
        ToastUtil.show(failMsg);

    }

    public boolean checkNull() {
        boolean isNull = false;
        if (TextUtils.isEmpty(getUserName())) {
            inputEmail.setError("账号不能为空");
            isNull = true;
        } else if (TextUtils.isEmpty(getPwd())) {
            inputPassword.setError("密码不能为空");
            isNull = true;
        }
        return isNull;
    }

    @OnClick(R.id.btn_login)
    public void onViewClicked() {
        mPresenter.login(getUserName(), getPwd());
    }
}
