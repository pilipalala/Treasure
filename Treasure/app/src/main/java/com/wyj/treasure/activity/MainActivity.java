package com.wyj.treasure.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.wyj.treasure.R;
import com.wyj.treasure.fragment.BaseFragment;
import com.wyj.treasure.fragment.DashboardFragment;
import com.wyj.treasure.fragment.HomeFragment;
import com.wyj.treasure.fragment.NotificationsFragment;
import com.wyj.treasure.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.navigation)
    BottomNavigationView navigation;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initNotification();

    }

    /**
     * 获取通知栏权限
     * 如果没有打开通知权限则跳到设置界面
     */
    public void initNotification() {
        boolean enabled = MyUtils.isNotificationEnabled(this);
        if (!enabled) {
            new AlertDialog.Builder(this).setTitle("提示").setMessage("请开启通知栏权限！").setNegativeButton("不去", null).setPositiveButton("好", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MyUtils.toSetting(MainActivity.this);
                }
            }).show();
        }
    }

    private void initData() {
        initragment();
        check(0);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    private void initragment() {
        mBaseFragment = new ArrayList<>();
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        notificationsFragment = new NotificationsFragment();
        mBaseFragment.add(homeFragment);
        mBaseFragment.add(dashboardFragment);
        mBaseFragment.add(notificationsFragment);
    }
}
