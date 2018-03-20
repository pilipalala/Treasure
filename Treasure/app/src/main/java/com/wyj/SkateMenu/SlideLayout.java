package com.wyj.SkateMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * Created by wangyujie
 * Date 2017/6/5
 * Time 22:19
 * TODO 侧滑菜单item
 */

public class SlideLayout extends FrameLayout {
    private View contentView;
    private View menuView;
    /**
     * content 的宽
     */
    private int contentWidth;
    /**
     * menu 的宽
     */
    private int menuWidth;
    private int viewHeight;
    private int toScrollX;
    /**
     * 滚动器
     */
    private Scroller scroller;
    private float downX, downY;
    private OnStateChangeListener listener;
    private float startX;
    private float startY;

    public SlideLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    /**
     * 当布局文件加载完成的时候回调这个方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        contentView = getChildAt(0);
        menuView = getChildAt(1);
    }

    /**
     * 测量的时候得到宽和高
     * <p>
     * 在onMeasure()方法中
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        contentWidth = contentView.getMeasuredWidth();
        menuWidth = menuView.getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        /**
         * 指定菜单的位置
         * */
        menuView.layout(contentWidth, 0, contentWidth + menuWidth, viewHeight);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*1.按下记录坐标值*/
                downX = startX = event.getX();
                downY = startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                /*2.记录结束值*/
                float endX = event.getX();
                float endY = event.getY();

                /*3.计算偏移量*/

                float distanceX = endX - startX;
                Log.e(TAG, "onTouchEvent: " + getScaleX());

                toScrollX = (int) (getScrollX() - distanceX);
                Log.e(TAG, "toScrollX: " + toScrollX);
                if (toScrollX < 0) {
                    toScrollX = 0;
                } else if (toScrollX > menuWidth) {
                    toScrollX = menuWidth;
                }
                scrollTo(toScrollX, getScrollY());

                /*在X轴和Y轴滑动的距离*/
                startX = event.getX();
                startY = event.getY();

                float DX = Math.abs(endX - downX);
                float DY = Math.abs(endY - downY);
                if (DX > DY && DX > 8) {
                    /*水平方向滑动*/
                    /*响应侧滑*/
                    /*反拦截--事件给slideLayout*/
                    /*把父View拦截*/
                    getParent().requestDisallowInterceptTouchEvent(true);
                }

                break;
            case MotionEvent.ACTION_UP:
                int totlaScrollX = getScrollX();/*偏移量*/
                if (totlaScrollX < menuWidth / 2) {
                    /*关闭menu*/
                    closeMenu();
                } else {
                    /*打开menu*/
                    openMenu();
                }
                break;

        }

        return true;


    }

    /**
     * true:拦截孩子的事件，但会执行当前控件的onTouchEvent()方法
     * false:不拦截孩子的事件，事件继续传递
     *
     * @param event
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        boolean intercept = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                /*1.按下记录坐标值*/
                downX = startX = event.getX();
                if (listener != null) {
                    listener.onDown(this);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                /*2.记录结束值*/
                float endX = event.getX();
                /*在X轴和Y轴滑动的距离*/
                startX = event.getX();
                float DX = Math.abs(endX - downX);
                if (DX > 8) {
                    /*水平方向滑动*/
                    /*响应侧滑*/
                    intercept = true;
                }

                break;
            case MotionEvent.ACTION_UP:

                break;

        }


        return intercept;

    }

    public void openMenu() {
        /*目标--》menuWidth*/
        int distanceX = menuWidth - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();/*强制刷新*/
        if (listener != null) {
            listener.onOpen(this);
        }
    }


    /**
     * 关闭menu
     */
    public void closeMenu() {
        /*目标--》0*/
        int distanceX = 0 - getScrollX();
        scroller.startScroll(getScrollX(), getScrollY(), distanceX, getScrollY());
        invalidate();/*强制刷新*/
        if (listener != null) {
            listener.onClose(this);
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), scroller.getCurrY());
            invalidate();
        }
    }

    public void setOnStateChangeListener(OnStateChangeListener listener) {
        this.listener = listener;

    }

    public interface OnStateChangeListener {
        void onClose(SlideLayout slideLayout);

        void onDown(SlideLayout slideLayout);

        void onOpen(SlideLayout slideLayout);
    }
}
