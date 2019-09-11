package com.vtrump.bonso;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.orhanobut.logger.Logger;

import java.math.BigDecimal;

/**
 * @author wangyujie
 * @date 2019/7/25.17:01
 * @describe 体重仪表盘
 */
public class DashboardView extends View {
    //控件宽高
    private int mWidth;
    private int mHeight;
    //边距
    private float mPadding;
    //仪表盘半径
    private int mRadius;
    //圆环最小值和最大值
    private int mMin;
    private int mMax;
    //圆环起始角度
    private float mArcStartAngle = 135f;
    //圆环范围大小
    private float mArcSweepAngle = 270f;
    //动画时长
    private long mProgressAnimTime = 500;
    //当前进度的角度
    private float mProgressSweepAngle = 0f;
    //默认控件大小
    private final static int DEFAULT_SIZE = 250;
    //默认边距
    private final static int DEFAULT_PADDING = 10;
    //圆环颜色
//    private int[] doughnutColors = new int[]{Color.GREEN, Color.YELLOW, Color.RED};
    private int[] doughnutColors = new int[]{
            Color.parseColor("#CAE0FF"),
            Color.parseColor("#85B8FF"),
            Color.parseColor("#66A6FF"),
            Color.parseColor("#64A5FF"),
            Color.parseColor("#2681FF"),
            Color.parseColor("#0970FF"),
    };
    //刻度总数
    private int mCalibrationTotalNumber;
    //每个刻度之间的角度
    private float mSmallCalibrationBetweenAngle;
    //刻度文本
    private int[] mCalibrationNumberText = new int[]{0, 250};


