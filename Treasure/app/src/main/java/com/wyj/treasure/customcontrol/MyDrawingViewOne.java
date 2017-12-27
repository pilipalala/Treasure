package com.wyj.treasure.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 *         on 2017/12/26.15:43
 *         TODO
 */

public class MyDrawingViewOne extends View {

    private Context mContext;

    public MyDrawingViewOne(Context context) {
        this(context, null);
    }

    public MyDrawingViewOne(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawingViewOne(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        //抗锯齿
        paint.setAntiAlias(true);
        //填充颜色
        paint.setColor(Color.RED);
        //填充模式 Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        paint.setStrokeWidth(20);
        /**
         * 设置阴影
         * radius:阴影的倾斜度
         * dx:水平位移
         * dy:垂直位移
         * */
        paint.setShadowLayer(10, 15, 15, Color.YELLOW);

        canvas.drawARGB(255, 255, 255, 255);
        /*1、画圆*/
        canvas.drawCircle(200, 200, 150, paint);


        /*2、画线*/
        canvas.drawLine(450, 210, 800, 800, paint);
        //3、画多条线
        /**
         * 这里不是形成连接线，而是每两个点形成一条直线（四个点：（10，10）、（100，100），（200，200），（400，400）），
         * 两两连成一条直线；
         * */
        float[] pts = {450, 660, 560, 860, 660, 960, 760, 1000};
        canvas.drawLines(pts, paint);


        /*4、画点*/
        paint.setColor(Color.BLUE);
        canvas.drawPoint(200, 200, paint);
        /*5、画多个点*/
        /**
         * float[] pts:点的合集，与上面直线一直，样式为｛x1,y1,x2,y2,x3,y3,……｝
         * int offset:集合中跳过的数值个数，注意不是点的个数！一个点是两个数值；
         * count:参与绘制的数值的个数，指pts[]里人数值个数，而不是点的个数，因为一个点是两个数值
         *
         * */
        paint.setColor(Color.BLUE);
        canvas.drawPoints(pts, paint);
        canvas.drawPoints(pts, 2, 4, paint);//跳过第一个点，画出后面两个点，第四个点不画

        /*6、画矩形*/
        canvas.drawRect(20, 1100, 120, 1200, paint);

        Rect rect = new Rect(150, 1100, 250, 1200);
        canvas.drawRect(rect, paint);

        RectF rectf = new RectF(280, 1100, 380, 1200);
        canvas.drawRect(rectf, paint);

        /*7、圆角矩形*/
        /**
         * RectF rect:要画的矩形
         * float rx:生成圆角的椭圆的X轴半径
         * float ry:生成圆角的椭圆的Y轴半径
         * */
        RectF roundRect = new RectF(20, 1300, 520, 1500);
        canvas.drawRoundRect(roundRect, 20, 10, paint);


        /*8、椭圆*/

        RectF ovalRect = new RectF(20, 1300, 520, 1500);
        paint.setColor(Color.YELLOW);
        canvas.drawOval(ovalRect, paint);

        /*9、画弧*/
        /**
         * 弧是椭圆的一部分，而椭圆是根据矩形来生成的，所以弧当然也是根据矩形来生成的；
         * void drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
         * 参数：
         * RectF oval:生成椭圆的矩形
         * float startAngle：弧开始的角度，以X轴正方向为0度
         * float sweepAngle：弧持续的角度
         * boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
         * */
        RectF arcRectF = new RectF(320, 1300, 820, 1500);
        paint.setColor(Color.RED);
        canvas.drawArc(arcRectF, 0, 90, false, paint);


    }
}
