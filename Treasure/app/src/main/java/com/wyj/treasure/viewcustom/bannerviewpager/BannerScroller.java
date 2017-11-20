package com.wyj.treasure.viewcustom.bannerviewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author wangyujie
 *         on 2017/11/20.10:49
 *         TODO
 */

public class BannerScroller extends Scroller {


    /**
     * 动画持续的时间
     */
    private int mScrollerDuration = 1000;

    public BannerScroller(Context context) {
        this(context, null);
    }

    public BannerScroller(Context context, Interpolator interpolator) {
        this(context, interpolator, false);
    }

    public BannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    /**
     * 设置切换页面持续的时间
     *
     * @param mScrollerDuration
     */
    public void setScrollerDuration(int mScrollerDuration) {
        this.mScrollerDuration = mScrollerDuration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollerDuration);

    }
}
