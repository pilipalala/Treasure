package com.wyj.treasure.activity.transition;

import android.content.Intent;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;

import butterknife.OnClick;

public class TransitionsActivity extends BaseActivity {

    @Override
    protected int getContentViewID() {
        return R.layout.activity_transitions;
    }

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("跳转", TransitionsSlideActivity.class, 0));
        mData.add(new ItemInfo("RecycleView过渡动画跳转", TransitionsListActivity.class, 0));
        return super.getListData();
    }

    @Override
    protected void initData() {
        setTitle("属性动画");
    }

}
