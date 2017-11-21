package com.wyj.treasure;

import android.content.Context;
import android.os.Looper;
import android.os.Process;

import com.wyj.treasure.utils.ToastUtil;

/**
 * Created by wangyujie
 * Date 2017/11/21
 * Time 23:19
 * TODO
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static CrashHandler mInstance;

    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private Context mContext;

    public CrashHandler() {
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static CrashHandler getInstance() {
        if (mInstance == null) {
            synchronized (CrashHandler.class) {
                if (mInstance == null) {
                    mInstance = new CrashHandler();
                }
            }
        }
        return mInstance;
    }


    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        if (!handlerExection(e)) {
            /*未处理  调用系统默认的处理器处理*/
            if (mDefaultHandler != null) {
                mDefaultHandler.uncaughtException(t, e);
            }
        } else {
            /*已经人为处理*/
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            Process.killProcess(Process.myPid());
            System.exit(1);

        }
    }

    /**
     * 人为处理异常
     *
     * @param e
     * @return true：已处理  false：没有处理
     */
    private boolean handlerExection(Throwable e) {
        if (e == null) {
            return false;
        }
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                ToastUtil.show("uncaughtException");
                Looper.loop();
            }
        }.start();
        //收集错误信息
        collectErrorInfo();
        //保存错误信息
        saveErrorInfo();
        return false;
    }

    private void saveErrorInfo() {
        
    }

    private void collectErrorInfo() {

    }
}
