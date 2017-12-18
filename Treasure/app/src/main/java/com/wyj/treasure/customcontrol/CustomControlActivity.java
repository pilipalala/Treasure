package com.wyj.treasure.customcontrol;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomControlActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_custom_control);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> finish());
        tvTitle.setText("自定义控件三部曲");

    }

    @OnClick({R.id.btn_anima, R.id.btn_valueanimator,R.id.btn_objectanimator})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.btn_anima:
                intent.setClass(CustomControlActivity.this, AnimationActivity.class);
                break;
            case R.id.btn_valueanimator:
                intent.setClass(CustomControlActivity.this, ValueAnimatorActivity.class);
                break;
            case R.id.btn_objectanimator:
                intent.setClass(CustomControlActivity.this, ObjectanimatorActivity.class);
                break;
        }
        startActivity(intent);
    }
}
