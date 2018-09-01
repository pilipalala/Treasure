package com.wyj.gifview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.MyUtils;

/**
 * @author wangyujie
 * @date 2018/8/28.15:27
 * @describe 添加描述
 */
public class FlowerView extends View {

    private int mCenterX;
    private int mCenterY;
    private static final String TAG = "FlowerView";
    private ValueAnimator mAnimator;

    public FlowerView(Context context) {
        this(context, null);
    }

    public FlowerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private Paint mPaint = new Paint();

    private void init() {
    }

    public void startAnim() {
        mAnimator = ValueAnimator.ofFloat(1, 0);

        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                /*得到某个时间计算的结果---这个时间点旋转的角度*/
                postInvalidate();
            }
        });
        mAnimator.setInterpolator(new LinearInterpolator());
        mAnimator.setDuration(300);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float rotationAngle = (float) (2 * Math.PI / 3);
        Bitmap bitmap = MyUtils.setBitmapSize(BitmapFactory.decodeResource(getResources(), R.mipmap.flower));

        for (int i = 0; i < 3; i++) {
            double angle = i * rotationAngle;
            float left = (float) (Math.cos(angle) * 60 + mCenterX);
            float top = (float) (Math.sin(angle) * 60 + mCenterY);
            canvas.drawBitmap(bitmap, left - bitmap.getWidth() / 2, top - bitmap.getWidth() / 2, mPaint);
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = 500;
        int height = 500;
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, heightSize);
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, height);
            width = widthSize;
        } else {
            setMeasuredDimension(widthSize, heightSize);
            width = widthSize;
            height = heightSize;
        }
        mCenterX = width / 2;
        mCenterY = height / 2;
    }

}
