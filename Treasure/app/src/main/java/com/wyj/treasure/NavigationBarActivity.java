package com.wyj.treasure;


import com.wyj.navigationbar.DefaultNavigationBar;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

/**
 * @author wangyujie
 * @date 2018/8/14.18:13
 * @describe 自定义标题栏
 */
public class NavigationBarActivity extends BaseActivity {


    @Override
    protected int initView() {
        return 0;
    }

    @Override
    protected int contentView() {
        return  R.layout.activity_navigation_bar;
    }

    @Override
    protected void initData() {


        DefaultNavigationBar builder = new DefaultNavigationBar.Builder(this, findViewById(R.id.view_group))
                .setTitle("标题")
                .setRightTitle("右边")
                .setRightClickListener(v -> ToastUtil.show("右边"))
                .builder();
    }

}
