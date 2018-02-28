package com.wyj.treasure.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.fragment.BaseFragment;
import com.wyj.treasure.fragment.DashboardFragment;
import com.wyj.treasure.fragment.HomeFragment;
import com.wyj.treasure.fragment.NotificationsFragment;

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

    public void other() {
        View layoutView = View.inflate(this, R.layout.activity_main, null);
        layoutView = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        layoutView = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
    }
}
