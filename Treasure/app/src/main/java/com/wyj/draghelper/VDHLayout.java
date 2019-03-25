package com.wyj.draghelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * @author wangyujie
 * @date 2019/3/1.16:11
 * @describe 步骤：
 * 1、创建ViewDragHelper实例
 * 2、触摸相关的方法的调用 (onInterceptTouchEvent() onTouchEvent())
 * 3、ViewDragHelper.Callback实例的编写
 */
public class VDHLayout extends LinearLayout {

    private final ViewDragHelper mViewDragHelper;

    public VDHLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mViewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View view, int i) {
                return true;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                int leftBound = getPaddingLeft();//左边界
                int rightBound = getWidth() - leftBound - child.getMeasuredWidth() - getPaddingRight();//右边界
                //先比较左边界 移动的位置>==PaddingLeft
                //再比较右边界 移动的位置<==ViewGroup.getWidth()-PaddingLeft-PaddingRight-child.getWidth
                int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                int topBound = getPaddingTop();
                int bottomBound = getHeight() - topBound - getPaddingBottom() - child.getMeasuredHeight();
                int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
