package com.wyj.treasure.fragment;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.ReflectActivity;
import com.wyj.treasure.activity.SwipeDeleteActivity;
import com.wyj.treasure.mode.ItemInfo;

public class DashboardFragment extends BaseCardViewFragment {

    @Override
    protected void processData() {
        mData.add(new ItemInfo("Java 反射机制", ReflectActivity.class, R.mipmap.ic_launcher));
        mData.add(new ItemInfo("Recycleview 滑动删除", SwipeDeleteActivity.class, R.mipmap.ic_launcher));
        notifyDataChanged();

    }

}
