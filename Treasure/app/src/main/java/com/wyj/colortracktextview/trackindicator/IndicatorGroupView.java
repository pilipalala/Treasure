package com.wyj.colortracktextview.trackindicator;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

/**
 * @author wangyujie
 *         on 2017/12/4.14:59
 *         TODO Indicator容器
 */

public class IndicatorGroupView extends FrameLayout {
    /**
     * 指示器条目的容器
     */
    private LinearLayout mIndicatorGroup;
    /**
     * 指示器
     */
    private View mBottomTrackView;
    /**
     * 指示器宽度
     */
    private int mItemWidth;
    /**
     * 指示器的LayoutParams
     */
    private LayoutParams mTrackParams;
    private int mInitLeftMargin;

    public IndicatorGroupView(@NonNull Context context) {
        this(context, null);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IndicatorGroupView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mIndicatorGroup = new LinearLayout(context);
        addView(mIndicatorGroup);
    }

    /**
     * 添加ItemView
     *
     * @param itemView
     */
    public void addItemView(View itemView) {
        mIndicatorGroup.addView(itemView);
    }

    /**
     * 获取当前 ItemView
     *
     * @param position
     * @return
     */
    public View getItemView(int position) {
        return mIndicatorGroup.getChildAt(position);
    }

    /**
     * 添加底部跟踪指示器
     *
     * @param bottomTrackView
     * @param itemWidth
     */
    public void addBottomTrackView(View bottomTrackView, int itemWidth) {
        if (bottomTrackView == null) {
            return;
        }
        mBottomTrackView = bottomTrackView;
        addView(mBottomTrackView);
        mItemWidth = itemWidth;
        //让指示器在底部
        mTrackParams = (LayoutParams) mBottomTrackView.getLayoutParams();
        mTrackParams.gravity = Gravity.BOTTOM;

        /**
         * 如果用户设置固定的宽度，就用固定值，反之就用mItemWidth
         * */
        int trackWidth = mTrackParams.width;
        if (mTrackParams.width == LayoutParams.MATCH_PARENT || mTrackParams.width == LayoutParams.WRAP_CONTENT) {
            trackWidth = mItemWidth;
        }
        /**
         * 设置的宽度过大
         * */
        if (trackWidth > mItemWidth) {
            trackWidth = mItemWidth;
        }
        //设置宽度
        mTrackParams.width = trackWidth;
        /**
         * 确保指示器在最中间
         * */
        mInitLeftMargin = (mItemWidth - trackWidth) / 2;
        mTrackParams.leftMargin = mInitLeftMargin;
        requestLayout();
    }

    /**
     * 滚动底部的指示器
     *
     * @param position
     * @param positionOffset
     */
    public void scrollBottomTrack(int position, float positionOffset) {
        int leftMargin = (int) ((position + positionOffset) * mItemWidth);
        //控制leftMargin移动
        mTrackParams.leftMargin = leftMargin + mInitLeftMargin;
        requestLayout();
    }

    /**
     * 点击移动 滚动底部的指示器
     *
     * @param position
     */
    public void scrollBottomTrack(int position) {
        /**
         * 最终要移动的位置
         * */
        int finalLeftMargin = (position * mItemWidth) + mInitLeftMargin;
        //当前的位置
        int currentLeftMargin = mTrackParams.leftMargin;
        //移动的距离
        int distance = finalLeftMargin - currentLeftMargin;

        //带动画
        ValueAnimator animator = ObjectAnimator.ofFloat(currentLeftMargin, finalLeftMargin).setDuration((long) (Math.abs(distance) * 0.5));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator anima) {
                float leftMargin = (float) anima.getAnimatedValue();
                //控制leftMargin移动
                mTrackParams.leftMargin = (int) leftMargin;
                requestLayout();
            }
        });
        /**
         * 插值器
         * */
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();


    }
}
