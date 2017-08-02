package com.wyj.treasure.fragment;


import android.view.LayoutInflater;
import android.view.View;

import com.wyj.treasure.R;

public class NotificationsFragment extends BaseFragment {

    @Override
    protected View initView() {
        return LayoutInflater.from(mContext).inflate(R.layout.fragment_notifications, null);
    }

    @Override
    protected void initData() {
        super.initData();

    }
}
