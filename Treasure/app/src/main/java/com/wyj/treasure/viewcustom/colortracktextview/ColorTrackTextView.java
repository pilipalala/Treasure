package com.wyj.treasure.viewcustom.colortracktextview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * @author wangyujie
 *         on 2017/11/30.15:51
 *         TODO 颜色跟踪的textview
 *         <p>
 *         <p>
 *         知识点 canvas 的 掌握
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

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void initPaint() {
        mOriginPaint = getPaints(Color.BLACK);
        mChangePaint = getPaints(Color.RED);

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
        paint.getTextBounds(text, 0, text.length(), rect);
        int textWidth = rect.width();

        //x = 宽度的一半 - 字体宽度的一半；
        int x = (getWidth() - textWidth) / 2;
        //获取中心点到基线的距离
        int dy = getDy(rect, paint);
        //int dy = getDy(rect);

        //计算基线的位置
        int baseLine = (getHeight() + rect.height()) / 2 - dy;
        //y 代表基线 baseLine
        canvas.drawText(text, x, baseLine, paint);
        canvas.restore();
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
