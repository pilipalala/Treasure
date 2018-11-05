package com.wyj.mvp.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.wyj.mvp.entity.girl.User;
import com.wyj.mvp.view.IUserLoginView;
import com.wyj.mvp.presenter.UserLoginPresenter;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

public class UserLoginActivity extends BaseActivity implements IUserLoginView {
    private EditText mEtUsername, mEtPassword;
    private Button mBtnLogin, mBtnClear;
    private ProgressBar mPbLoading;
    private UserLoginPresenter mUserLoginPresenter = new UserLoginPresenter(this);


    @Override
    protected int getContentViewID() {
        return R.layout.activity_user_login;

    }

    @Override
    protected void initData() {
        initViews();
    }

    private void initViews() {
        mEtUsername = findViewById(R.id.id_et_username);
        mEtPassword = findViewById(R.id.id_et_password);

        mBtnClear = findViewById(R.id.id_btn_clear);
        mBtnLogin = findViewById(R.id.id_btn_login);

        mPbLoading = findViewById(R.id.id_pb_loading);
        mBtnLogin.setOnClickListener(v -> {
            mUserLoginPresenter.login();
        });
        mBtnClear.setOnClickListener(v -> mUserLoginPresenter.clear());
    }

    @Override
    public String getUserName() {
        return mEtUsername.getText().toString();
    }

    @Override
    public String getPassword() {
        return mEtPassword.getText().toString();
    }

    @Override
    public void clearUserName() {
        mEtUsername.setText("");
    }

    @Override
    public void clearPassword() {
        mEtPassword.setText("");
    }

    @Override
    public void showLoading() {
        mPbLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mPbLoading.setVisibility(View.GONE);
    }

    @Override
    public void toMainActivity(User user) {
        ToastUtil.show("login onDetailSuccess , " +
                user.getUsername()+"to BusActivity");
        startActivity(new Intent(this, MVPLoginActivity.class));
    }

    @Override
    public void showFailedError() {
        ToastUtil.show("login failed");
    }
}
