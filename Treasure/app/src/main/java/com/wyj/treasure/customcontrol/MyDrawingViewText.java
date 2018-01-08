package com.wyj.treasure.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 *         on 2017/12/26.15:43
 *         TODO
 */

public class MyDrawingViewText extends View {

    private Context mContext;

    public MyDrawingViewText(Context context) {
        this(context, null);
    }

    public MyDrawingViewText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawingViewText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();

        //TODO 普通设置
        //抗锯齿 使用会使绘图速度变慢
        paint.setAntiAlias(true);
        //填充颜色
        paint.setColor(Color.RED);
        //填充模式 文字和几何图形都有效 Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paint.setStyle(Paint.Style.FILL);
        //画笔宽度
        paint.setStrokeWidth(5);
        //设置文字大小
        paint.setTextSize(80);


        //设置文字对齐方式
//        paint.setTextAlign(Paint.Align.CENTER);
//        paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变

        drawText(canvas, paint);


    }

    private void drawText(Canvas canvas, Paint paint) {
        /*1、绘图样式的区别*/
        canvas.drawText("Hello world! 填充模式", 10, 100, paint);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText("Hello world! 描边模式", 10, 200, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("Hello world! 填充且描边模式", 10, 300, paint);


        //TODO 样式设置
        paint.setFakeBoldText(true);//是否为粗体文字
        paint.setUnderlineText(true);//下划线
        paint.setStrikeThruText(true);//设置带有删除线效果

        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(80);

        /*2、倾斜度正负区别*/
        paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度
        canvas.drawText("Hello world! 往右斜", 10, 400, paint);

        paint.setTextSkewX((float) 0.25);//设置字体水平倾斜度
        canvas.drawText("Hello world! 往左斜", 10, 500, paint);


        //3、水平拉伸设置*/
        paint.setTextSkewX(0);//设置字体水平倾斜度
        //水平方向拉伸两倍
        paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
        canvas.drawText("Hello world!", 10, 600, paint);
        //写在同一位置,不同颜色,看下高度是否看的不变
        paint.setTextScaleX(1);//先还原拉伸效果
        canvas.drawText("Hello world!", 10, 700, paint);
        paint.setColor(Color.GREEN);
        paint.setTextScaleX(2);//重新设置拉伸效果
        canvas.drawText("Hello world!", 10, 700, paint);



        /*4、指定个个文字位置
        * void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
        * void drawPosText (String text, float[] pos, Paint paint)
        * 说明：
        * 第一个构造函数：实现截取一部分文字绘制；
        * 参数说明：
        * char[] text：要绘制的文字数组
        * int index:：第一个要绘制的文字的索引
        * int count：要绘制的文字的个数，用来算最后一个文字的位置，从第一个绘制的文字开始算起
        * float[] pos：每个字体的位置，同样两两一组，如｛x1,y1,x2,y2,x3,y3……｝
        * */

        Paint paint1 = new Paint();
        paint1.setColor(Color.RED);  //设置画笔颜色

        paint1.setStrokeWidth(5);//设置画笔宽度
        paint1.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint1.setTextSize(80);//设置文字大小
        paint1.setStyle(Paint.Style.FILL);//绘图样式，设置为填充
        float[] pos = new float[]{80, 800,
                80, 900,
                80, 1000,
                80, 1100};
        canvas.drawPosText("画图示例", pos, paint1);//两个构造函数



        /*5、沿路径绘制*/

        Path path1 = new Path();
        path1.addCircle(400, 950, 200, Path.Direction.CCW);
        canvas.drawPath(path1, paint1);

        Path path2 = new Path();
        path2.addCircle(850, 950, 200, Path.Direction.CCW);
        canvas.drawPath(path2, paint1);

        paint1.setColor(Color.BLUE);
        paint1.setTextSize(45);

        String str = "风萧萧兮易水寒，壮士一去兮不复返";

        canvas.drawTextOnPath(str, path1, 0, 0, paint1);
        //第二个路径，改变hoffset、voffset参数值
        canvas.drawTextOnPath(str, path2, 280, 20, paint1);

        /*6、字体样式设置（Typeface）*/

        paint1.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充
        paint1.setTextSize(80);
        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.NORMAL);
        paint1.setTypeface(font);
        canvas.drawText("piliPala", 10, 1300, paint1);


    }
}
