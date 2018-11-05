package com.wyj.treasure.activity;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wyj.treasure.R;
import com.wyj.treasure.fragment.GoodsFragment;
import com.wyj.treasure.fragment.RecommendFragment;
import com.wyj.treasure.utils.ArgbAnimator;
import com.wyj.treasure.widget.ScrollLayout;

import butterknife.BindView;
import butterknife.OnClick;


public class PullUpToLoadMoreActivity extends BaseActivity {

    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.iv_banner)
    ImageView ivBanner;
    @BindView(R.id.sv_first)
    /*滑动内容第一部分模块*/ NestedScrollView svFirst;
    @BindView(R.id.pager_tabs)
    TabLayout pagerTabs;
    @BindView(R.id.vp_tabs)
    ViewPager vpTabs;
    @BindView(R.id.sl_root)
    ScrollLayout slRoot;
    @BindView(R.id.iv_float)
    ImageView ivFloat;
    @BindView(R.id.rl_root)
    RelativeLayout rlRoot;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    /**
     * 模块管理
     */

    private ArgbAnimator argb;
    private View tv_title;

    @Override
    protected int getContentViewID() {
        return 0;
    }

    @Override
    protected int contentView() {
        return R.layout.activity_pull_up_to_load_more;
    }

    @Override
    protected void initData() {
        //设置导航图标一定要设置在setsupportactionbar后面才有用不然他会显示小箭头
        initActionBar();
        initViewPager();
        argb = new ArgbAnimator(getResources().getColor(R.color.transparent), getResources().getColor(R.color.colorPrimary));
        svFirst.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> setToolbarBg(scrollY));
        slRoot.setOnScrollChangeListener((ScrollLayout.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> ivFloat.setVisibility(scrollY >= v.getScrollHeight() / 2 ? View.VISIBLE : View.GONE));
    }

    private void initViewPager() {
        vpTabs.setAdapter(new TabAdapter(getSupportFragmentManager()));
        pagerTabs.setupWithViewPager(vpTabs);
        mToolbar.setBackgroundColor(Color.TRANSPARENT);
    }

    private void initActionBar() {
        setSupportActionBar(mToolbar);
        ActionBar bar = getSupportActionBar();
        bar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        View actionView = getLayoutInflater().inflate(R.layout.actionbar_main, null);
        tv_title = actionView.findViewById(R.id.tv_title);
        actionView.findViewById(R.id.iv_return).setOnClickListener(v -> {
            //这里在返回键中模拟键盘弹出时的情景
            ViewGroup.LayoutParams params = slRoot.getLayoutParams();
            params.height = slRoot.getHeight() * 2 / 3;
            slRoot.setLayoutParams(params);
        });
        ActionBar.LayoutParams actionBarParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        bar.setCustomView(actionView, actionBarParams);
    }


    @OnClick(R.id.iv_float)
    public void onViewClicked() {
        slRoot.open();
    }

    public class TabAdapter extends FragmentPagerAdapter {
        final String[] titles = {"图文详情", "商品参数", "热卖推荐"};

        public TabAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return RecommendFragment.newInstance();
                case 1:
                    return GoodsFragment.newInstance();
                case 2:
                    return RecommendFragment.newInstance();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

    /**
     * 根据偏移值计算toolbar的颜色过渡值
     *
     * @param scrollY
     */
    private int lastColor = Color.TRANSPARENT;

    private void setToolbarBg(int scrollY) {
        float fraction = (float) scrollY / ivBanner.getHeight();
        int color = argb.getFractionColor(fraction);
        if (color != lastColor) {
            lastColor = color;
            mToolbar.setBackgroundColor(color);
        }
        tv_title.setVisibility(fraction < 1 ? View.INVISIBLE : View.VISIBLE);
    }
}
