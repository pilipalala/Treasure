package com.wyj.mvp.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.wyj.mvp.ui.fragment.ZhiHuFragment;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.adapter.FragmentViewPagerAdapter;

import java.util.ArrayList;

import butterknife.BindView;

public class ZhiHuDailyActivity extends BaseActivity implements ZhiHuFragment.CallBackValue {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.tab_viewpager)
    ViewPager tabViewpager;
    private ArrayList<Fragment> fragments;
    private String[] tabTitles = new String[]{"日报", "头条", "收藏"};
    private FragmentViewPagerAdapter adapter;

    @Override
    public boolean isStartAnimation() {
        return false;
    }

    @Override
    protected int initView() {
        return 0;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_zhi_hu_daily;
    }

    @Override
    protected void initData() {
        toolbar.setNavigationOnClickListener(v -> finish());
        fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(new ZhiHuFragment(i));
        }
        adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments, tabTitles);
        tabViewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(tabViewpager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });
    }

    @Override
    public void SendMessageValue(String strValue) {
        toolbar.setTitle("知乎日报-" + strValue);
    }

}
