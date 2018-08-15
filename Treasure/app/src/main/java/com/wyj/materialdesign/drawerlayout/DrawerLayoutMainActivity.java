package com.wyj.materialdesign.drawerlayout;

import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

public class DrawerLayoutMainActivity extends BaseActivity {

    @Override
    protected int initView() {
        return DEFATE_VIEW;
    }

    @Override
    protected void initData() {
        setTitle("抽屉DrawerLayout");
    }

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("基本DrawerLayout+NavigationView", DrawerLayoutDemoActivity.class, 0));
        mData.add(new ItemInfo("NavigationView在Toolbar下方", DrawerLayout_BelowToolbarActivity.class, 0));
        mData.add(new ItemInfo("DrawerLayout+其他布局", DrawerLayout_OtherActivity.class, 0));
        return super.getListData();
    }
}
