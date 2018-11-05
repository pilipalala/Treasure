package com.wyj.treasure.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.MultiFeedAdapter;

import butterknife.BindView;

public class MultiSuspensionBarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.feed_list)
    RecyclerView feedList;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.suspension_bar)
    RelativeLayout mSuspensionBar;
    private int mSuspensionHeight;
    private MultiFeedAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private int mCurrentPosition = 0;

    @Override
    protected int getContentViewID() {
       return R.layout.activity_multi_suspension_bar;
    }

    @Override
    protected void initData() {
        toolbar.setNavigationIcon(R.mipmap.icon_top_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        linearLayoutManager = new LinearLayoutManager(this);
        adapter = new MultiFeedAdapter();
        feedList.setLayoutManager(linearLayoutManager);
        feedList.setAdapter(adapter);
        feedList.setHasFixedSize(true);
        feedList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mSuspensionHeight = mSuspensionBar.getHeight();

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (adapter.getItemViewType(mCurrentPosition + 1) == MultiFeedAdapter.TYPE_TIME) {
                    View view = linearLayoutManager.findViewByPosition(mCurrentPosition + 1);
                    if (view != null) {
                        if (view.getTop() <= mSuspensionHeight) {
                            mSuspensionBar.setY(view.getTop() - mSuspensionHeight);
                        } else {
                            mSuspensionBar.setY(0);
                        }
                    }

                }
                if (linearLayoutManager.findFirstVisibleItemPosition() != mCurrentPosition) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mSuspensionBar.setY(0);
                    updateSuspensionBar();
                }
            }
        });
        updateSuspensionBar();

    }

    private void updateSuspensionBar() {
        tvTime.setText("NOVEMBER " + (1 + mCurrentPosition / 4));
    }

}
