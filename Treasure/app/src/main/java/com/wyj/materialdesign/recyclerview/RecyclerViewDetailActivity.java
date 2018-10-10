package com.wyj.materialdesign.recyclerview;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import butterknife.BindView;

public class RecyclerViewDetailActivity extends BaseActivity {

    @BindView(R.id.iv_detail)
    ImageView ivDetail;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.tv_detail)
    TextView tvDetail;

    @Override
    protected int initView() {
        return EMPTY_VIEW;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_recycler_view_detail;

    }

    @Override
    protected void initData() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.icon_top_back);
        }
        initToolbarLayout();
        Glide.with(this).load(R.mipmap.my).into(ivDetail);
        tvDetail.setText(generateDetailContent("噼里啪啦"));
    }

    private void initToolbarLayout() {
//        collapsingToolbar.setTitle("噼里啪啦");
//        collapsingToolbar.setTitle("CollapsingToolbarLayout");
        //通过CollapsingToolbarLayout修改字体颜色
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);//设置还没收缩时状态下字体颜色
        collapsingToolbar.setCollapsedTitleTextColor(Color.GREEN);//设置收缩后Toolbar上字体的颜色
    }

    public String generateDetailContent(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 500; i++) {
            sb.append(str + "\t");
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
