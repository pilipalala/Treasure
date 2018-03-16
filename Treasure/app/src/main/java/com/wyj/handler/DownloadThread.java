package com.wyj.handler;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;

import java.util.Arrays;
import java.util.List;

/**
 * @author wangyujie
 *         on 2018/3/16.17:49
 *         TODO
 */

public class DownloadThread extends HandlerThread implements Handler.Callback {
    private Handler mUIHander;
    private Handler mDownHander;
    private List<String> mDownloadUrlList;
    public static final String DOWN_KEY = "down_key";
    public static final int TYPE_START = 0x1111;

    public static final int TYPE_FINISHED = 0x1110;

    public DownloadThread(String name) {
        super(name);
    }

    public Handler getUIHander() {
        return mUIHander;
    }

    public DownloadThread setUIHander(Handler mUIHander) {
        this.mUIHander = mUIHander;
        return this;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        if (mUIHander == null) {
            new Throwable("Not set UIHandler!");
        }
        mDownHander = new Handler(getLooper(), this);

        for (String url : mDownloadUrlList) {
            Message message = mDownHander.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(DOWN_KEY, url);
            message.setData(bundle);
            mDownHander.sendMessage(message);
        }

    }

    public void setDownloadUrls(String... urls) {
        mDownloadUrlList = Arrays.asList(urls);
    }
    @Override
    public boolean handleMessage(Message msg) {
        if (msg == null && msg.getData() == null) {
            return false;
        }
        Bundle data = msg.getData();
        String url = (String) data.get(DOWN_KEY);
        Message startMsg = mUIHander.obtainMessage(TYPE_START, "\n 开始下载 @" + DateUtils.getMMddhhmmss(System.currentTimeMillis()) + "\n" + url);
        mUIHander.sendMessage(startMsg);

        SystemClock.sleep(2000);    //模拟下载

        Message finishMsg = mUIHander.obtainMessage(TYPE_FINISHED, "\n 开始下载 @" + DateUtils.getMMddhhmmss(System.currentTimeMillis()) + "\n" + url);
        mUIHander.sendMessage(finishMsg);
        return true;
    }

    @Override
    public boolean quitSafely() {
        mUIHander = null;
        return super.quitSafely();
    }
}
