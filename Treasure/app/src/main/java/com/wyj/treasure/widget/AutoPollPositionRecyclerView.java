package com.sumsoar.sxyx.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


import com.wyj.treasure.utils.DensityUtil;

import java.lang.ref.WeakReference;

/**
 * @author nicestars
 * @time 2019/2/18 09:32.
 * @Description ：轮询receclerview
 */
public class AutoPollPositionRecyclerView  extends RecyclerView{


    private static final long TIME_AUTO_POLL = 2000;
    private AutoPollTask autoPollTask;
    private boolean running; //表示是否正在自动轮询
    private boolean canRun;//表示是否可以自动轮询
    public static int position = 0;

    public AutoPollPositionRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollPositionRecyclerView.AutoPollTask(this);

    }

    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollPositionRecyclerView> mReference;

        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoPollPositionRecyclerView reference) {
            this.mReference = new WeakReference<AutoPollPositionRecyclerView>(reference);
        }

        @Override
        public void run() {
            Log.e("NiceStars", "run");
            AutoPollPositionRecyclerView recyclerView = mReference.get();
            if (recyclerView != null && recyclerView.running && recyclerView.canRun) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int offsetY = -DensityUtil.dip2px(0);
                layoutManager.scrollToPositionWithOffset(++position, offsetY);
                recyclerView.postDelayed(recyclerView.autoPollTask, TIME_AUTO_POLL);
            }
        }
    }


    //开启:如果正在运行,先停止->再开启
    public void start() {
        if (running)
            stop();
        canRun = true;
        running = true;
        postDelayed(autoPollTask, TIME_AUTO_POLL);
    }

    public void stop() {
        running = false;
        removeCallbacks(autoPollTask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (running)
                    stop();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun)
                    start();
                break;
        }
        return super.onTouchEvent(e);
    }


}
