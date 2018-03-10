package com.wyj.baseadapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wangyujie
 * Date 2018/3/4
 * Time 14:37
 * TODO
 */

public class DividerListViewItemDecoration extends RecyclerView.ItemDecoration {


    private final Paint mPaint;
    int[] attrs = new int[]{
            android.R.attr.listDivider
    };
    private int mOritation = LinearLayoutManager.VERTICAL;
    private Drawable mDivider;


    public DividerListViewItemDecoration(Context context, int oritation, int color) {
        TypedArray array = context.obtainStyledAttributes(attrs);
        mDivider = array.getDrawable(0);
        mPaint = new Paint();
        mPaint.setColor(color);
        setOritation(oritation);
    }

    private void setOritation(int oritation) {
        if (oritation != LinearLayoutManager.HORIZONTAL && oritation != LinearLayoutManager.VERTICAL) {
            throw new IllegalArgumentException("哥们，逗我呢？");
        }
        mOritation = oritation;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOritation == LinearLayoutManager.VERTICAL) {
            drawVertical(c, parent);
        } else {
            drawHorizontal(c, parent);
        }

    }

    /**
     * 画垂直线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getHeight() - parent.getPaddingBottom();

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;

            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }

    }

    /**
     * 画水平线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {


        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + params.bottomMargin + Math.round(child.getTranslationY());

            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }


    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //1.调用此方法(首先会先获取条目之间的间隙宽度----Rect矩形区域)
        //获得条目的偏移量(所有的条目都会调用一次该方法)
        if (mOritation == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicWidth());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
