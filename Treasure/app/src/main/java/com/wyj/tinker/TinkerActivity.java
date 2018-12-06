package com.wyj.tinker;

import android.widget.Button;
import android.widget.EditText;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TinkerActivity extends BaseActivity {

    @BindView(R.id.btn_permission)
    Button btnPermission;
    @BindView(R.id.et_error)
    EditText mEtError;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_permission)
    public void onViewClicked() {
        mEtError.setError("这是一条错误信息");
    }
}
