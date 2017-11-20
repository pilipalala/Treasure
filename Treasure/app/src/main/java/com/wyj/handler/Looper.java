package com.wyj.handler;

/**
 * Created by wangyujie
 * Date 2017/11/19
 * Time 19:48
 * TODO
 */

public final class Looper {
    //每一个主线程都会有一个Looper对象
    //Looper对象保存在ThreadLocal,保证了线程数据的隔离
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
    //一个Looper对象，对应 一个消息队列
    MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * Looper对象的初始化
     */
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    /**
     * 获取当前线程的Looper对象
     *
     * @return
     */
    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 轮询消息队列
     */
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this thread.");
        }
        MessageQueue queue = me.mQueue;
        for (;;) {
            Message msg = queue.next();
            if (msg==null) {
                continue;
            }
            //转发给Handler
            msg.target.dispatchMessage(msg);

        }
    }
}
