package com.wyj.colortracktextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.wyj.treasure.R;

/**
 * @author wangyujie
 * on 2017/11/30.15:51
 * TODO 颜色跟踪的textview
 * <p>
 * <p>
 * 知识点 canvas 的 掌握
 */

public class ColorTrackTextView extends android.support.v7.widget.AppCompatTextView {
    /**
     * 1、实现一个文字两种颜色 -- 绘制不变色字体的画笔
     */
    private Paint mOriginPaint;
    /**
     * 2、实现一个文字两种颜色 -- 绘制变色字体的画笔
     */
    private Paint mChangePaint;

    /**
     * 实现一个文字两种颜色 -- 当前滑动的进度
     */
    private float mCurrentProgress = 0f;
    /**
     * 当前朝向
     */
    private Direction mDirection = Direction.LEFE_TO_RIGHT;
    /**
     *
     * */
    private int mOridianColor = Color.BLACK;
    /**
     *
     * */
    private int mChangeColor = Color.RED;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ColorTrackTextView);
        mOridianColor = typedArray.getColor(R.styleable.ColorTrackTextView_oridianColor, mOridianColor);
        mChangeColor = typedArray.getColor(R.styleable.ColorTrackTextView_changeColor, mChangeColor);


        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        mOriginPaint = getPaints(mOridianColor);
        mChangePaint = getPaints(mChangeColor);
    }

    /**
     * 设置原始不变色的字体颜色
     *
     * @param color
     */
    public void setOridianColor(int color) {
        mOridianColor = color;
    }

    /**
     * 设置变色字体的颜色
     *
     * @param color
     */
    public void setChangeColor(int color) {
        mChangeColor = color;
    }

    /**
     * 根据颜色获取画笔
     *
     * @param color
     * @return
     */
    public Paint getPaints(int color) {
        Paint paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //防抖动
        paint.setDither(true);
        //设置字体的大小
        paint.setTextSize(getTextSize());
        //设置颜色
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*不能使用系统的了*/
//        super.onDraw(canvas);

        /*1实现不同的颜色*/

        /*计算中间的位置*/
        int width = getWidth();
        int middle = (int) (width * mCurrentProgress);


        /*根据中间的位置去绘制  两边不同的文字颜色  截取绘制文字的范围*/

        String text = getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }

        drawOriginText(text, canvas, middle);
        drawChangeText(text, canvas, middle);

    }

    /**
     * 绘制变色的部分
     *
     * @param text
     * @param canvas
     * @param middle
     */
    private void drawChangeText(String text, Canvas canvas, int middle) {
        //判断当前的朝向
        if (mDirection == Direction.LEFE_TO_RIGHT) {
            drawText(text, canvas, mChangePaint, 0, middle);
        } else {
            drawText(text, canvas, mChangePaint, getWidth() - middle, getWidth());
        }
    }

    /**
     * 绘制不变色的部分
     *
     * @param text
     * @param canvas
     * @param middle
     */
    private void drawOriginText(String text, Canvas canvas, int middle) {
        if (mDirection == Direction.LEFE_TO_RIGHT) {
            drawText(text, canvas, mOriginPaint, middle, getWidth());
        } else {
            drawText(text, canvas, mOriginPaint, 0, getWidth() - middle);
        }
    }

    private void drawText(String text, Canvas canvas, Paint paint, int start, int end) {

        //变色范围  0 - middle - getWidth()
        canvas.save();
        //只绘制截取部分
        canvas.clipRect(new Rect(start, 0, end, getHeight()));
        //获取字体的范围bounds
        Rect rect = new Rect();
        //获取指定字符串所对应的最小矩形
        paint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();

        //x = 宽度的一半 - 字体宽度的一半；
        int x = (getWidth() - textWidth) / 2;
        //获取中心点到基线的距离
        int dy = getDy(rect, paint);

        //计算基线的位置
        int baseLine = (getHeight() + rect.height()) / 2 - dy;

        int baseLineHeight = getBaselineHeight(paint);

        //y 代表基线 baseLine
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
    }

    /**
     * <img width="570" height="220" src="http://upload-images.jianshu.io/upload_images/5017748-f2495c62aeaef2b6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240" alt="">
     * <p>
     * 假设我们是在画布Canvas的顶部绘制一行文字，规定一行文字的高度是y，
     * 文字区域的高度是Height（TOP和BOTTOM之间，TOP到0和BOTTOM到y的距离相等，这样文字才看起来是居中）。
     * 因此，0到y和TOP到BOTTOM的中线是重合的，y轴坐标都是y/2。
     * <p>
     * 我们要绘制一行文字时，设计必然会告诉我们0到y的距离，所以中线的位置也是固定的y/2，
     * 那么我们设置了Paint的文字大小后，Ascent和Descent又能直接得到，就可以算出中线到基线的距离，公式如下：
     * <p>
     * 基线到中线的距离=(Descent+Ascent)/2-Descent
     * <p>
     * 注意，实际获取到的Ascent是负数。公式推导过程如下：
     * 中线到BOTTOM的距离是(Descent+Ascent)/2，这个距离又等于Descent+中线到基线的距离，
     * 即(Descent+Ascent)/2=基线到中线的距离+Descent。
     * <p>
     * 有了基线到中线的距离，我们只要知道任何一行文字中线的位置，就可以马上得到基线的位置，
     * 从而得到Canvas的drawText方法中参数y的值。
     *
     * @param paint
     * @return
     */
    public int getBaselineHeight(Paint paint) {
        int baseLine;
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();

        baseLine = getHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;

        return baseLine;
    }

    private int getDy(Rect rect) {
        // 基线到中心点的位置 = 字体的高度/2 -
        int dy = rect.height() / 2 - rect.bottom;
        return dy;
    }

    public int getDy(Rect rect, Paint paint) {
        //获取文字的Metri 用来计算基线
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        // 基线到中心点的位置 = 字体的高度/2 -
        int dy = rect.height() / 2 - fontMetricsInt.bottom;
        return dy;
    }

    /**
     * 设置当前的进度
     *
     * @param currentProgress
     */
    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }

    /**
     * 设置不同的朝向
     */
    public void setDirection(Direction direction) {
        mDirection = direction;
    }

    /**
     * 方向的枚举类型
     */
    public enum Direction {
        RIGHT_TO_LEFT,
        LEFE_TO_RIGHT,
    }
}
