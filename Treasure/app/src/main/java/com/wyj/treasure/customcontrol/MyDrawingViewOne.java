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
 * on 2017/12/26.15:43
 * TODO
 */

public class MyDrawingViewOne extends View {

    private Context mContext;
    private Paint paint = new Paint();

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
        initPaint();

        /*画布背景色*/
        canvas.drawARGB(255, 255, 255, 255);

        /**1、画圆
         * 圆心坐标(200,200)
         * 半径 150 px
         * */
        canvas.drawCircle(200, 200, 150, paint);

        /**2、画线
         * 起点坐标(450,210) 终点坐标(800,800)
         * */
        canvas.drawLine(450, 200, 700, 700, paint);
        //3、画多条线
        /**
         * 这里不是形成连接线，而是每两个点形成一条直线（四个点：（10，10）、（100，100），（200，200），（400，400）），
         * 两两连成一条直线；
         * */
        float[] pts = {250, 460, 360,460, 300, 560, 600,600};
        canvas.drawLines(pts, paint);


        /*4、画点*/
        paint.setColor(Color.BLUE);
        canvas.drawPoint(200, 200, paint);
        /*5、画多个点*/
        /**
         * float[] pts:点的合集，与上面直线一直，样式为｛x1,y1,x2,y2,x3,y3,……｝
         * int offset:集合中跳过的数值个数，注意不是点的个数！一个点是两个数值；
         * count:参与绘制的数值的个数，指pts[]里人数值个数，而不是点的个数，因为一个点是两个数值
         * */
        paint.setColor(Color.BLUE);
        canvas.drawPoints(pts, paint);
        canvas.drawPoints(pts, 2, 4, paint);//跳过第一个点，画出后面两个点，第四个点不画

        /*6、画矩形*/
        canvas.drawRect(20, 650, 120, 750, paint);

        Rect rect = new Rect(150, 650, 250, 750);
        canvas.drawRect(rect, paint);

        RectF rectf = new RectF(280, 650, 380, 750);
        canvas.drawRect(rectf, paint);

        /*7、圆角矩形*/
        /**
         * RectF rect:要画的矩形
         * float rx:生成圆角的椭圆的X轴半径
         * float ry:生成圆角的椭圆的Y轴半径
         * */
        RectF roundRect = new RectF(20, 780, 320, 950);
        canvas.drawRoundRect(roundRect, 20, 20, paint);


        /*8、椭圆*/

        RectF ovalRect = new RectF(400, 780, 620, 950);
        paint.setColor(Color.RED);
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
        paint.setStrokeWidth(5);
        paint.setStyle(Paint.Style.FILL);
        RectF arcRectF = new RectF(20, 980, 300, 1100);
        // 绘制背景矩形
        paint.setColor(Color.GRAY);
        canvas.drawRect(arcRectF, paint);
        RectF arcRectF1 = new RectF(320, 980, 600, 1100);
        paint.setColor(Color.GRAY);
        canvas.drawRect(arcRectF1, paint);
        paint.setColor(Color.RED);
        canvas.drawArc(arcRectF, 0, 90, false, paint);
        canvas.drawArc(arcRectF1, 0, 90, true, paint);
    }

    private void initPaint() {
        //抗锯齿
        paint.setAntiAlias(true);
        //填充颜色
        paint.setColor(Color.RED);
        //填充模式
        // Style.FILL //填充
        // Style.FILL_AND_STROKE //描边加填充
        // Style.STROKE //描边
        paint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        paint.setStrokeWidth(20);
        /**
         * 设置阴影
         * radius:阴影的倾斜度
         * dx:水平位移
         * dy:垂直位移
         * */
//        paint.setShadowLayer(10, 15, 15, Color.YELLOW);
    }
}
