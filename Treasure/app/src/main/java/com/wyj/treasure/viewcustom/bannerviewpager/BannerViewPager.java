package com.wyj.treasure.viewcustom.bannerviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.reflect.Field;


/**
 * @author wangyujie
 *         on 2017/11/16.17:28
 *         TODO
 */

public class BannerViewPager extends ViewPager {


    private BannerAdapter mAdapter;
    /**
     * 发送消息的msgWhat
     */
    private int SCROLL_MSG = 0x001;
    /**
     * 轮播间隔的时间
     */
    private long SCROOL_CUT_DOWN_TIME = 3 * 1000;
    /**
     * 改变viewpager切换的速率
     */
    private BannerScroller mScroller;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == SCROLL_MSG) {
                //每隔一段时间后切换到下一页
                setCurrentItem(getCurrentItem() + 1);
                //继续循环执行
                startRoll();
            }
        }
    };


    public BannerViewPager(Context context) {
        this(context, null);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**
         * 改变viewpager切换的速率  mScroller.startScroll(sx, sy, dx, dy, duration);
         * duration 持续时间  局部变量
         *
         * 改变mScroller 通过反射设置
         *
         * */
        try {
            mScroller = new BannerScroller(context, new AccelerateDecelerateInterpolator());
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置切换页面持续的时间
     *
     * @param mScrollerDuration
     */
    public void setScrollerDuration(int mScrollerDuration) {
        mScroller.setScrollerDuration(mScrollerDuration);
    }

    /**
     * 1.设置自定义Adapter
     *
     * @param adapter
     */
    public void setAdapter(BannerAdapter adapter) {
        mAdapter = adapter;
//        设置viewpager的adapter
        setAdapter(new BannerPagerAdapter());
    }

    /**
     * 2.实现自动轮播
     */
    public void startRoll() {
        //清楚消息
        mHandler.removeMessages(SCROLL_MSG);

        mHandler.sendEmptyMessageDelayed(SCROLL_MSG, SCROOL_CUT_DOWN_TIME);
    }

    /**
     * Activity调用Destory方法后就会调用onDetachedFromWindow()
     * <p>
     * 销毁Handler停止发送,解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
    }

    private class BannerPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            //官方推荐
            return view == object;
        }

        /**
         * 创建ViewPager条目回调的方法
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //Adapter设计模式 为了完全让用户自定义
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount());
            container.addView(bannerItemView);
            return bannerItemView;

        }

        /**
         * 销毁条目回调的方法
         *
         * @param container
         * @param position
         * @param object
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;

        }
    }
}
