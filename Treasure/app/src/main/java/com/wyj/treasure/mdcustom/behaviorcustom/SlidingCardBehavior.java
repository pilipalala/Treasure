package com.wyj.treasure.mdcustom.behaviorcustom;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * Created by wangyujie
 * on 2017/10/31.14:07
 * TODO
 */

public class SlidingCardBehavior extends Behavior<SlidingCardLayout> {

    private int mInitialOffset;

    /**
     * 当view展现出来时候那一刻调用onMeasureChild、onLayoutChild
     */

    @Override
    public boolean onMeasureChild(CoordinatorLayout parent, SlidingCardLayout child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        /**
         * 当前控件的高度 = 父容器给的高度-上面和下面几个child的头部的高度
         * */
        int offset = getChildMeasureOffset(parent, child);
        int heightMeasureSpec = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(heightMeasureSpec, View.MeasureSpec.EXACTLY));
        return true;

    }

    /**
     * @param parent
     * @param child
     * @return
     */
    private int getChildMeasureOffset(CoordinatorLayout parent, SlidingCardLayout child) {
        /**
         * 上面和下面几个child的头部的高度
         * */
        int offset = 0;
        for (int i = 0; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view != child && view instanceof SlidingCardLayout) {
                offset += ((SlidingCardLayout) view).getHeaderHeight();
            }
        }
        return offset;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, SlidingCardLayout child, int layoutDirection) {

        /**
         * 摆放里面的所有子控件
         * */
        parent.onLayoutChild(child, layoutDirection);


        /**
         *  给里面的child做一个偏移
         *  拿到上面的child，获取每一个child头部的高度相加
         * */
        SlidingCardLayout previous = getPreviousChild(parent, child);
        if (previous != null) {
            int offset = previous.getTop() + previous.getHeaderHeight();
            child.offsetTopAndBottom(offset);
        }
        mInitialOffset = child.getTop();
        return true;

    }

    /**
     * 拿到上面的child
     *
     * @param parent
     * @param child
     * @return
     */
    private SlidingCardLayout getPreviousChild(CoordinatorLayout parent, SlidingCardLayout child) {
        int index = parent.indexOfChild(child);
        for (int i = index - 1; i >= 0; i--) {
            if (parent.getChildAt(i) instanceof SlidingCardLayout) {
                return (SlidingCardLayout) parent.getChildAt(i);
            }
        }
        return null;
    }


    /**
     * 监听方向
     *
     * @param coordinatorLayout
     * @param child
     * @param directTargetChild
     * @param target
     * @param nestedScrollAxes
     * @return
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View directTargetChild, View target, int nestedScrollAxes) {

        boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return isVertical && child == directTargetChild;

    }

    /**
     * @param coordinatorLayout
     * @param child
     * @param target
     * @param dx
     * @param dy
     * @param consumed          调用onNestedPreScroll方法之前会先调用onStartNestedScroll方法 判断返回的是true 还是false 来监听方向
     */
    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View target, int dx, int dy, int[] consumed) {
        /**
         * 1.控制自己的滑动
         * */
        int shift = scrool(child, dy);

        /**
         * 2.控制上边和下边child的滑动
         * */

        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

    }

    private int scrool(SlidingCardLayout child, int dy) {
        /*原来的位置减去现在的位置*/
        int offset = -dy;
        child.offsetTopAndBottom(offset);
        return 0;

    }
}
