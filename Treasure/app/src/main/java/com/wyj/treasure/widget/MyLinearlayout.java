package com.wyj.treasure.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author wangyujie
 *         on 2018/1/16.13:43
 *         TODO
 */

public class MyLinearlayout extends ViewGroup {
    public MyLinearlayout(Context context) {
        this(context, null);
    }

    public MyLinearlayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyLinearlayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int lineWidth = 0;//记录每一行的宽度
        int lineHeight = 0;//记录每一行的高度
        int height = 0;//记录整个FlowLayout所占高度
        int width = 0;//记录整个FlowLayout所占宽度
        int count = getChildCount();

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //测量子控件
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //获得子控件的高度和宽度
            /*int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();*/


            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > widthSize) {
                //需要换行
                width = Math.max(lineWidth, childWidth);
                height += lineHeight;
                //因为由于盛不下当前控件，而将此控件调到下一行，所以将此控件的高度和宽度初始化给lineHeight、lineWidth
                lineHeight = childHeight;
                lineWidth = childWidth;

            } else {
                // 否则累加值lineWidth,lineHeight取最大高度
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidth += childWidth;
            }
            //最后一行是不会超出width范围的，所以要单独处理
            if (i == count - 1) {
                height += lineHeight;
                width = Math.max(width, lineWidth);
            }
            //得到最大宽度，并且累加高度
//            height += childHeight;
//            width = Math.max(childWidth, width);
        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);

    }

    /**
     * getMeasuredWidth()与getWidth()
     * 区别主要体现在下面几点：
     * - 首先getMeasureWidth()方法在measure()过程结束后就可以获取到了，而getWidth()方法要在layout()过程结束后才能获取到。
     * - getMeasureWidth()方法中的值是通过setMeasuredDimension()方法来进行设置的，而getWidth()方法中的值则是通过layout(left,top,right,bottom)方法设置的。
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int lineWidth = 0;//累加当前行的行宽
        int lineHeight = 0;//当前行的行高
        int top = 0, left = 0;//当前坐标的top坐标和left坐标
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = null;
            if (child.getLayoutParams() instanceof MarginLayoutParams) {
                lp = (MarginLayoutParams) child.getLayoutParams();
            }
            if (lp == null) {
                return;
            }

//            int childWidth = child.getMeasuredWidth();
//            int childHeight = child.getMeasuredHeight();

            int childWidth = child.getMeasuredWidth() + lp.rightMargin + lp.leftMargin;
            int childHeight = child.getMeasuredHeight() + lp.bottomMargin + lp.topMargin;
            if (lineWidth + childWidth > getMeasuredWidth()) {
                //换行
                top += lineHeight;
                left = 0;
                lineHeight = childHeight;
                lineWidth = childWidth;
            } else {
                lineHeight = Math.max(lineHeight, childHeight);
                lineWidth += childWidth;
            }
            //计算childView的left,top,right,bottom
            int lc = left + lp.leftMargin;
            int tc = top + lp.topMargin;
            int rc = lc + child.getMeasuredWidth();
            int bc = tc + child.getMeasuredHeight();

            child.layout(lc, tc, rc, bc);
//            top += childHeight;
            //将left置为下一子控件的起始点
            left += childWidth;
        }

    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);

    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);

    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);

    }
}
