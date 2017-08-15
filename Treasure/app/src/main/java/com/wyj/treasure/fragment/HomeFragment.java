package com.wyj.treasure.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.DongTaiActivity;
import com.wyj.treasure.activity.MeiTuanRobustActivity;
import com.wyj.treasure.activity.NetworkChangeActivity;
import com.wyj.treasure.activity.ServiceActivity;
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


    private String[] TITLE = {"动态添加布局", "广播接收器", "后台服务", "美团热修复"};
    private Class<?>[] ACTIVITY = {DongTaiActivity.class, NetworkChangeActivity.class, ServiceActivity.class, MeiTuanRobustActivity.class};
    private List<HomeItem> mDataList;


    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void processData() {

    }

    @Override
    protected void initData() {
        super.initData();
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dealWithData();
        HomeAdapter homeAdapter = new HomeAdapter(R.layout.adapter_item_home, mDataList);
        homeAdapter.openLoadAnimation();
        homeAdapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(getActivity(), ACTIVITY[position]);
            startActivity(intent);
        });
        rvList.setAdapter(homeAdapter);

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
