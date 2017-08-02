package com.wyj.treasure.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.wyj.treasure.R;

public class DashboardFragment extends BaseFragment {

    @Override
    protected View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_dashboard,null);
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
