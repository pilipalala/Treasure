package com.wyj.treasure;

import android.view.View;
import android.view.ViewGroup;

import com.wyj.navigationbar.DefaultNavigationBar;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

public class NavigationBarActivity extends BaseActivity {


    @Override
    protected void initView() {
        setContentView(R.layout.activity_navigation_bar);
    }

    @Override
    protected void initData() {


        DefaultNavigationBar builder = new DefaultNavigationBar.Builder(this, findViewById(R.id.view_group))
                .setTitle("标题")
                .setRightTitle("右边")
                .setRightClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtil.show("右边");

                    }
                })
                .builder();
    }

}
