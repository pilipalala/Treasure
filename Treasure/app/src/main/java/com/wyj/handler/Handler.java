package com.wyj.handler;

/**
 * Created by wangyujie
 * Date 2017/11/19
 * Time 19:48
 * TODO
 */

public class Handler {

    private Looper mLooper;
    private MessageQueue mQueue;
    private Looper mLoop;

    public Handler() {
        //获取主线程的Looper对象
        mLooper = Looper.myLooper();

        mQueue = mLooper.mQueue;

    }

    /**
     * 发送消息，压入队列
     *
     * @param msg
     */
    public void sendMessage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }
    public void dispatchMessage(Message msg) {
            handleMessage(msg);

    }
    public void handleMessage(Message msg) {
    }
}
