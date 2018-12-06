package com.wyj.treasure.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.wyj.treasure.utils.LogUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author wangyujie
 * @date 2018/11/9.17:02
 * @describe 2018 年 Android 保活方案效果统计
 * https://mp.weixin.qq.com/s?__biz=MzI3Mzc4ODQ3Mw==&mid=2247484280&idx=1&sn=5d3743234d1e6908435262aaef3acaea&chksm=eb1cb82edc6b313836ebf4064c78006eaa979a6d0f2db7d7355938a8b170aecd5c3c6bbc4966&mpshare=1&scene=1&srcid=0919zaJ4n03x7Ttt0XtZI7jZ#rd
 */

public class ProtectedService extends Service {

    private OnTimeChangeListener mOnTimeChangeListener;
    private Timer mRunTimer;

    private int mTimeSec;
    private int mTimeMin;
    private int mTimeHour;
    private DownloadBinder mDownloadBinder;
    private ScreenReceiverUtil.SreenStateListener mScreenListenerer = new ScreenReceiverUtil.SreenStateListener() {
        @Override
        public void onSreenOn() {
            mScreenManager.finishActivity();
            LogUtil.d("ProtectedService 关闭了1像素Activity");
        }

        @Override
        public void onSreenOff() {
            mScreenManager.startActivity();
            LogUtil.d("ProtectedService 打开了1像素Activity");
        }

        @Override
        public void onUserPresent() {
        }
    };
    private ScreenReceiverUtil mScreenListener;
    private ScreenManager mScreenManager;

    public ProtectedService() {
    }

    public class DownloadBinder extends Binder {


        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

        /**
         * 获取 Service 实例
         *
         * @return
         */
        public ProtectedService getService() {
            return ProtectedService.this;
        }

        public void setOnTimeChangeListener(OnTimeChangeListener listener) {
            mOnTimeChangeListener = listener;
        }

    }

    @Override
    public void onCreate() {
        super.onCreate();
        //        注册锁屏广播监听器
        mScreenListener = new ScreenReceiverUtil(this);
        mScreenManager = ScreenManager.getInstance(this);
        mScreenListener.setScreenReceiverListener(mScreenListenerer);
        mDownloadBinder = new DownloadBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d("ProtectedService onStartCommand");
        startRunTimer();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mDownloadBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.d("ProtectedService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopRunTimer();
    }

    private void startRunTimer() {
        TimerTask mTask = new TimerTask() {
            @Override
            public void run() {
                mTimeSec++;
                if (mTimeSec == 60) {
                    mTimeSec = 0;
                    mTimeMin++;
                }
                if (mTimeMin == 60) {
                    mTimeMin = 0;
                    mTimeHour++;
                }
                if (mTimeHour == 24) {
                    mTimeSec = 0;
                    mTimeMin = 0;
                    mTimeHour = 0;
                }
                String time = "时间为：" + mTimeHour + " : " + mTimeMin + " : " + mTimeSec;
                if (mOnTimeChangeListener != null) {
                    mOnTimeChangeListener.showTime(time);
                }
                LogUtil.d("ProtectedService" + time);
            }
        };
        mRunTimer = new Timer();
        // 每隔1s更新一下时间
        mRunTimer.schedule(mTask, 1000, 1000);
    }

    private void stopRunTimer() {
        if (mRunTimer != null) {
            mRunTimer.cancel();
            mRunTimer = null;
        }
        mTimeSec = 0;
        mTimeMin = 0;
        mTimeHour = 0;
        LogUtil.d("ProtectedService" + mTimeHour + " : " + mTimeMin + " : " + mTimeSec);

    }

    public interface OnTimeChangeListener {
        void showTime(String time);
    }
}
