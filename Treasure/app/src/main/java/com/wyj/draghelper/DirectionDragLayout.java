package com.wyj.draghelper;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.wyj.treasure.R;

/**
 * @author wangyujie
 * @date 2019/3/1.10:28
 * @describe ViewDragHelper是用于编写自定义ViewGroup的实用程序类。
 * 它提供了许多有用的操作和状态跟踪，允许用户在其父ViewGroup中拖动和重新定位视图。
 */
public class DirectionDragLayout extends ConstraintLayout {

    private final ViewDragHelper mViewDragHelper;
    /**
     * 左边的View
     */
    private View mCallView;
    /**
     * 右边的View
     */
    private View mUnlockView;
    /**
     * 上边的View
     */
    private View mCameraView;
    /**
     * 中间的View，就是要拖动的View
     */
    private View mHomeView;


    Point mHomeCenterPoint = new Point();
    Point mCallCenterPoint = new Point();
    Point mCameraCenterPoint = new Point();
    Point mUnlockCenterPoint = new Point();
    Point mHomeOriginalPoint = new Point();

    private int mTopBound;
    private int mBottomBound;
    private int mLeftBound;
    private int mRightBound;
    /**
     * 当前正在水平拖拽的标记
     */
    private boolean mIsHorizontalDrag = false;
    /**
     * 当前正在竖直拖拽的标记
     */
    private boolean mIsVerticalDrag = false;
    /**
     * 是否到达边界的标记
     */
    private boolean mIsReachBound;

    public DirectionDragLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 1、创建 ViewDragHelper 实例
        // 参一 : 当前的ViewGroup对象
        // 参二 : 灵敏度 1.0f是正常的
        // 参三 : 提供信息和接收事件的回调
        mViewDragHelper = ViewDragHelper.create(this, 1f, dragCallBack);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // 3，获取View
        mCallView = findViewById(R.id.iv_drag_call);
        mUnlockView = findViewById(R.id.iv_drag_unlock);
        mCameraView = findViewById(R.id.iv_drag_camera);
        mHomeView = findViewById(R.id.iv_drag_home);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //homeView的边界坐标
        mHomeOriginalPoint.x = mHomeView.getLeft();
        mHomeOriginalPoint.y = mHomeView.getTop();

        //homeView的中心点坐标
        mHomeCenterPoint.x = mHomeView.getLeft() + mHomeView.getMeasuredWidth() / 2;
        mHomeCenterPoint.y = mHomeView.getTop() + mHomeView.getMeasuredHeight() / 2;

        mCallCenterPoint.x = mCallView.getLeft() + mCallView.getMeasuredWidth() / 2;
        mCallCenterPoint.y = mCallView.getTop() + mCallView.getMeasuredHeight() / 2;

        mUnlockCenterPoint.x = mUnlockView.getLeft() + mUnlockView.getMeasuredWidth() / 2;
        mUnlockCenterPoint.y = mUnlockView.getTop() + mUnlockView.getMeasuredHeight() / 2;

        mCameraCenterPoint.x = mCameraView.getLeft() + mCameraView.getMeasuredWidth() / 2;
        mCameraCenterPoint.y = mCameraView.getTop() + mCameraView.getMeasuredHeight() / 2;

        mTopBound = mCameraCenterPoint.y - mHomeView.getMeasuredHeight() / 2;
        mBottomBound = mHomeCenterPoint.y - mHomeView.getMeasuredHeight() / 2;
        mRightBound = mUnlockCenterPoint.x - mHomeView.getMeasuredWidth() / 2;
        mLeftBound = mCallCenterPoint.x - mHomeView.getMeasuredWidth() / 2;

    }

    // 2、在onInterceptTouchEvent和onTouchEvent中调用mViewDragHelper的方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 在 mDragHelper 拦截事件前，记得重置拖拽标记
        resetFlags();
        // 通过使用 mDragHelper.shouldInterceptTouchEvent(ev)来决定我们是否应该拦截当前的事件
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    private void resetFlags() {
        mIsHorizontalDrag = false;
        mIsVerticalDrag = false;
        mIsReachBound = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // 通过mDragHelper.processTouchEvent(event)来处理事件
        mViewDragHelper.processTouchEvent(event);
        return true; // 返回 true，表示事件被处理了。
    }

    ViewDragHelper.Callback dragCallBack = new ViewDragHelper.Callback() {
        // 4, 这个是必须重写的方法，
        // 返回true,表示允许捕获该子view
        @Override
        public boolean tryCaptureView(@NonNull View child, int i) {
            // 当 child 是 mHomeView 时，才允许捕获。
            return child == mHomeView;
        }

        // 5, 限制被拖拽的子 view 沿纵轴的运动
        // 如果不重写，就不能实现纵向的拖动
        // 参一：child 表示正在拖拽的 view
        // 参二：top Attempted motion along the Y axis 理解为拖动的那个 view 想要到达位置的 top 值
        // 参三：增量，变化量
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            // 11, 固定向拖拽
            if (mIsHorizontalDrag) {
                return mHomeView.getTop();
            }
            if (Math.abs(dy) > 5) {
                mIsVerticalDrag = true;
            }

            return Math.min(Math.max(mTopBound, top), mBottomBound);
        }

        // 6, 限制被拖拽的子view沿横轴的运动
        // 如果不重写，就不能实现横向的拖动
        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            // 11, 固定向拖拽
            if (mIsVerticalDrag) {
                return mHomeView.getLeft();
            }
            if (Math.abs(dx) > 5) {
                mIsHorizontalDrag = true;
            }
            return Math.min(Math.max(mLeftBound, left), mRightBound);
        }

        // 10, 松手返回起始点
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            // 判断释放的 view 是不是 mHomeView
            if (releasedChild == mHomeView) {
                // 让释放的 view 停在给定的位置
                mViewDragHelper.settleCapturedViewAt(mHomeOriginalPoint.x, mHomeOriginalPoint.y);
                invalidate();
            }
        }

        // 当拖拽的 View 的位置发生变化的时候回调(特指 capturedview)
        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            // 12, 到达边界时触发操作
            if (mIsReachBound) {
                return;
            }
            if (left <= mLeftBound) {
                mIsReachBound = true;
                Toast.makeText(getContext(), "到达左边界", Toast.LENGTH_SHORT).show();
            }
            if (left >= mRightBound) {
                mIsReachBound = true;
                Toast.makeText(getContext(), "到达右边界", Toast.LENGTH_SHORT).show();
            }
            if (top <= mTopBound) {
                mIsReachBound = true;
                Toast.makeText(getContext(), "到达上边界", Toast.LENGTH_SHORT).show();
            }
        }
    };

    // 10, 松手返回起始点
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }
}
