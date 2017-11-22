package com.wyj.treasure.viewcustom.bannerviewpager;

import android.app.Activity;
import android.app.Application;
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

import com.wyj.treasure.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wangyujie
 *         on 2017/11/16.17:28
 *         TODO
 */

public class BannerViewPager extends ViewPager {


    /**
     * 当前Activity
     */
    private Activity mActivity;
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
    /**
     * 界面复用
     */
    private List<View> mConverViews;
    /**
     * 点击事件接口
     */
    private OnItemClickListener clickListener;
    private OnItemTouchListener touchListener;
    /**
     * 是否可以滚动
     */
    private boolean mScrollAble = true;
    private Handler mHandler;

    //管理Activity的生命周期
    Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycle() {
        @Override
        public void onActivityResumed(Activity activity) {
            LogUtil.i("onActivityResumed");
            if (activity == mActivity) {
                //开始轮播
                startRoll();
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            LogUtil.i("onActivityPaused");
            if (activity == mActivity) {
                //停止轮播
                mHandler.removeMessages(SCROLL_MSG);
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
        mActivity = (Activity) context;
        try {
            mScroller = new BannerScroller(context, new AccelerateDecelerateInterpolator());
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            field.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mConverViews = new ArrayList<>();
        initHandler();

    }

    private void initHandler() {
        mHandler = new Handler() {
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

        // adapter不能是空
        if (mAdapter == null) {
            return;
        }
        // 判断是不是只有一条数据
        mScrollAble = mAdapter.getCount() != 1;
        if (mScrollAble && mHandler != null) {
            // 清除消息
            mHandler.removeMessages(SCROLL_MSG);
            // 消息  延迟时间  让用户自定义  有一个默认  3500
            mHandler.sendEmptyMessageDelayed(SCROLL_MSG, SCROOL_CUT_DOWN_TIME);
        }
    }

    /**
     * 停止轮播
     */
    public void stopRoll() {
        mHandler.removeMessages(SCROLL_MSG);
    }

    /**
     * Activity调用Destory方法后就会调用onDetachedFromWindow()
     * <p>
     * 销毁Handler停止发送,解决内存泄漏
     */
    @Override
    protected void onDetachedFromWindow() {
        mHandler.removeMessages(SCROLL_MSG);
        mHandler = null;
        mActivity.getApplication().unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {
        if (mAdapter != null) {
            initHandler();
            startRoll();
            // 管理Activity的生命周期
            mActivity.getApplication().registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        }
        super.onAttachedToWindow();
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

    /**
     * 设置click监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    /**
     * 设置Touch 监听
     *
     * @param touchListener
     */
    public void setOnItemTouchListener(OnItemTouchListener touchListener) {
        this.touchListener = touchListener;
    }

    public interface OnItemTouchListener {
        void onTouch(View view, MotionEvent motionEvent);
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
                    if (clickListener != null) {
                        clickListener.onclick(position % mAdapter.getCount());
                    }
                }
            });
            bannerItemView.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    if (touchListener != null) {
                        touchListener.onTouch(view, event);
                    }
                    return false;
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
