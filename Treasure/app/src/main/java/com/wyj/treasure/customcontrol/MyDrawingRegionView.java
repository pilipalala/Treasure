package com.wyj.treasure.customcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author wangyujie
 *         on 2017/12/26.15:43
 *         TODO
 */

public class MyDrawingRegionView extends View {

    private Context mContext;

    public MyDrawingRegionView(Context context) {
        this(context, null);
    }

    public MyDrawingRegionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDrawingRegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //初始化画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        Rect rect1 = new Rect(150, 200, 600, 350);
        Rect rect2 = new Rect(300, 50, 450, 500);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        Region region1 = new Region(rect1);
        Region region2 = new Region(rect2);
        //取两个区域的交集
        region1.op(region2, Region.Op.REPLACE);
        //再构造一个画笔,填充Region操作结果
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.BLUE);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region1, paint_fill);
    }

    /**
     * 对于特定的区域，我们都可以使用多个矩形来表示其大致形状。事实上，如果矩形足够小，
     * 一定数量的矩形就能够精确表示区域的形状，也就是说，一定数量的矩形所合成的形状，也可以代表区域的形状。
     * RegionIterator类，实现了获取组成区域的矩形集的功能，其实RegionIterator类非常简单，总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
     * RegionIterator(Region region) //根据区域构建对应的矩形集
     * boolean	next(Rect r) //获取下一个矩形，结果保存在参数Rect r 中
     * 由于在Canvas中没有直接绘制Region的函数，我们想要绘制一个区域，就只能通过利用RegionIterator构造矩形集来逼近的显示区域。用法如下：
     */
    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

}
