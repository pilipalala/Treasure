package com.wyj.treasure.fragment;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.wyj.baseadapter.RecyclerViewActivity;
import com.wyj.process.ProcessActivity;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.ChangeLaucherActivity;
import com.wyj.treasure.activity.DongTaiActivity;
import com.wyj.treasure.activity.GridLayoutManagerActivity;
import com.wyj.treasure.activity.MyWebViewActivity;
import com.wyj.treasure.activity.NetworkChangeActivity;
import com.wyj.treasure.activity.PullUpToLoadMoreActivity;
import com.wyj.treasure.activity.ServiceActivity;
import com.wyj.treasure.activity.TinkerActivity;
import com.wyj.treasure.activity.transition.TransitionsActivity;
import com.wyj.treasure.adapter.HomeAdapter;
import com.wyj.treasure.mode.HomeItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class HomeFragment extends BaseFragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder1;
    private ComponentName componentName;
    private ComponentName componentNameDefault;
    private PackageManager packageManager;

    private String[] TITLE = {
            "动态添加布局", "广播接收器",
            "后台服务", "微信热修复", "过渡动画",
            "RecyclerView通过GridLayoutManager实现多样式布局",
            "向上拖动查看图文详情控件", "网页交互",
            "万能adapter", "动态更换图片",
            "多进程通信"};
    private Class<?>[] ACTIVITY = {
            DongTaiActivity.class, NetworkChangeActivity.class,
            ServiceActivity.class, TinkerActivity.class,
            TransitionsActivity.class, GridLayoutManagerActivity.class,
            PullUpToLoadMoreActivity.class, MyWebViewActivity.class,
            RecyclerViewActivity.class, ChangeLaucherActivity.class,
            ProcessActivity.class};
    private List<HomeItem> mDataList;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void processData() {
        packageManager = getActivity().getApplicationContext().getPackageManager();
        //得到此activity的全限定名
        componentNameDefault = getActivity().getComponentName();
        componentName = new ComponentName(
                getActivity().getBaseContext(),
                "com.wyj.treasure.MainAliasActivity");

    }

    @Override
    protected void initData() {
        super.initData();
        toolbar.setNavigationIcon(null);
        /*android:layoutAnimation="@anim/anim_layout"*/
        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_item);
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.5f);
        controller.setAnimation(animation);
        controller.setOrder(LayoutAnimationController.ORDER_RANDOM);
        rvList.setLayoutAnimation(controller);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dealWithData();
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.adapter_item_home, mDataList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setOnItemClickListener((adapter, view, position) -> {

            if (TITLE[position].equals("动态更换图片")) {
                disableComponent(componentNameDefault);
                enableComponent(componentName);
            }
            Intent intent = new Intent(getActivity(), ACTIVITY[position]);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                startActivity(intent);
            }
        });
        rvList.setAdapter(homeAdapter);

    }

    /**
     * 启动组件
     *
     * @param componentName 组件名
     */
    private void enableComponent(ComponentName componentName) {
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);
    }

    /**
     * 禁用组件
     *
     * @param componentName 组件名
     */
    private void disableComponent(ComponentName componentName) {
        packageManager.setComponentEnabledSetting(componentName,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void dealWithData() {
        mDataList = new ArrayList<>();
        for (int i = 0; i < TITLE.length; i++) {
            HomeItem item = new HomeItem();
            item.setActivity(ACTIVITY[i]);
            item.setTitle(TITLE[i]);
            mDataList.add(item);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
