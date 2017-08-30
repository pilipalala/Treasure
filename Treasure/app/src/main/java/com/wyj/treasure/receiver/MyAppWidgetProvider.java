package com.wyj.treasure.receiver;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.SystemClock;
import android.widget.RemoteViews;

import com.wyj.treasure.R;
import com.wyj.treasure.utils.LogUtil;
import com.wyj.treasure.utils.ToastUtil;

/**
 * Created by admin
 * on 2017/8/23.
 * TODO
 */

public class MyAppWidgetProvider extends AppWidgetProvider {
    private static final String CLICK_ACTION = "com.wyj.treasure.action.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        //这里判断是自己的action，做自己的事情，比如小部件被单击了要干什么，这里是做一个动画效果
        if (intent.getAction().equals(CLICK_ACTION)) {
            ToastUtil.show("click it");
            new Thread(() -> {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher_round);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                for (int i = 0; i < 37; i++) {
                    float degree = (i * 10) % 360;
                    RemoteViews remoteView = new RemoteViews(context.getPackageName(), R.layout.widget);
                    remoteView.setImageViewBitmap(R.id.iv_widget, remoteBitmap(bitmap, degree));
                    Intent intentClick = new Intent();
                    intent.setAction(CLICK_ACTION);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
                    remoteView.setOnClickPendingIntent(R.id.iv_widget, pendingIntent);
                    appWidgetManager.updateAppWidget(new ComponentName(context, MyAppWidgetProvider.class), remoteView);
                    SystemClock.sleep(30);
                }
            }).start();

        }
    }

    /**
     * 每次桌面小部件更新是都调用一次该方法
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetIds
     */
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        LogUtil.i("onUpdate");
        int counter = appWidgetIds.length;
        LogUtil.i("counter---:" + counter);
        for (int i = 0; i < counter; i++) {
            int appWidgetId = appWidgetIds[i];
            onWidgetUpdate(context, appWidgetManager, appWidgetId);
        }

    }

    /**
     * 桌面小部件更新
     *
     * @param context
     * @param appWidgetManager
     * @param appWidgetId
     */
    private void onWidgetUpdate(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        //桌面小部件 单击事件发送的Intent广播
        Intent intentClick = new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intentClick, 0);
        remoteViews.setOnClickPendingIntent(R.id.iv_widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    /**
     * @param context
     * @param bitmap
     * @param degree
     * @return
     */
    private Bitmap remoteBitmap(Bitmap bitmap, float degree) {
        Matrix matrix = new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        Bitmap tmpBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        return tmpBitmap;

    }
}
