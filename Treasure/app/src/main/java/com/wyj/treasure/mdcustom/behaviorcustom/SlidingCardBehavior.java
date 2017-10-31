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
       /* int offset = getChildMeasureOffset(parent, child);
        int heightMeasureSpec = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;
        child.measure(parentWidthMeasureSpec, View.MeasureSpec.makeMeasureSpec(heightMeasureSpec, View.MeasureSpec.EXACTLY));
        return true;*/
        int offset = getChildMeasureOffset(parent, child);
        int height = View.MeasureSpec.getSize(parentHeightMeasureSpec) - offset;
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);
        child.measure(parentWidthMeasureSpec, heightMeasureSpec);
        return true;



    }

    /**
     * 获取卡片的默认偏移值
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
     * 获取上一个卡片
     *
     * @param parent
     * @param child
     * @return
     */
    private SlidingCardLayout getPreviousChild(CoordinatorLayout parent, SlidingCardLayout child) {
        int index = parent.indexOfChild(child);
        for (int i = index - 1; i >=0; i--) {
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout) {
                return (SlidingCardLayout) view;
            }
        }
        return null;
    }

    /**
     * 拿到下面的child
     * 获取下一个卡片
     * @param parent
     * @param child
     * @return
     */
    private SlidingCardLayout getNextChild(CoordinatorLayout parent
            , SlidingCardLayout child) {
        int index = parent.indexOfChild(child);
        for (int i = index + 1; i < parent.getChildCount(); i++) {
            View view = parent.getChildAt(i);
            if (view instanceof SlidingCardLayout) {
                return (SlidingCardLayout) view;
            }
        }
        return null;
    }


    /**
     * 有嵌套滑动到来了，问下父View是否接受嵌套滑动
     *
     * @param coordinatorLayout
     * @param child             嵌套滑动对应的父类的子类(因为嵌套滑动对于的父View不一定是一级就能找到的，可能挑了两级父View的父View，child的辈分>=target)
     * @param directTargetChild
     * @param target            具体嵌套滑动的那个子类
     * @param nestedScrollAxes  支持嵌套滚动轴。水平方向，垂直方向，或者不指定
     * @return 是否接受该嵌套滑动
     */
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, SlidingCardLayout child, View directTargetChild, View target, int nestedScrollAxes) {
        //判断监听的方向
        boolean isVertical = (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
        return isVertical && child == directTargetChild;

    }
/**
 * 在嵌套滑动的子View未滑动之前告诉过来的准备滑动的情况
 *
 * @param parent
 * @param child
 * @param target   具体嵌套滑动的那个子类
 * @param dx       水平方向嵌套滑动的子View想要变化的距离
 * @param dy       垂直方向嵌套滑动的子View想要变化的距离
 * @param consumed 这个参数要我们在实现这个函数的时候指定，回头告诉子View当前父View消耗的距离
 *                 consumed[0] 水平消耗的距离，consumed[1] 垂直消耗的距离 好让子view做出相应的调整
 */


    @Override
    public void onNestedPreScroll(CoordinatorLayout parent, SlidingCardLayout child, View target, int dx, int dy, int[] consumed) {
        int minOffset = mInitialOffset;
        int maxOffset = mInitialOffset + child.getHeight() - child.getHeaderHeight();
        /**
         * 1.控制自己的滑动
         * */
        consumed[1] = scroll(child, dy,
                minOffset,
                maxOffset);

        /**
         * 2.控制上边和下边child的滑动
         * */
        shiftSliding(consumed[1], parent, child);

    }

    /**
     * @param parent
     * @param child
     * @param target
     * @param dxConsumed
     * @param dyConsumed
     * @param dxUnconsumed
     * @param dyUnconsumed
     * 调用onNestedPreScroll方法之前会先调用onStartNestedScroll方法 判断返回的是true 还是false 来监听方向
     */
    @Override
    public void onNestedScroll(CoordinatorLayout parent, SlidingCardLayout child, View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
        /**
         * 1.控制自己的滑动
         * */
        int shift = scroll(child, dyConsumed,
                mInitialOffset,
                mInitialOffset + child.getHeight() - child.getHeaderHeight());

        /**
         * 2.控制上边和下边child的滑动
         * */
        shiftSliding(shift, parent, child);
    }

    private void shiftSliding(int shift, CoordinatorLayout parent, SlidingCardLayout child) {
        if (shift == 0) {
            return;
        }
        if (shift > 0) {//往上推
            //推动上面所有的卡片
            SlidingCardLayout current = child;
            SlidingCardLayout card = getPreviousChild(parent, current);
            while (card != null) {
                int offset = getHeaderOverlap(card, current);
                if (offset > 0) {
                    card.offsetTopAndBottom(-offset);
                }
                current = card;
                card = getPreviousChild(parent, current);
            }
        } else {//往下推
            SlidingCardLayout current = child;
            SlidingCardLayout card = getNextChild(parent, current);
            while (card != null) {
                int offset = getHeaderOverlap(current,card);
                if (offset > 0) {
                    card.offsetTopAndBottom(offset);
                }
                current = card;
                card = getNextChild(parent, current);
            }
        }
    }


    /**
     * 计算其它卡片的偏移值
     * @param above
     * @param below
     * @return
     */
    private int getHeaderOverlap(SlidingCardLayout above, SlidingCardLayout below) {
        return above.getTop() + above.getHeaderHeight() - below.getTop();

    }

    /**
     * 处理自己的滑动
     * @param child
     * @param dy
     * @param minlOffset
     * @param maxOffset
     * @return 是上滑>0还是下滑<0
     */
    private int scroll(SlidingCardLayout child, int dy, int minlOffset, int maxOffset) {
        /**
         * 原来的位置减去现在的位置
         * dy[min max]
         * */
        int initialOffset = child.getTop();
        int offset = clamp(initialOffset - dy, minlOffset, maxOffset) - initialOffset;
        child.offsetTopAndBottom(offset);
        return -offset;

    }

    /**
     *  取上下限之间的值
     * @param i
     * @param minOffset
     * @param maxOffset
     * @return
     */
    private int clamp(int i, int minOffset, int maxOffset) {
        if (i > maxOffset) {
            return maxOffset;
        } else if (i < minOffset) {
            return minOffset;
        } else {
            return i;
        }
    }
}
