package com.wyj.treasure.viewcustom.bannerviewpager;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


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

    /**
     * 界面复用
     */
    private List<View> mConverViews;
    /**
     * 点击事件接口
     */
    private OnItemClickListener listener;

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
        mConverViews = new ArrayList<>();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN://按下
                mHandler.removeMessages(SCROLL_MSG);
                break;
            case MotionEvent.ACTION_UP://松开
                startRoll();
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
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
        //设置viewpager的adapter
        setAdapter(new BannerPagerAdapter());
        //设置中间位置
        int item = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % adapter.getCount();//要保证是图片个数
        setCurrentItem(item);
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

    /**
     * 获取复用界面
     *
     * @return
     */
    public View getConverView() {
        for (int i = 0; i < mConverViews.size(); i++) {
            if (mConverViews.get(i).getParent() != null) {
                return mConverViews.get(i);
            }
        }
        return null;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onclick(int position);
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
            View bannerItemView = mAdapter.getView(position % mAdapter.getCount(), getConverView());
            container.addView(bannerItemView);
            bannerItemView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onclick(position % mAdapter.getCount());
                    }
                }
            });
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
            mConverViews.add((View) object);
        }
    }
}
