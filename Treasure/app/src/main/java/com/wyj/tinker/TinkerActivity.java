package com.wyj.tinker;

import android.widget.Button;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class TinkerActivity extends BaseActivity {

    @BindView(R.id.btn_permission)
    Button btnPermission;

    @Override
    protected int getContentViewID() {
        return R.layout.activity_tinker;
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_permission)
    public void onViewClicked() {

    }

}
