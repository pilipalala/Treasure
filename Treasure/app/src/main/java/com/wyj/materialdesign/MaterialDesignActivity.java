package com.wyj.materialdesign;

import com.wyj.materialdesign.drawerlayout.DrawerLayoutMainActivity;
import com.wyj.materialdesign.recyclerview.BottomSheetActivity;
import com.wyj.materialdesign.recyclerview.PaletteActivity;
import com.wyj.materialdesign.recyclerview.RecyclerRefreshActivity;
import com.wyj.materialdesign.recyclerview.StyleActivity;
import com.wyj.materialdesign.recyclerview.XiTuActivity;
import com.wyj.materialdesign.toolbar.TabLayoutMainActivity;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

public class MaterialDesignActivity extends BaseActivity {
    @Override
    protected int initView() {
        return DEFATE_VIEW;
    }

    @Override
    protected void initData() {
        setTitle("MaterialDesign 样式");
    }

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("抽屉DrawerLayout", DrawerLayoutMainActivity.class, 0));
        mData.add(new ItemInfo("BottomSheet效果", BottomSheetActivity.class, 0));
        mData.add(new ItemInfo("Palette", PaletteActivity.class, 0));
        mData.add(new ItemInfo("仿XiTu个人中心", XiTuActivity.class, 0));
        mData.add(new ItemInfo("5.0新样式", StyleActivity.class, 0));
        mData.add(new ItemInfo("RecyclerView刷新", RecyclerRefreshActivity.class, 0));
        mData.add(new ItemInfo("TabLayout", TabLayoutMainActivity.class, 0));
        return super.getListData();
    }
}
