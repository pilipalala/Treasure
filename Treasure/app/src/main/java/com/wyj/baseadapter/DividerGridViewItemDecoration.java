package com.wyj.baseadapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by wangyujie
 * Date 2018/3/4
 * Time 14:37
 * TODO
 */

public class DividerGridViewItemDecoration extends RecyclerView.ItemDecoration {


    private int mSpanCount;
    private int lineWidth = 10;
    private int lineHeight = 10;

    private Drawable mDivider;
    int[] attrs = new int[]{
            android.R.attr.listDivider
    };
    private final Paint mPaint;


    public DividerGridViewItemDecoration(Context context) {
        TypedArray array = context.obtainStyledAttributes(attrs);
        mDivider = array.getDrawable(0);
        array.recycle();
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);

    }


    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizontal(c, parent);
    }

    /**
     * 画垂水平间隔线
     *
     * @param c
     * @param parent
     */
    private void drawHorizontal(Canvas c, RecyclerView parent) {

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {

            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - params.leftMargin;
            int right = child.getRight() + params.rightMargin + lineWidth;

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + lineHeight;

//            mDivider.setBounds(left, top, right, bottom);
//            mDivider.draw(c);
            Rect rect = new Rect(left, top, right, bottom);
            c.drawRect(rect, mPaint);
        }

    }

    /**
     * 画垂直间隔线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {

        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin;
            int right = left + lineWidth;
            int top = child.getTop() - params.topMargin;
            int bottom = child.getBottom() + params.bottomMargin;

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 是否是最后一列
     *
     * @param itemPosition
     * @param parent
     * @return
     */
    public boolean isLastColumn(int itemPosition, RecyclerView parent) {
        mSpanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((itemPosition + 1) % mSpanCount == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否是最后一行
     *
     * @param itemPosition
     * @param parent
     * @return
     */
    public boolean isLastRow(int itemPosition, RecyclerView parent) {
        mSpanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int count = parent.getAdapter().getItemCount();
            int lastRowCount = count % mSpanCount;
            //最后一行数量小于spanCount
            if (lastRowCount == 0 || lastRowCount < mSpanCount) {
                return true;
            }
        }


        return false;
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        //1.调用此方法(首先会先获取条目之间的间隙宽度----Rect矩形区域)
        //获得条目的偏移量(所有的条目都会调用一次该方法)
        int bottom = lineHeight;
        int right = lineWidth;
        if (isLastColumn(itemPosition, parent)) {
            right = 0;
        }
        if (isLastRow(itemPosition, parent)) {
            bottom = 0;
        }
        outRect.set(0, 0, right, bottom);
    }

    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    public int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager lm = (GridLayoutManager) layoutManager;
            return lm.getSpanCount();
        }
        return 0;
    }
}
