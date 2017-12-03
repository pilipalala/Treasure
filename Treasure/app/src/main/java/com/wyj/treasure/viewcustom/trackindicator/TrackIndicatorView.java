package com.wyj.treasure.viewcustom.trackindicator;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.wyj.treasure.R;

/**
 * @author wangyujie
 *         on 2017/12/2.13:45
 *         TODO
 */

public class TrackIndicatorView extends HorizontalScrollView implements ViewPager.OnPageChangeListener {
    /**
     * 适配器
     */
    private IndicatorAdapter mAdapter;
    /**
     * 指示器条目的容器
     */
    private LinearLayout mIndicatorGroup;
    /**
     * 指定一屏幕可见显示多少
     */
    private int mTabVisibleNums = 0;
    /**
     * item的宽度
     */
    private int mItemWidth;

    /**
     * viewpager
     */
    private ViewPager mViewPager;

    public TrackIndicatorView(Context context) {
        this(context, null);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrackIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
        initAttribute(context, attrs);
    }

    private void initAttribute(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TrackIndicatorView);
        mTabVisibleNums = typedArray.getInteger(R.styleable.TrackIndicatorView_tabVisubleNums, mTabVisibleNums);

        typedArray.recycle();
    }

    /**
     * 设置adapter适配器
     *
     * @param adapter
     */
    public void setAdapter(IndicatorAdapter adapter) {
        mAdapter = adapter;
        if (adapter == null) {
            throw new NullPointerException("adapter is null");
        }
        //动态添加view 获取有多少数据
        int count = mAdapter.getCount();

        //循环添加itemview
        for (int i = 0; i < count; i++) {
            View itemView = mAdapter.getView(i, this);
            mIndicatorGroup.addView(itemView);
            //点击事件
            if (mViewPager != null) {
                switchItemClick(i, itemView);
            }
        }
    }

    private void switchItemClick(int i, View itemView) {
        itemView.setOnClickListener(v -> {

            mViewPager.setCurrentItem(i);

        });
    }

    public void setAdapter(IndicatorAdapter adapter, ViewPager viewPager) {
        this.mViewPager = viewPager;
        viewPager.addOnPageChangeListener(this);
        setAdapter(adapter);

    }

    /**
     * 如果没有配置mTabVisibleNums 那么我们的宽度就是取item里面最宽的
     * 如果宽度相加不足屏幕的宽度 就默认屏幕宽度
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //指定item的宽度
            mItemWidth = getItemWidth();
            for (int i = 0; i < mAdapter.getCount(); i++) {
                View childAt = mIndicatorGroup.getChildAt(i);
                childAt.getLayoutParams().width = mItemWidth;
                childAt.requestLayout();
            }
        }
    }

    /**
     * @return item的宽度
     */
    private int getItemWidth() {
        int parentWidth = getWidth();
        /**
         * 1.指定了item数量
         * */
        if (mTabVisibleNums != 0) {
            return parentWidth / mTabVisibleNums;
        }
        /**
         * 2.没有指定item数量
         * */
        int itemWidth = 0;
        /**
         * 获取最宽
         * */
        int maxItemWidth = 0;

        for (int i = 0; i < mAdapter.getCount(); i++) {
            int currentItemWidth = mIndicatorGroup.getChildAt(i).getMeasuredWidth();
            maxItemWidth = Math.max(currentItemWidth, maxItemWidth);
        }

        /**
         * allWidth > 屏幕宽度
         * */
        itemWidth = maxItemWidth;
        /**
         * 所有item的宽度总和
         * */
        int allWidth = maxItemWidth * mAdapter.getCount();
        /**
         * allWidth < 屏幕宽度
         * */
        if (allWidth < parentWidth) {
            itemWidth = parentWidth / mAdapter.getCount();
        }
        return itemWidth;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        scrollCurrentIndicator(position, positionOffset);

    }

    /**
     * 不断的滚动当前的指示器
     *
     * @param position
     * @param positionOffset
     */
    private void scrollCurrentIndicator(int position, float positionOffset) {
        //当前总共的位置
        float totalScroll = (position + positionOffset) * mItemWidth;
        //左边的偏移
        float offsetScroll = (getWidth() - mItemWidth) / 2;
        //最终的一个偏移量
        int finalScroll = (int) (totalScroll - offsetScroll);
        //调用scrollTo
        scrollTo(finalScroll, 0);

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
