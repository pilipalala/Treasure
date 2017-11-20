package com.wyj.handler;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangyujie
 * Date 2017/11/19
 * Time 19:48
 * TODO
 */

public class MessageQueue {

    //通过数组的结构存储Message对象
    Message[] items;
    //入队元素索引位置
    int putIndex;
    //出队元素索引位置
    int takeIndex;
    //计数器
    int count;

    //互斥锁
    /**
     * 代码块加锁
     * synchronized () {
     * <p>
     * }
     */
    private Lock lock;

    //条件变量
    private Condition notEmty;
    private Condition notFull;

    MessageQueue() {
        //消息队列应该要有大小的限制
        this.items = new Message[50];
        lock = new ReentrantLock();
        notEmty = lock.newCondition();
        notFull = lock.newCondition();
    }

    /**
     * 加入队列（子线程运行）
     *
     * @param msg
     */
    public void enqueueMessage(Message msg) {
        try {
            lock.lock();
            //消息队列满了，子线程停止发送消息，阻塞
            while (count == items.length) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            items[putIndex] = msg;
            //循环取值
            putIndex = (++putIndex == items.length) ? 0 : putIndex;
            count++;

            //有新的message对象，通知主线程
            notEmty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 出队列（主线程运行）
     *
     * @return
     */
    public Message next() {
        Message msg = null;
        try {
            lock.lock();
            //消息队列为空,主线程Looper停止轮询，阻塞
            while (count == 0) {
                try {
                    notEmty.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            msg = items[takeIndex];//取出
            items[takeIndex] = null;//元素置空
            takeIndex = (++takeIndex == items.length) ? 0 : takeIndex;
            count--;

            //使用了一个Message对象，通知子线程
            notFull.signalAll();
        } finally {
            lock.unlock();
        }
        return msg;
    }


}
