package com.wyj.treasure.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.tv_home)
    TextView tvHome;
    Unbinder unbinder;

    @Override
    protected View initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        super.initData();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_home)
    public void onViewClicked() {

    }
}
