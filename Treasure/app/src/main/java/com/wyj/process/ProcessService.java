package com.wyj.process;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.wyj.treasure.IMyAidlInterface;
import com.wyj.treasure.utils.LogUtil;

public class ProcessService extends Service {
    private static final String TAG = "ProcessService";
    private MyBinder binder;

    public ProcessService() {
    }


    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.i(TAG + "执行了onCreat()");
        binder = new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        User user = (User) intent.getSerializableExtra("data");
        LogUtil.i(TAG + "user"+user.toString());
        LogUtil.i(TAG + "执行了onStartCommand()");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG + "执行了onDestory()");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        LogUtil.i(TAG + "执行了onUnbind()");
        return super.onUnbind(intent);
    }

    // 实例化AIDL的Stub类(Binder的子类)
    class MyBinder extends IMyAidlInterface.Stub {
        //重写接口里定义的方法
        @Override
        public String getInfor(String infor) throws RemoteException {
            LogUtil.i(TAG + "接收到Activity的字符串infor: " + infor);
            return "service传过去的字符串infor";
        }

        @Override
        public String getName(char name) throws RemoteException {
            LogUtil.i(TAG + "接收到Activity的字符串name: " + name);
            return "service传过去的字符串name";
        }

        @Override
        public String getBook(Book book) throws RemoteException {
            LogUtil.i(TAG + "接收到Activity的字符串book: " + book.toString());
            return "service传过去的字符串book";
        }
    }
}
