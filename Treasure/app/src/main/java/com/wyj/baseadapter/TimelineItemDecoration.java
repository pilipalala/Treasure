package com.wyj.baseadapter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author wangyujie
 *         on 2018/3/5.10:06
 *         TODO
 */

public class TimelineItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    //ItemView左边的间距
    private float mOffsetLeft;
    //时间轴结点的半径
    private float mNodeRadius;
    //分割线高度
    private float mDividerHeight;

    public TimelineItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);


        mOffsetLeft = 200;
        mNodeRadius = 20;
        mDividerHeight = 1;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //第一个ItemView不需要在上面绘制分割线
        if (parent.getChildAdapterPosition(view) != 0) {
            //这里直接硬编码为1px
            outRect.top = 1;
        }

        outRect.left = (int) mOffsetLeft;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int childCount = parent.getChildCount();


        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);


            float dividerTop = view.getBottom();
            float dividerLeft = parent.getPaddingLeft() + mOffsetLeft;
            float dividerBottom = dividerTop + mDividerHeight;
            float dividerRight = parent.getWidth() - parent.getPaddingRight();

            /*设置分割线*/
            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);


            int left = parent.getPaddingLeft();
            int top = view.getTop();
            int bottom = view.getBottom();


            //绘制时间轴结点
            float centerX = left + mOffsetLeft / 2;
            float centerY = top / 2 + bottom / 2;
            mPaint.setStyle(Paint.Style.STROKE);
            c.drawCircle(centerX, centerY, mNodeRadius, mPaint);
            mPaint.setStyle(Paint.Style.FILL_AND_STROKE);


            //绘制上半部轴线
            float upLineTopX = centerX;
            float upLineTopY = top;
            float upLineBottomX = centerX;
            float upLineBottomY = centerY - mNodeRadius;
            c.drawLine(upLineTopX, upLineTopY, upLineBottomX, upLineBottomY, mPaint);


            //绘制下半部轴线
            float downLineTopX = centerX;
            float downLineTopY = centerY + mNodeRadius;
            float downLineBottomX = centerX;
            float downLineBottomY = bottom + mDividerHeight;
            c.drawLine(downLineTopX, downLineTopY, downLineBottomX, downLineBottomY, mPaint);



            c.drawCircle(view.getLeft(),centerY,mNodeRadius,mPaint);



        }
    }
}
