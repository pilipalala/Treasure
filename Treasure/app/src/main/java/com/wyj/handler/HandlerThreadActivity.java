package com.wyj.handler;


import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.wyj.treasure.R;
import com.wyj.treasure.activity.BaseActivity;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import butterknife.BindView;
import butterknife.OnClick;

public class HandlerThreadActivity extends BaseActivity {

    @BindView(R.id.text1)
    TextView text1;
    private HandlerThread mHandlerThread;

    private Handler mainHandler, workHandler;


    @Override
    protected int getContentViewID() {
        return R.layout.activity_handler_thread;
    }

    @Override
    protected void initData() {
        // 创建与主线程关联的Handler
        mainHandler = new Handler();
        // 步骤1：创建HandlerThread实例对象
        // 传入参数 = 线程名字，作用 = 标记该线程
        mHandlerThread = new HandlerThread("handlerThread");

        // 步骤2：启动线程
        mHandlerThread.start();
        // 步骤3：创建工作线程Handler & 复写handleMessage（）
        // 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
        // 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
//        new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
//            @Override
//            public boolean handleMessage(Message msg) {
//                return false;
//            }
//        });
        workHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                final String s = (String) msg.obj;
                switch (msg.what) {
                    case 1:
                        //延时操作
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                text1.setText(s + "我爱学习");
                            }
                        });
                        break;
                    case 2:
                        //延时操作
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                text1.setText(s + "我不喜欢学习");
                            }
                        });
                        break;
                    default:
                        super.handleMessage(msg);
                        break;
                }

            }
        };
//        initThread();
    }

    private void initThread() {


        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {

                int i = 0;
                for (; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + "循环变量i的值：" + i);
                }
                return i;
            }
        });

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "循环变量i的值：" + i);
            if (i == 20) {
                new Thread(task, "有返回值的线程").start();
            }
            try {
                System.out.println("子线程的返回值：" + task.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    @OnClick({R.id.button1, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                // 步骤4：使用工作线程Handler向工作线程的消息队列发送消息
                // 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
                // a. 定义要发送的消息
                Message msga = workHandler.obtainMessage(2, "A");
                // b. 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msga);
                break;
            case R.id.button2:
                // 通过sendMessage（）发送
                // a. 定义要发送的消息
                Message msgb = new Message();
                msgb.what = 2;
                msgb.obj = "B";
                // b. 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msgb);
                break;
            case R.id.button3:
                // 步骤5：结束线程，即停止线程的消息循环
                mHandlerThread.quit();
                break;
            default:

                break;
        }
    }
}
