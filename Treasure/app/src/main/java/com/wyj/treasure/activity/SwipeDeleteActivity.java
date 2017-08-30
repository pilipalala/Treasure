package com.wyj.treasure.activity;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.SwipeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SwipeDeleteActivity extends BaseActivity  implements SwipeAdapter.IonSlidingViewClickListener{

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    private SwipeAdapter mAdapter;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_swipe_delete);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new SwipeAdapter(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onDeleteBtnCilck(View view, int position) {
        mAdapter.removeData(position);
    }
}
