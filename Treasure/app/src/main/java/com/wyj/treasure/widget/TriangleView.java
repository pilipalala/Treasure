package com.vtrump.bonso.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 * @date 2019/7/29.18:53
 * @describe 添加描述
 */
public class TriangleView extends View {
    private static final int TOP = 0;
    private static final int BOTTOM = 1;
    private static final int RIGHT = 2;
    private static final int LEFT = 3;
    private static final int DEFUALT_WIDTH = 10;
    private static final int DEFUALT_HEIGHT = 6;
    private Paint mPaint;
    private int mColor;
    private int mWidth;
    private int mHeight;
    private int mDirection;
    private Path mPath;

    public TriangleView(final Context context) {
        this(context, null);
    }

    public TriangleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        mColor = Color.RED;
        mPaint.setColor(mColor);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPath = new Path();
        mDirection = TOP;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //宽高
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = dp2px(DEFUALT_WIDTH);
        setMeasuredDimension(measureSize(widthMeasureSpec, size), measureSize(heightMeasureSpec, size));
    }

    /**
     * dp2px
     */
    private int dp2px(float dpValue) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * 当布局为wrap_content时设置默认长宽
     */
    private int measureSize(int measureSpec, int defaultSize) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                defaultSize = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                break;
        }
        return defaultSize;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mDirection) {
            case TOP:
                mPath.moveTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth / 2, 0);
                break;
            case BOTTOM:
                mPath.moveTo(0, 0);
                mPath.lineTo(mWidth / 2, mHeight);
                mPath.lineTo(mWidth, 0);
                break;
            case RIGHT:
                mPath.moveTo(0, 0);
                mPath.lineTo(0, mHeight);
                mPath.lineTo(mWidth, mHeight / 2);
                break;
            case LEFT:
                mPath.moveTo(0, mHeight / 2);
                mPath.lineTo(mWidth, mHeight);
                mPath.lineTo(mWidth, 0);
                break;
            default:
                break;
        }

        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }
}
