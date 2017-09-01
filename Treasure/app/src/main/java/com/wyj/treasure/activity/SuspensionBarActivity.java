package com.wyj.treasure.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.adapter.FeedAdapter;
import com.wyj.treasure.utils.GlideUtils;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.widget.CircleImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SuspensionBarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.feed_list)
    RecyclerView feedList;
    @BindView(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.top_divider)
    View topDivider;
    @BindView(R.id.suspension_bar)
    RelativeLayout mSuspensionBar;
    /*悬浮条高度*/
    private int mSuspensionHeight;
    /**
     * 悬浮条信息来自的那个列表项在RecyclerView的位置
     */
    private int mCurrentPosition = 0;


    @Override
    protected void initView() {
        setContentView(R.layout.activity_suspension_bar);
        ButterKnife.bind(this);
    }

    @Override
    protected void initData() {
        toolbar.setNavigationIcon(R.mipmap.icon_top_back);
        toolbar.setNavigationOnClickListener(v -> finish());
        toolbar.inflateMenu(R.menu.menu_suspensionbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_text:
                        Intent intent = new Intent(SuspensionBarActivity.this, MultiSuspensionBarActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        FeedAdapter adapter = new FeedAdapter();
        feedList.setLayoutManager(linearLayoutManager);
        feedList.setAdapter(adapter);
        /*设置固定大小*/
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
                /*获取第mCurrentPosition+1个item*/
                View view = linearLayoutManager.findViewByPosition(mCurrentPosition+1);
                if (view != null) {
                    /*view 距离顶部的距离小于 悬浮条的高度*/
                    if (view.getTop() <= mSuspensionHeight) {
                        LogUtil.i("--------");
                        mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                    } else {
                        LogUtil.i("++++++++");
                        mSuspensionBar.setY(0);
                    }
                }
                /*悬浮条的位置不在第一个*/
                if (mCurrentPosition != linearLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = linearLayoutManager.findFirstVisibleItemPosition();
                    mSuspensionBar.setY(0);
                    updateSuspensionBar();
                }

            }
        });
        updateSuspensionBar();
    }

    private void updateSuspensionBar() {
        GlideUtils.loadImageResource(this, getAvatarResId(mCurrentPosition), ivAvatar);
        tvNickname.setText("Taeyeon " + mCurrentPosition);
    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.mipmap.avatar1;
            case 1:
                return R.mipmap.avatar2;
            case 2:
                return R.mipmap.avatar3;
            case 3:
                return R.mipmap.avatar4;
        }
        return 0;
    }
}
