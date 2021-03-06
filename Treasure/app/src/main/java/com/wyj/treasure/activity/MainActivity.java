package com.wyj.treasure.activity;

import android.Manifest;
import android.content.Intent;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.wyj.network.NetWorkService;
import com.wyj.treasure.R;
import com.wyj.treasure.fragment.BaseFragment;
import com.wyj.treasure.fragment.DashboardFragment;
import com.wyj.treasure.fragment.HomeFragment;
import com.wyj.treasure.fragment.NotificationsFragment;
import com.wyj.treasure.utils.ActivityCollector;
import com.wyj.treasure.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
    private long mExitTime = 0L;
    private Fragment mFragment;
    private List<BaseFragment> mBaseFragment;
    private HomeFragment homeFragment;
    private DashboardFragment dashboardFragment;
    private NotificationsFragment notificationsFragment;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                check(0);
                return true;
            case R.id.navigation_dashboard:
                check(1);
                return true;
            case R.id.navigation_notifications:
                check(2);
                return true;
        }
        return false;
    };

    private void check(int position) {
        BaseFragment to = getFragment(position);
        switchFragment(mFragment, to);
    }

    public BaseFragment getFragment(int position) {
        BaseFragment baseFragment = mBaseFragment.get(position);
        return baseFragment;
    }

    /**
     * @param from 刚显示的Fragment。马上就要被隐藏
     * @param to   马上要切换的Fragment。 一会要显示
     */
    private void switchFragment(Fragment from, Fragment to) {
        if (from != to) {//才切换
            mFragment = to;
            /**
             * 开启一个事务
             * */
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (!to.isAdded()) {//to没被添加
                //隐藏from
                if (from != null) {
                    transaction.hide(from);
                }
                //添加to
                if (to != null) {
                    /**
                     * 提交事务commit
                     * */
                    transaction.add(R.id.content, to).commit();
                }
            } else {
                //隐藏from
                if (from != null) {
                    transaction.hide(from);
                }
                //显示to
                if (to != null) {
                    transaction.show(to).commit();
                }
            }
        }
    }

    @Override
    protected int getContentViewID() {
        return EMPTY_VIEW;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_main;
    }


    @Override
    protected void initData() {
        initFragment();
        check(0);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        requestRuntimePermission(new String[]{
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
        }, new PermissionListener() {
            @Override
            public void onGranted() {
                //开始Service监听网络状态的变化，当网络有变化时发送广播通知
                startService(new Intent(mContext, NetWorkService.class));
            }

            @Override
            public void onDenied(List<String> deniedPermissions, List<String> unRationalePermissions) {

            }
        });
    }

    private void initFragment() {
        mBaseFragment = new ArrayList<>();
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        mBaseFragment.add(homeFragment);
        mBaseFragment.add(dashboardFragment);
        mBaseFragment.add(notificationsFragment);
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - mExitTime) > 2000) {
            ToastUtil.show("再按一次退出");
            mExitTime = System.currentTimeMillis();
        } else {
            ActivityCollector.AppExit(this);
            super.onBackPressed();
        }
    }
}
