package com.wyj.mvp.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wyj.mvp.ui.adapter.GirlAdapter;
import com.wyj.mvp.entity.girl.Girl;
import com.wyj.mvp.presenter.GirlPresenter;
import com.wyj.mvp.view.IGirlView;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;
import com.wyj.treasure.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

public class GirlListActivity extends BaseActivity implements IGirlView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_girl)
    RecyclerView rvGirl;
    @BindView(R.id.tv_title)
    TextView tvTitle;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_girl_list;
    }

    @Override
    protected void initData() {
        tvTitle.setText("MVP_DEMO");
        toolbar.setNavigationOnClickListener(v -> finish());
        /*中间者 让他触发加载数据*/
        new GirlPresenter(this).fetch();
    }

    @Override
    public void showLoading() {
        ToastUtil.show("正在加载中.....");
    }

    @Override
    public void showGirls(List<Girl> girls) {
        rvGirl.setAdapter(new GirlAdapter(this));
        rvGirl.setLayoutManager(new LinearLayoutManager(this));
    }
}
