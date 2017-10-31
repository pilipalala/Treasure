package com.wyj.treasure.mdcustom.behaviorcustom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.R;

public class BehaviorActivity extends AppCompatActivity {
    /**
     * CoordinateLayout + Behavior
     * <p>
     * NestedScrollingParent
     * 可以监听所有子控件或者子容器的事件
     */

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_behavior);
    }
}
