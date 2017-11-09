package com.wyj.mdcustom.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;

import com.wyj.treasure.R;

/**
 * Created by wangyujie
 * Date 2017/10/29
 * Time 15:15
 * TODO
 * 3个动画 分别是
 * 小圆旋转的动画
 * 聚合的动画
 * 空心圆的扩散效果，显示主界面
 */

public class SplashView extends View {
    /**
     * 大圆(里面包换很多小圆的)的半径
     */
    private float mRotationRadius = 200;

    /**
     * 第一个小圆的半径
     */
    private float mCircleRadius = 50;

    /**
     * 小圆圆的颜色列表，在initialize方法里面初始化
     */
    private int[] mCirceClors;

    /**
     * 大圆和小圆旋转的时间
     */
    private long mRotationDurartion = 1600;//ms

    /**
     * 第二部分动画的执行总时间(包括第二个动画时间，各占1/2)
     */
    private long mSplashDuration = 1600;//ms
    /**
     * 整体的背景颜色
     */
    private int mSplashBgColor = Color.WHITE;

    /**
     * 参数，保存了一些绘制状态，会被动态的改变
     */
    /**
     * 空心圆初始半径
     */
    private float mHoleRadius = 0f;

    /**
     * 当前大圆旋转角度(弧度)
     */
    private float mCurrentRotationAngle = 0f;
    /**
     * 当前大圆的半径
     */
    private float mCurrentRotationRadius = mRotationRadius;

    /**
     * 绘制圆的画笔
     */
    private Paint mPaint = new Paint();
    /**
     * 绘制背景的画笔
     */
    private Paint mPaintBackground = new Paint();

    /**
     * 屏幕正中心点坐标
     */
    private float mCenterX;
    private float mCenterY;
    /**
     * 屏幕对角线一半
     */
    private float mDiagonalDist;
    private ValueAnimator mAnimator;
    /**
     * 设计模式:策略
     */
    private SplashState mState = null;

    public SplashView(Context context) {
        super(context);
        init();
    }

    private void init() {
        mPaint.setAntiAlias(true);//抗锯齿
        mPaintBackground.setAntiAlias(true);
        mPaintBackground.setStyle(Paint.Style.STROKE);
        mPaintBackground.setColor(mSplashBgColor);
        mCirceClors = getContext().getResources().getIntArray(R.array.splash_circle_colors);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterX = w / 2;
        mCenterY = h / 2;
        /**
         * 屏幕对角线一半
         * */
        mDiagonalDist = (float) Math.sqrt((w * w + h * h) / 2);
    }

    private void drawCircle(Canvas canvas) {
        /**
         *得到每个小圆的间隔角度
         * */
        float rotationAngle = (float) (2 * Math.PI / mCirceClors.length);
        for (int i = 0; i < mCirceClors.length; i++) {
            double angle = i * rotationAngle + mCurrentRotationAngle;
            float cx = (float) (mCurrentRotationRadius * Math.cos(angle) + mCenterX);
            float cy = (float) (mCurrentRotationRadius * Math.sin(angle) + mCenterY);
            mPaint.setColor(mCirceClors[i]);
            canvas.drawCircle(cx, cy, mCircleRadius, mPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        if (mHoleRadius > 0f) {
            /*得到画笔的宽度 = 对角线/2 - 空心部分圆的半径*/
            float stridWidth = mDiagonalDist - mHoleRadius;
            mPaintBackground.setStrokeWidth(stridWidth);//设置画笔的线宽
            float radius = mHoleRadius + stridWidth / 2;
            canvas.drawCircle(mCenterX, mCenterY, radius, mPaintBackground);
        } else {
            canvas.drawColor(mSplashBgColor);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        /*绘制动画*/
        if (mState == null) {
            mState = new RotationState();
        }
        mState.drawState(canvas);
        super.onDraw(canvas);
    }

    public void splashDisappear() {
        //完毕后进入进场动画---动画1结束，动画23开启
        if (mState != null && mState instanceof RotationState) {
            RotationState rotationState = (RotationState) mState;
            rotationState.cancel();
            post(new Runnable() {
                @Override
                public void run() {
                    mState = new MergingState();
                }
            });
        }

    }

    private abstract class SplashState {
        public abstract void drawState(Canvas canvas);
    }

    /**
     * 1、小圆旋转动画
     * 不断得绘制小圆   控制坐标   旋转的角度   公转的半径
     */
    private class RotationState extends SplashState {
        /**
         * 动画初始化
         */
        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        public RotationState() {
            /*花1600ms，计算某个时刻当前角度是多少 : 0~2π中的某个值*/
            mAnimator = ValueAnimator.ofFloat(0, (float) Math.PI * 2);

            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    /*得到某个时间计算的结果---这个时间点旋转的角度*/
                    mCurrentRotationAngle = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration(mRotationDurartion);
            mAnimator.setRepeatCount(ValueAnimator.INFINITE);
            mAnimator.start();
        }

        /**
         * 停止动画
         */
        public void cancel() {
            mAnimator.cancel();
        }

        /**
         * 绘制动画
         *
         * @param canvas
         */
        @Override
        public void drawState(Canvas canvas) {
            /*擦黑板--绘制背景*/
            drawBackground(canvas);
            /*绘制小圆*/
            drawCircle(canvas);
        }
    }

    /**
     * 2、聚合动画
     */
    private class MergingState extends SplashState {
        public MergingState() {
            /*花1600ms，计算某个时刻当前角度是多少 : 0~r中的某个值*/
            mAnimator = ValueAnimator.ofFloat(0, mRotationRadius);

            mAnimator.setInterpolator(new OvershootInterpolator(10f));
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    /*得到某个时间计算的结果---这个时间点大圆的半径*/
                    mCurrentRotationRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mState = new ExpandState();
                }
            });
            mAnimator.setDuration(mSplashDuration*2);
            mAnimator.reverse();//翻转执行
        }

        @Override
        public void drawState(Canvas canvas) {
            /*擦黑板--绘制背景*/
            drawBackground(canvas);
            /*绘制小圆*/
            drawCircle(canvas);
        }
    }

    /**
     * 3 、水波纹的空心扩散动画
     * 不断的控制画笔的宽度---空心圆的半径
     */
    private class ExpandState extends SplashState {
        public ExpandState() {
            /*花800ms，计算某个时刻当前空心圆的半径 : 0~对角线的一半中的某个值*/
            mAnimator = ValueAnimator.ofFloat(0, mDiagonalDist);
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    /*得到某个时间计算的结果---这个时间点空心圆的半径*/
                    mHoleRadius = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            mAnimator.setDuration(mSplashDuration * 2);
            mAnimator.start();
        }

        @Override
        public void drawState(Canvas canvas) {
            /*擦黑板--绘制背景*/
            drawBackground(canvas);
        }
    }
}
