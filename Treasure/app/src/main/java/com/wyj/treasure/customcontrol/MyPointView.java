package com.wyj.treasure.customcontrol;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.wyj.treasure.utils.LogUtil;

/**
 * @author wangyujie
 *         on 2017/12/12.10:40
 *         TODO
 */

public class MyPointView extends View {
    private Point mPoint = new Point(10);
    private float mPointFloat;

    public MyPointView(Context context) {
        this(context, null);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mPoint != null) {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            LogUtil.i("mPointFloat---onDraw-----" + mPointFloat);
//            canvas.drawCircle(300, 300, mPointFloat, paint);
            canvas.drawCircle(500, 500, mPoint.getRadius(), paint);
        }
    }

    public void doPointAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), new Point(50), new Point(200));
        animator.setDuration(3000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPoint = (Point) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.start();
    }

    public void doPointFloatAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(50, 200);
        animator.setDuration(3000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mPointFloat = (float) animation.getAnimatedValue();
                LogUtil.i("mPointFloat" + mPointFloat);
                invalidate();
            }
        });
        animator.start();
    }

    public void setPointRadius(float radius) {
        mPoint.setRadius(radius);
        invalidate();
    }
    public float getPointRadius(){
        return 50f;
    }

    private class PointEvaluator implements TypeEvaluator<Point> {

        @Override
        public Point evaluate(float fraction, Point startValue, Point endValue) {
            float startRadius = startValue.getRadius();
            float endRadius = endValue.getRadius();
            float current = startRadius + (endRadius - startRadius) * fraction;
            return new Point(current);
        }
    }
}