    //当前值
    private float mValue;
    //体重数值画笔
    private Paint mPaintWeightValue;
    //体重单位画笔
    private Paint mPaintWeightUnit;
    //体重文字画笔
    private Paint mPaintWeight;
    //圆环环画笔
    private Paint mPaintInnerArc;
    //进度点画笔
    private Paint mPaintProgressPoint;
    //刻度文字画笔
    private Paint mPaintCalibrationText;
    //刻度画笔
    private Paint mPaintSmallCalibration;
    //圆环环区域
    private RectF mRectInnerArc;
    //进度条的圆点属性
    private float[] mProgressPointPosition;
    private float mProgressPointRadius;
    //刻度起始位置和结束位置
    private float mCalibrationStart;
    private float mCalibrationEnd;
    //刻度的文本位置
    private float mCalibrationTextStart;
    // 体重数值画笔属性
    private final static int DEFAULT_VALUE_TEXT_SIZE = 50;
    private final static int DEFAULT_VALUE_TEXT_COLOR = Color.BLACK;
    // 体重文字画笔属性
    private final static int DEFAULT_VALUE_LEVEL_TEXT_SIZE = 15;
    private final static int DEFAULT_VALUE_LEVEL_COLOR = Color.parseColor("#999999");
    //圆环的默认属性
    private static final int DEFAULT_INNER_ARC_WIDTH = 10;
    private static final int DEFAULT_INNER_ARC_COLOR = Color.parseColor("#64A5FF");
    //进度点的默认属性
    private static final int DEFAULT_PROGRESS_POINT_RADIUS = 6;
    private static final int DEFAULT_PROGRESS_POINT_COLOR = Color.parseColor("#3F8FFF");
    // 刻度画笔默认值
    private final static int DEFAULT_SMALL_CALIBRATION_WIDTH = 5;
    private final static int DEFAULT_SMALL_CALIBRATION_COLOR = Color.WHITE;
    // 默认刻度文字画笔参数
    private final static int DEFAULT_CALIBRATION_TEXT_TEXT_SIZE = 10;
    private final static int DEFAULT_CALIBRATION_TEXT_TEXT_COLOR = Color.parseColor("#CAE0FF");
    //文字之间的间距
    private int mTextSpacing;
    //中间文字之间的间距
    private static final int DEFAULT_TEXT_SPACING = 7;

    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, @NonNull AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, @NonNull AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //min=0，max=250kg
        mTextSpacing = dp2px(DEFAULT_TEXT_SPACING);
        mMin = mCalibrationNumberText[0];
        mMax = mCalibrationNumberText[mCalibrationNumberText.length - 1];
        //计算刻度的相关数据
        resetCalibrationData();
        //初始化
        initView();
    }

    private void initView() {

        //数值画笔
        mPaintWeightValue = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWeightValue.setTextAlign(Paint.Align.CENTER);
        mPaintWeightValue.setTextSize(sp2px(DEFAULT_VALUE_TEXT_SIZE));
        mPaintWeightValue.setColor(DEFAULT_VALUE_TEXT_COLOR);

        //体重单位画笔
        mPaintWeightUnit = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWeightUnit.setTextAlign(Paint.Align.CENTER);
        mPaintWeightUnit.setTextSize(sp2px(12));
        mPaintWeightUnit.setColor(DEFAULT_VALUE_TEXT_COLOR);

        //体重文字画笔
        mPaintWeight = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWeight.setTextAlign(Paint.Align.CENTER);
        mPaintWeight.setTextSize(sp2px(DEFAULT_VALUE_LEVEL_TEXT_SIZE));
        mPaintWeight.setColor(DEFAULT_VALUE_LEVEL_COLOR);

        //圆环画笔
        mPaintInnerArc = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintInnerArc.setStrokeWidth(dp2px(DEFAULT_INNER_ARC_WIDTH));
        mPaintInnerArc.setColor(DEFAULT_INNER_ARC_COLOR);
        mPaintInnerArc.setStyle(Paint.Style.STROKE);
        mPaintInnerArc.setStrokeCap(Paint.Cap.ROUND);

        //进度点画笔
        mPaintProgressPoint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintProgressPoint.setStyle(Paint.Style.FILL);
        mPaintProgressPoint.setColor(DEFAULT_PROGRESS_POINT_COLOR);
        mProgressPointRadius = dp2px(DEFAULT_PROGRESS_POINT_RADIUS);

        //刻度画笔
        mPaintSmallCalibration = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintSmallCalibration.setStrokeWidth(dp2px(DEFAULT_SMALL_CALIBRATION_WIDTH));
        mPaintSmallCalibration.setColor(DEFAULT_SMALL_CALIBRATION_COLOR);

        //刻度文字画笔
        mPaintCalibrationText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCalibrationText.setTextAlign(Paint.Align.CENTER);
        mPaintCalibrationText.setTextSize(sp2px(DEFAULT_CALIBRATION_TEXT_TEXT_SIZE));
        mPaintCalibrationText.setColor(DEFAULT_CALIBRATION_TEXT_TEXT_COLOR);

        //进度点的图片
        mProgressPointPosition = new float[2];


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //屏幕宽高
        mWidth = w;
        mHeight = h;
        //半径
        mRadius = Math.min(mWidth, mHeight) / 2;

        Logger.d("mWidth:" + mWidth + ",mHeight:" + mHeight + ",mRadius:" + mRadius);
        //初始化圆环
        initArcRect(mPadding, mPadding, mWidth - mPadding, mWidth - mPadding);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int size = dp2px(DEFAULT_SIZE);
        mPadding = Math.max(Math.max(getPaddingLeft(), getPaddingTop()),
                Math.max(getPaddingRight(), getPaddingBottom()));
        mPadding = Math.max(dp2px(DEFAULT_PADDING), mPadding);
        setMeasuredDimension(measureSize(widthMeasureSpec, size), measureSize(heightMeasureSpec, size));
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

        //绘制圆环
        drawArc(canvas, mArcStartAngle, mArcSweepAngle);

//        //绘制进度圆环
        drawProgressArc(canvas, mArcStartAngle, mProgressSweepAngle);

        //绘制文本
        drawText(canvas, mValue, "体重", "kg");
    }

    private void drawArc(Canvas canvas, float arcStartAngle, float arcSweepAngle) {
        //绘制圆环
        SweepGradient gradient = new SweepGradient(-180, -180, doughnutColors, null);
        mPaintInnerArc.setShader(gradient);
        canvas.drawArc(mRectInnerArc, arcStartAngle, arcSweepAngle, false, mPaintInnerArc);

        //绘制刻度
        drawCalibration(canvas, arcStartAngle);
    }

    private void drawProgressArc(Canvas canvas, float arcStartAngle, float progressSweepAngle) {
//        if (progressSweepAngle == 0) {
//            return;
//        }
        Path path = new Path();
        //添加进度圆环的区域
        path.addArc(mRectInnerArc, arcStartAngle, progressSweepAngle);
        //计算切线值和为重
        PathMeasure pathMeasure = new PathMeasure(path, false);
        pathMeasure.getPosTan(pathMeasure.getLength(), mProgressPointPosition, null);
        //绘制进度点
        if (mProgressPointPosition[0] != 0 && mProgressPointPosition[1] != 0) {
            canvas.drawCircle(mProgressPointPosition[0], mProgressPointPosition[1], mProgressPointRadius, mPaintProgressPoint);
        } else {
            float strokeWidth = mPaintInnerArc.getStrokeWidth();
            float r = mRadius + mPadding;
            Log.e("DashboardView_282", "-->半径: " + r);
            double cirle = r * Math.sin((360 - mArcSweepAngle) / 2);
            float cirleX = (float) (r - cirle + strokeWidth);
            float cirleY = (float) (r + cirle - strokeWidth);
            Log.e("DashboardView_282", "-->strokeWidth: " + strokeWidth);
            Log.e("DashboardView_282", "-->mHeight: " + mHeight);
            Log.e("DashboardView_282", "-->mWidth: " + mWidth);
            Log.e("DashboardView_282", "-->cirle: " + cirle);
            Log.e("DashboardView_282", "-->mRadius: " + mRadius);
            Log.e("DashboardView_282", "-->mPadding: " + mPadding);
            Log.e("DashboardView_282", "-->X轴: " + cirleX);
            Log.e("DashboardView_282", "-->Y轴: " + cirleY);
            canvas.drawCircle(cirleX, cirleY, mProgressPointRadius, mPaintProgressPoint);
        }
    }

    private void drawText(Canvas canvas, float value, String weight, String weightUnit) {
        mPaintWeightValue.setTextSize(sp2px(DEFAULT_VALUE_TEXT_SIZE));
        float marginTop = mRadius + mTextSpacing;
        float valueWidth = mPaintWeightValue.measureText(String.valueOf(value));
        float unitWidth = mPaintWeightUnit.measureText(weightUnit);
        float mTextSize = DEFAULT_VALUE_TEXT_SIZE;
        while ((valueWidth + unitWidth + sp2px(mTextSpacing)) > (mRadius * 2 - sp2px(DEFAULT_PADDING * 2))) {
            mPaintWeightValue.setTextSize(sp2px(mTextSize--));
            valueWidth = mPaintWeightValue.measureText(String.valueOf(value));
        }
        //绘制数值
        canvas.drawText(String.valueOf(value), mRadius - unitWidth / 2, marginTop, mPaintWeightValue);
        //绘制单位
        canvas.drawText(weightUnit, mRadius + valueWidth / 2 + mTextSpacing / 2, marginTop, mPaintWeightUnit);
        //绘制体重文字
        marginTop = marginTop + getPaintHeight(mPaintWeight, weight) + mTextSpacing * 2;
        canvas.drawText(weight, mRadius, marginTop, mPaintWeight);
    }

    private void drawCalibration(Canvas canvas, float arcStartAngle) {
        //旋转画布
        canvas.save();
        canvas.rotate(arcStartAngle - 270, mRadius, mRadius);
        //遍历数量
        for (int i = 0; i < mCalibrationTotalNumber; i++) {
            //绘制刻度文字
            if (i == 0) {
//                canvas.drawText(String.valueOf(mCalibrationNumberText[0]), mRadius, mCalibrationTextStart, mPaintCalibrationText);
            } else if (i == mCalibrationTotalNumber - 1) {
//                canvas.drawText(String.valueOf(mCalibrationNumberText[mCalibrationNumberText.length - 1]), mRadius, mCalibrationTextStart, mPaintCalibrationText);
            } else {
                //画白色分割线
                canvas.drawLine(mRadius, mCalibrationStart, mRadius, mCalibrationEnd, mPaintSmallCalibration);
            }
//            旋转
            canvas.rotate(mSmallCalibrationBetweenAngle, mRadius, mRadius);
        }
        canvas.restore();
    }

    private void initArcRect(float left, float top, float right, float bottom) {
        //外环区域
        mRectInnerArc = new RectF(left, top, right, bottom);
        initInnerRect();
    }

    private void initInnerRect() {
        //计算刻度位置
        mCalibrationStart = mRectInnerArc.top - mPaintInnerArc.getStrokeWidth() / 2;
        mCalibrationEnd = mCalibrationStart + mPaintInnerArc.getStrokeWidth();
        //刻度文字位置
        mCalibrationTextStart = mCalibrationEnd + dp2px(13);
    }


    /**
     * 重置角度信息
     */
    private void resetCalibrationData() {
        //计算总共有多少个刻度
        mCalibrationTotalNumber = 4;
        //计算刻度之间的角度
        mSmallCalibrationBetweenAngle = mArcSweepAngle / 3;
    }

    /**
     * 测量画笔高度
     */
    private float getPaintHeight(Paint paint, String text) {
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
    }

    /**
     * 设置当前值
     *
     * @param value  当前数字
     * @param isAnim 是否开启动画
     * @param reset  true:从min开始进行动画 / false:从当前值开始绘制
     */
    public void setValue(float value, boolean isAnim, boolean reset) {
        value = value < mMin ? mMin : value > mMax ? mMax : value;
        //计算进度需要转动的角度
        float progressSweepAngle = computeProgressSweepAngle(value);
        //如果开启动画
        if (isAnim) {
            startProgressAnim(value, progressSweepAngle, reset);
        } else {
            mValue = value;
            mProgressSweepAngle = progressSweepAngle;
            postInvalidate();
        }
    }

    /**
     * 启动进度动画
     */
    private void startProgressAnim(float value, float progressSweepAngle, boolean reset) {
        //启动角度变动动画
        float angle = reset ? 0 : mProgressSweepAngle;
        ValueAnimator angleAnim = ValueAnimator.ofFloat(angle, progressSweepAngle);
        angleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        angleAnim.setDuration(mProgressAnimTime);
        angleAnim.addUpdateListener(valueAnimator -> {
            //设置当进度
            mProgressSweepAngle = (float) valueAnimator.getAnimatedValue();//215
        });
        angleAnim.start();
        float start = reset ? mMin : mValue;
        //启动文字变动动画
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(start, value);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.setDuration(mProgressAnimTime);
        valueAnimator.addUpdateListener(valueAnimator1 -> {
            //设置当进度
//            mValue = (Float) valueAnimator1.getAnimatedValue();
            BigDecimal b = new BigDecimal((Float) valueAnimator1.getAnimatedValue());
            mValue = b.setScale(2, BigDecimal.ROUND_DOWN).floatValue();
            postInvalidate();
        });
        valueAnimator.start();
    }

    /**
     * 计算需要进度条需要转动的角度
     */
    private float computeProgressSweepAngle(float value) {
        //如果小于最小值
        if (value <= mMin) {
            return 0;
        }
        //如果大于最大值
        if (value >= mMax) {
            return mArcSweepAngle;
        }
        //计算其他情况
        int index = findValueInterval(value);
        //如果不在范围内的
        if (index == -1) {
            return ((mValue - mMin) / (mMax - mMin)) * mArcSweepAngle;
        }
        index--;
        float angle = mArcSweepAngle * index;
        float intervalMin = mCalibrationNumberText[index];
        float intervalMax = mCalibrationNumberText[index + 1];
        //当前数值所在区域文字
        return angle + ((value - intervalMin) / (intervalMax - intervalMin)) * mArcSweepAngle;
    }

    /**
     * 寻找value所在区间
     */
    private int findValueInterval(float value) {
        int i = -1;
        //是否有区间数据
        if (mCalibrationNumberText != null && mCalibrationNumberText.length > 0) {
            for (int j = 0; j < mCalibrationNumberText.length; j++) {
                if (mCalibrationNumberText[j] > value) {
                    return j;
                }
            }
        }
        return i;
    }

    /**
     * dp2px
     */
    private int dp2px(float dpValue) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5f);
    }

    /**
     * sp2px
     */
    private int sp2px(float spValue) {
        float fontScale = getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取最大值
     */
    public int getMax() {
        return mMax;
    }

    /**
     * 获取最小值
     */
    public int getMin() {
        return mMin;
    }

    /**
     * 获取当前值
     */
    public float getValue() {
        return mValue;
    }

}
