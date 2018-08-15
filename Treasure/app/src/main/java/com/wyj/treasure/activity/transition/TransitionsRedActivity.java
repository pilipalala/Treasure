package com.wyj.treasure.activity.transition;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransitionsRedActivity extends BaseActivity {

    @Override
    public boolean isStartAnimation() {
        return false;
    }

    @Override
    protected int initView() {
        return R.layout.activity_transitions_red;
    }

    @Override
    protected void initData() {
        setTitle("这是个过渡动画");
    }

}
