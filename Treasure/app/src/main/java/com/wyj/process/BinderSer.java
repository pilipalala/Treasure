package com.wyj.process;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;


public class BinderSer extends Service {
    private int count;
    private IBinder  myBinder = new MyBinder();;
    private boolean quit = false;


    public BinderSer() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!quit) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    count++;
                }


            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        quit = true;
    }

    public class MyBinder extends Binder {
        int getCount() {
            return count;
        }

    }
}
