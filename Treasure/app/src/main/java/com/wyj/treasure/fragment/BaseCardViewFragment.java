package com.wyj.treasure.fragment;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.BaseListAdapter;
import com.wyj.treasure.mode.ItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseCardViewFragment extends BaseFragment {

    @BindView(R.id.recycle)
    RecyclerView recycle;
    Unbinder unbinder;
    protected BaseListAdapter adapter;
    protected List<ItemInfo> mData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    Unbinder unbinder1;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_notifications, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    protected void initData() {
        super.initData();
        toolbar.setNavigationIcon(null);
        mData = new ArrayList<>();
        adapter = new BaseListAdapter(getActivity());
        recycle.setAdapter(adapter);
        recycle.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter.setOnClickListener(position -> {
            Intent intent = new Intent(getActivity(), mData.get(position).getClz());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            } else {
                startActivity(intent);
            }
        });
    }

    protected void notifyDataChanged() {
        adapter.setData(mData);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void processData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
