package com.wyj.treasure.mdcustom.behaviorcustom;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

public class BehaviorActivity extends BaseActivity {
    /**
     * CoordinateLayout + Behavior
     * <p>
     * NestedScrollingParent
     * 可以监听所有子控件或者子容器的事件
     */
    @Override
    protected void initView() {
        setContentView(R.layout.activity_behavior);
    }

    @Override
    protected void initData() {

    }
}
