package com.wyj.treasure.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 *         on 2017/12/26.15:43
 *         TODO 路径及文字
 */

public class MyDrawingViewTwo extends View {

    private Context mContext;

    public MyDrawingViewTwo(Context context) {
        this(context, null);
    }

    public MyDrawingViewTwo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawingViewTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        paint.setStrokeWidth(5);

        drawPath(canvas, paint);


    }

    /**
     * canvas中绘制路径利用：
     * void drawPath (Path path, Paint paint)
     *
     * @param paint void moveTo (float x1, float y1):直线的开始点；即将直线路径的绘制点定在（x1,y1）的位置；
     *              void lineTo (float x2, float y2)：直线的结束点，又是下一次绘制直线路径的开始点；lineTo()可以一直用；
     *              void close ():如果连续画了几条直线，但没有形成闭环，调用Close()会将路径首尾点连接起来，形成闭环；
     */
    private void drawPath(Canvas canvas, Paint paint) {

        /**
         * 1、直线路径
         * */
        Path path = new Path();
        path.moveTo(20, 20);//设定起始点
        path.lineTo(20, 100);//第一条直线的终点，也是第二条直线的起点
        path.lineTo(220, 100);//画第二条直线
        path.close();//闭环
        canvas.drawPath(path, paint);
        /**
         *  2、矩形路径
         * void addRect (float left, float top, float right, float bottom, Path.Direction dir)
         * void addRect (RectF rect, Path.Direction dir)
         * 这里Path类创建矩形路径的参数与上篇canvas绘制矩形差不多，唯一不同的一点是增加了Path.Direction参数；
         * Path.Direction有两个值：
         * Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
         * Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；
         * */
        //第一个逆向生成
        Path CCWRectpath = new Path();
        RectF CCWRectF = new RectF(20, 150, 300, 300);
        CCWRectpath.addRect(CCWRectF, Path.Direction.CCW);
        canvas.drawPath(CCWRectpath, paint);
        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF CWRectF = new RectF(320, 150, 700, 300);
        CWRectpath.addRect(CWRectF, Path.Direction.CW);
        canvas.drawPath(CWRectpath, paint);

        String str = "一二三第五六七八九十";
        paint.setColor(Color.BLUE);
        paint.setTextSize(35);
        canvas.drawTextOnPath(str, CCWRectpath, 0, 30, paint);
        canvas.drawTextOnPath(str, CWRectpath, 0, 30, paint);

        /**
         * 3、圆角矩形路径
         * void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
         * void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
         * 这里有两个构造函数，部分参数说明如下：
         * 第一个构造函数：可以定制每个角的圆角大小：
         * float[] radii：必须传入8个数值，分四组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，
         * 如｛x1,y1,x2,y2,x3,y3,x4,y4｝，其中，x1,y1对应第一个角的（左上角）用来产生圆角的椭圆的横轴半径和纵轴半径，其它类推……
         * 第二个构造函数：只能构建统一圆角大小
         * float rx：所产生圆角的椭圆的横轴半径；
         * float ry：所产生圆角的椭圆的纵轴半径；
         * */

        Path roundRectPath = new Path();
        RectF roundRectF1 = new RectF(20, 350, 300, 600);
        roundRectPath.addRoundRect(roundRectF1, 15, 15, Path.Direction.CCW);
        RectF roundRectF2 = new RectF(320, 350, 600, 600);
        float[] radii = {10, 15, 20, 25, 30, 35, 40, 45};
        roundRectPath.addRoundRect(roundRectF2, radii, Path.Direction.CCW);
        canvas.drawPath(roundRectPath, paint);

        /**
         * 4、画圆
         * void addCircle (float x, float y, float radius, Path.Direction dir)
         * float x：圆心X轴坐标
         * float y：圆心Y轴坐标
         * float radius：圆半径
         * */

        Path roundPath = new Path();
        roundPath.addCircle(220, 720, 100, Path.Direction.CCW);
        canvas.drawPath(roundPath, paint);
        /**
         * 5、椭圆路径
         * RectF oval：生成椭圆所对应的矩形
         * */
        Path ovalPath = new Path();
        RectF rectF = new RectF(400, 750, 500, 800);
        ovalPath.addOval(rectF, Path.Direction.CCW);
        canvas.drawPath(ovalPath,paint);

        /**
         * 6、弧形路径
         * RectF oval：弧是椭圆的一部分，这个参数就是生成椭圆所对应的矩形；
         * float startAngle：开始的角度，X轴正方向为0度
         * float sweepAngel：持续的度数；
         * */
        Path arcPath = new Path();
        RectF arcRect =  new RectF(600, 850, 900, 1000);
        canvas.drawRect(arcRect,paint);
//        arcPath.addArc(arcRect, 0, 150);
        arcPath.addArc(arcRect, 0, 150);
        canvas.drawPath(arcPath, paint);




    }
}
