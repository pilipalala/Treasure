package com.wyj.treasure.customcontrol;


import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.mode.ItemInfo;

import java.util.List;


public class CustomControlActivity extends BaseActivity {



    @Override
    protected int initView() {
        return R.layout.activity_custom_control;
    }

    @Override
    protected void initData() {
        setTitle("自定义控件三部曲");
    }

    @Override
    protected List<ItemInfo> getListData() {
        mData.add(new ItemInfo("动画篇",AnimationActivity.class , 0));
        mData.add(new ItemInfo("ValueAnimator",ValueAnimatorActivity.class , 0));
        mData.add(new ItemInfo("ObjectAnimator",ObjectanimatorActivity.class , 0));
        mData.add(new ItemInfo("PropertyValuesHolder与Keyframe",PropertyActivity.class , 0));
        mData.add(new ItemInfo("AnimatorSet",AnimatorSetActivity.class , 0));
        mData.add(new ItemInfo("绘图篇-概述及基本几何图形绘制",DrawingArticlesOneActivity.class , 0));
        mData.add(new ItemInfo("绘图篇-路径及文字",DrawingArticlesTwoActivity.class , 0));
        mData.add(new ItemInfo("区域(Range)",DrawingArticlesRangeActivity.class , 0));
        mData.add(new ItemInfo("drawText()详解",DrawingArticlesDrawTextActivity.class , 0));
        mData.add(new ItemInfo("贝赛尔曲线",BezierActivity.class , 0));
        return super.getListData();
    }
}
