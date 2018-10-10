package com.wyj.treasure.activity.transition;


import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;


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
