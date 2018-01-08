package com.wyj.treasure.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 *         on 2017/12/26.15:43
 *         TODO
 */

public class MyDrawingDrawText extends View {

    private Context mContext;

    public MyDrawingDrawText(Context context) {
        this(context, null);
    }

    public MyDrawingDrawText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawingDrawText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStrokeWidth(5);

        float baseLineX = 100;
        float baseLineY = 200;

        paint.setColor(Color.BLUE);
        paint.setTextSize(150);//以px为单位
        /*设置的相对位置为，指定的原点(100,200)在绘制矩形的左侧。换句话说，所绘制的文字所在矩形在(0,200)点的右侧*/
        paint.setTextAlign(Paint.Align.LEFT);//从哪个方向开始绘制

        canvas.drawText("pilipala\'King", baseLineX, baseLineY, paint);

        /**
         * 通过paint.getFontMetrics()得到对应的FontMetrics对象。这里还有另外一个FontMetrics同样的类叫做FontMetricsInt，它的意义与FontMetrics完全相同，
         * 只是得到的值的类型不一样而已，FontMetricsInt中的四个成员变量的值都是Int类型，而FontMetrics得到的四个成员变量的值则都是float类型的。
         * */
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
//        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();

        float top = baseLineY + fontMetrics.top;
        float ascent = baseLineY + fontMetrics.ascent;
        float descent = baseLineY + fontMetrics.descent;
        float bottom = baseLineY + fontMetrics.bottom;

        /*画基线*/
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 1000, baseLineY, paint);
         /*画top*/
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, top, 1000, top, paint);
        /*画ascent*/
        paint.setColor(Color.LTGRAY);
        canvas.drawLine(baseLineX, ascent, 1000, ascent, paint);
         /*画descent*/
        paint.setColor(Color.CYAN);
        canvas.drawLine(baseLineX, descent, 1000, descent, paint);
        /*画bottom*/
        paint.setColor(Color.MAGENTA);
        canvas.drawLine(baseLineX, bottom, 1000, bottom, paint);

    }
}
